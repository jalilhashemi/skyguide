var informationJSON;
var actualAircraftTypeList;
var map;
var restUrl = 'http://localhost:8080';
var timeIndex = 0;
var Modify;
var Draw;
var drawings = [];
var source = new ol.source.Vector();
var vector = new ol.layer.Vector({
    source: source,
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 0, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: '#FF0000',
            width: 2
        }),
        image: new ol.style.Circle({
            radius: 7,
            fill: new ol.style.Fill({
                color: '#FF0000'
            })
        })
    })
});

$(document).ready(function () {

    $("#icon_loading").hide();
    // get the url params
    var url = new URL(window.location.href);
    var edit = url.searchParams.get("edit");
    var key = url.searchParams.get("key");
    if (key != null) {
        console.log('url key: ' + key);
        console.log('url edit: ' + edit);
        if (edit != null)
            showAdminView(key);
        else
            showUserView(key);
    }
    // standard form view
    else {
        initializeForm();
        initializeChangeHandlers();
    }

    // prevent enter for submission
    $(window).keydown(function(event){
        if(event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });
});

function showAdminView(key) {
    // not implemented yet
    alert("admin");
}

function showUserView(key) {
    disableAllFields();
    fillAllFields(key);

    initializeMap();
    $('#map-container').removeClass('display-none');
    map.updateSize();
}

function disableAllFields() {
    $('fieldset').prop('disabled', true);
}

function fillAllFields(key) {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/applications/' + key,
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            fillFields(json);
        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });

}

function fillFields(data) {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/information',
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            initializeDisabledInputs(json, data.activityType, data.aircraftType, data);
        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });
}

function initializeDisabledInputs(information, activityType, aircraftType, data) {
    $('#type_of_activity').append($('<option>', {
        text: activityType,
        selected: true
    }));
    if (aircraftType != null) {
        $('#type_of_aircraft').parent().show();
        $('#type_of_aircraft').append($('<option>', {
            text: aircraftType,
            selected: true
        }));

        $.each(information, function (i, infoItem) {
            if ($('#type_of_activity').val() == infoItem.label) {
                $.each(infoItem.aircraftTypeList, function (j, aircraftTypeItem) {
                    if ($('#type_of_aircraft').val() == aircraftTypeItem.label) {
                        $.each(aircraftTypeItem.fieldList, function (i, field) {
                            processField(field);
                        });
                    }
                });
            }
        });

        $('.data').each(function (index) {
                var input = $(this);
                input.val(data[input.attr('name')]);
            }
        );

        $('input[name=heightType]').each(function (index) {
            if ($(this).val() == data["heightType"])
                $(this).prop('checked', true);
        });

        var gps = [data["drawings"][0]["coordinates"][0]["lat"], data["drawings"][0]["coordinates"][0]["lon"]];
        console.log(gps);

        $('#field_gps_coord').val(gps[0] + ", "
            + gps[1]);

        var position = ol.proj.transform(gps, 'EPSG:4326', 'EPSG:21781');
        console.log(position);

        setView([position[0], position[1]]);

        var feature = new ol.Feature({
            geometry: new ol.geom.Point(position)
        });
        source.addFeature(feature);

        $.each(data["times"], function (i, time) {
            if (i > 0) {
                var template = $('#time_template'),
                    clone = template
                        .clone()
                        .removeClass('display-none')
                        .removeAttr('id')
                        //.prop('required', true)
                        .attr('data-time-index', i)
                        .addClass('time_field')
                        .insertBefore(template);

                clone
                    .find('[name="start"]').attr('name', 'start[' + i + ']')
                    .prop('required', true).end()
                    .find('[name="end"]').attr('name', 'end[' + i + ']')
                    .prop('required', true).end();
            }
            $('input[name^="start[' + i + ']"]').val(time["start"].substring(0,5));
            $('input[name^="end[' + i + ']"]').val(time["end"].substring(0,5));
        });

    }
}

function initializeDropdowns() {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/information',
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            appendSelection(json);
            informationJSON = json;
        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });
}

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.label
        }));
    });
}

function initializeForm() {
    initializeDropdowns();
    initializeDateRangePicker();
    initializeTooltips();
    initializeMap();
    initDrawTool();

}

function initializeTooltips() {
    $('[data-toggle="tooltip"]').tooltip();
}

function initializeDateRangePicker() {
    $(function () {
        $('input[name="dateFromUntil"]').daterangepicker({
            //autoUpdateInput: false,
            locale: {
                format: 'DD.MM.YYYY'
            }
        });
    });
}

/**
 * Hide all custom fields.
 */
function hideAllFields() {
    $('#container_fields').children('div .form-group').addClass('display-none');
    $('#container_fields').children('div .form-row').children('div .form-group').addClass('display-none');
    $('#map-container').addClass('display-none');
    $('#addScnt').addClass('display-none');
    $('.time_field').addClass('display-none');

    $('.custom-control-input').parent().addClass('display-none');
    $('.custom-control-input').addClass('display-none');
    $('.custom-control-input').prop('checked', false);
    $('.custom-control-input').prop('required', false);

    // empty all fields
    $('input.data.activity-data').val('');
}

/**
 * Displays a field with the label and the mandatory * sign
 * @param field The JSON field object from /information service
 */
function showField(field) {
    $('#' + field.id).parent().removeClass('display-none');
    $('#' + field.id).parent().children('label').remove();
    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.label + (field.mandatory ? '*' : '') + '</label>\n')
    $('#' + field.id)
        .attr('placeholder', field.placeholder)
        .prop('required', field.mandatory ? true : false);
}


function processField(field) {
    if (field.active) {
        if (field.id.substring(0, 6) === 'radio_') {
            $('#' + field.id).parent().removeClass('display-none');
            $('#' + field.id).parent().parent().removeClass('display-none');
            $('#' + field.id).prop('required', true);
            // $('#' + field.id).parent().attr('title', field.tooltip);
            initializeTooltips();
            $('#addScnt').removeClass('display-none');
        }
        else {
            showField(field);
        }
    }

}

function initializeChangeHandlers() {

    $(document).on('submit', '#needs-validation', function () {
        var form = document.getElementById('needs-validation');
        // submitApplication();
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            // here to send data
            event.preventDefault();
            submitApplication();
            $("#icon_loading").show();
            $("#btn_submit").attr('disabled','disabled');
        }
        form.classList.add('was-validated');
    });

    $(document).on('change', '#type_of_activity', function () {
        hideAllFields();

        // and hide the aircraft type selection and empty value
        $('#type_of_aircraft').parent().hide()
            .prop('required', false);
        $('#type_of_aircraft').val('');

        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.label) {
                // it's a activity type with multiple aircraft type
                if (activityType.aircraftTypeList.length > 1) {
                    actualAircraftTypeList = activityType.aircraftTypeList;

                    // show the dropdown
                    $('#type_of_aircraft').parent().show()
                        .prop('required', true);

                    // remove all options and append default text
                    $('#type_of_aircraft').find('option').remove().end().append("<option value=''>Select the aircraft type</option>");

                    // append all aircraft types
                    $.each(activityType.aircraftTypeList, function (i, aircraftType) {
                        $('#type_of_aircraft').append($('<option>', {
                            text: aircraftType.label
                        }));
                    });
                }
                else {
                    // things showed anytime
                    $('#map-container').removeClass('display-none');
                    map.updateSize();
                    // add time button
                    $('#addScnt').removeClass('display-none');

                    $.each(activityType.aircraftTypeList[0].fieldList, function (i, field) {
                        processField(field);
                    });
                }
            }
        });
    });

    $(document).on('change', '#type_of_aircraft', function () {
        hideAllFields();

        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.label) {
                $('#map-container').removeClass('display-none');
                map.updateSize();
                $('#addScnt').removeClass('display-none');
                $.each(aircraftType.fieldList, function (i, field) {
                    processField(field);
                });
            }
        });

    });

    $(document).on('click', '#addScnt', function () {
        timeIndex++;
        var template = $('#time_template'),
            clone = template
                .clone()
                .removeClass('display-none')
                .removeAttr('id')
                //.prop('required', true)
                .attr('data-time-index', timeIndex)
                .addClass('time_field')
                .insertBefore(template);

        clone
            .find('[name="start"]').attr('name', 'start[' + timeIndex + ']')
            .prop('required', true).end()
            .find('[name="end"]').attr('name', 'end[' + timeIndex + ']')
            .prop('required', true).end();
    });

    $(document).on('click', '.remove_time_button', function () {
        var row = $(this).parents('.form-row');

        // Remove element containing the option
        row.remove();
    });

    $(document).on('change', '#check-layer-icao', function () {
        if ($('#check-layer-icao').is(':checked')) {
            setLayerVisible(1, true);
        }
        else {
            setLayerVisible(1, false);
        }
    });

    $(document).on('change', '#field_gps_coord', function () {

        // if geocode is needed
        /*map.geocode('Payerne');*/

        var defaultEpsg = 'EPSG:21781';

        var position;
        var extent = map.getView().getProjection().getExtent();


        var query = $('#field_gps_coord').val();

        // source of the search transition code
        // https://github.com/geoadmin/mf-geoadmin3/blob/17ba14f3047bf4692752b36a1295172fa396177d/src/components/search/SearchService.js

        var DMSDegree = '[0-9]{1,2}[°|º]\\s*';
        var DMSMinute = '[0-9]{1,2}[\'|′]';
        var DMSSecond = '(?:\\b[0-9]+(?:\\.[0-9]*)?|\\.' +
            '[0-9]+\\b)("|\'\'|′′|″)';
        var DMSNorth = '[N]';
        var DMSEast = '[E]';
        var regexpDMSN = new RegExp(DMSDegree +
            '(' + DMSMinute + ')?\\s*' +
            '(' + DMSSecond + ')?\\s*' +
            DMSNorth, 'g');
        var regexpDMSE = new RegExp(DMSDegree +
            '(' + DMSMinute + ')?\\s*' +
            '(' + DMSSecond + ')?\\s*' +
            DMSEast, 'g');
        var regexpDMSDegree = new RegExp(DMSDegree, 'g');
        var regexpCoordinate = new RegExp(
            '([\\d\\.\']+)[\\s,]+([\\d\\.\']+)' +
            '([\\s,]+([\\d\\.\']+)[\\s,]+([\\d\\.\']+))?');

        var roundCoordinates = function (coords) {
            return [Math.round(coords[0] * 1000) / 1000,
                Math.round(coords[1] * 1000) / 1000];
        };

        // Parse degree EPSG:4326 notation
        var matchDMSN = query.match(regexpDMSN);
        var matchDMSE = query.match(regexpDMSE);
        if (matchDMSN && matchDMSN.length === 1 &&
            matchDMSE && matchDMSE.length === 1) {
            var northing = parseFloat(matchDMSN[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
            var easting = parseFloat(matchDMSE[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
            var minuteN = matchDMSN[0].match(DMSMinute) ?
                matchDMSN[0].match(DMSMinute)[0] : '0';
            northing = northing +
                parseFloat(minuteN.replace('\'', '').replace('′', '')) / 60;
            var minuteE = matchDMSE[0].match(DMSMinute) ?
                matchDMSE[0].match(DMSMinute)[0] : '0';
            easting = easting +
                parseFloat(minuteE.replace('\'', '').replace('′', '')) / 60;
            var secondN =
                matchDMSN[0].match(DMSSecond) ?
                    matchDMSN[0].match(DMSSecond)[0] : '0';
            northing = northing + parseFloat(secondN.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
            var secondE = matchDMSE[0].match(DMSSecond) ?
                matchDMSE[0].match(DMSSecond)[0] : '0';
            easting = easting + parseFloat(secondE.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
            position = ol.proj.transform([easting, northing],
                'EPSG:4326', defaultEpsg);
            if (ol.extent.containsCoordinate(extent, position)) {
                position = roundCoordinates(position);

            }
        }

        var match = query.match(regexpCoordinate);
        if (match) {
            var left = parseFloat(match[1].replace(/'/g, ''));
            var right = parseFloat(match[2].replace(/'/g, ''));
            // Old school entries like '600 000 200 000'
            if (match[3] != null) {
                left = parseFloat(match[1].replace(/'/g, '') +
                    match[2].replace(/'/g, ''));
                right = parseFloat(match[4].replace(/'/g, '') +
                    match[5].replace(/'/g, ''));
            }
            position = [left > right ? left : right,
                right < left ? right : left];

            // Match LV95
            if (ol.extent.containsCoordinate(extent, position)) {
                position = roundCoordinates(position);

            }

            // Match decimal notation EPSG:4326
            if (left <= 180 && left >= -180 &&
                right <= 180 && right >= -180) {
                position = [left > right ? right : left,
                    right < left ? left : right
                ];
                position = ol.proj.transform(position, 'EPSG:4326',
                    defaultEpsg);
                if (ol.extent.containsCoordinate(extent, position)) {
                    position = roundCoordinates(position);

                }
            }

            // Match LV03 coordinates
            $.ajax({
                crossOrigin: true,
                url: 'http://geodesy.geo.admin.ch/reframe/lv03tolv95?easting=' + position[0] + '&northing=' + position[1],
                type: 'GET',
                dataType: 'json'
            })
                .done(function (pos) {
                    if (ol.extent.containsCoordinate(extent, pos)) {
                        position = roundCoordinates(pos);
                    }
                })
                .fail(function (xhr, status, errorThrown) {
                    console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
                });

        }
        if (position != null) {
            var gps = ol.proj.transform(position, 'EPSG:21781', 'EPSG:4326');
            setView([position[0], position[1]]);
            // validate the field
            $('#field_gps_coord').addClass('.is-valid');
            $('#field_gps_coord').removeClass('.is-invalid');
            if (source.getFeatures().length != 0) {
                source.getFeatures()[0].getGeometry().setCoordinates(position);
                drawings = [];
                drawings.push({"drawingType": "Point", "coordinates": [{"lat": gps[0], "lon": gps[1]}]});
                console.log(drawings);
            }
            else {
                var feature = new ol.Feature({
                    geometry: new ol.geom.Point(position)
                });
                source.addFeature(feature);

                drawings = [];
                drawings.push({"drawingType": "Point", "coordinates": [{"lat": gps[0], "lon": gps[1]}]});
                console.log(drawings);
            }

        }
        else {
            $('#field_gps_coord').addClass('.is-invalid');
            $('#field_gps_coord').removeClass('.is-valid');
        }
    });

    $(document).on('click', '#btn_draw_rectangle', function () {
        Draw.setActive(true, 'Rectangle');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_point', function () {
        Draw.setActive(true, 'Point');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_polygon', function () {
        Draw.setActive(true, 'Polygon');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_circle', function () {
        Draw.setActive(true, 'Circle');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_path', function () {
        Draw.setActive(true, 'Path');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_modify', function () {
        Draw.setActive(false);
        Modify.setActive(true);
    });

}

function initDrawTool() {

    Modify = {
        init: function () {
            this.select = new ol.interaction.Select();
            map.addInteraction(this.select);

            this.modify = new ol.interaction.Modify({
                features: this.select.getFeatures()
            });
            map.addInteraction(this.modify);

            this.setEvents();
        },
        setEvents: function () {
            var selectedFeatures = this.select.getFeatures();

            this.select.on('change:active', function () {
                selectedFeatures.forEach(selectedFeatures.remove, selectedFeatures);
            });

            selectedFeatures.on('add', function (e) {
                e.element.on('change', function (e) {
                    var features = vector.getSource().getFeatures();
                    var geometry = features[0].getGeometry().getCoordinates();
                    var gps = ol.proj.transform(geometry, 'EPSG:21781', 'EPSG:4326');
                    drawings = [];
                    drawings.push({"drawingType": "Point", "coordinates": [{"lat": gps[0], "lon": gps[1]}]});
                    console.log(drawings);
                    $('#field_gps_coord').val(gps);
                });
            });
        },
        setActive: function (active) {
            this.select.setActive(active);
            this.modify.setActive(active);
        }
    };

    Modify.init();

    Draw = {
        init: function () {
            map.addInteraction(this.Point);
            this.Point.setActive(false);
            map.addInteraction(this.Path);
            this.Path.setActive(false);
            map.addInteraction(this.Polygon);
            this.Polygon.setActive(false);
            map.addInteraction(this.Circle);
            this.Circle.setActive(false);
            map.addInteraction(this.Rectangle);
            this.Rectangle.setActive(false);
            this.setEvents();
        },
        Point: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Point'
        }),
        Path: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'LineString'
        }),
        Polygon: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Polygon'
        }),
        Circle: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Circle'
        }),
        // new ol.interaction.DragBox({
        Rectangle: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Circle',
            geometryFunction: ol.interaction.Draw.createRegularPolygon(4)
        }),
        getActive: function () {
            return this.activeType ? this[this.activeType].getActive() : false;
        },
        setActive: function (active, type) {
            if (active) {
                this.activeType && this[this.activeType].setActive(false);
                this[type].setActive(true);
                this.activeType = type;
            } else {
                this.activeType && this[this.activeType].setActive(false);
                this.activeType = null;
            }
        },
        setEvents: function () {
            this.Point.on('drawend', function (evt) {
                var feature = evt.feature;
                var geometry = feature.getGeometry().getCoordinates();
                var gps = ol.proj.transform(geometry, 'EPSG:21781', 'EPSG:4326');
                drawings.push({"drawingType": "Point", "coordinates": [{"lat": gps[0], "lon": gps[1]}]});
                console.log(drawings);
                $('#field_gps_coord').val(gps);
            });
            this.Path.on('drawend', function (evt) {
                var feature = evt.feature;
                console.log("created Path: " + feature.getGeometry().getExtent());
            });
            this.Polygon.on('drawend', function (evt) {
                var feature = evt.feature;
                console.log("created Polygon: " + feature.getGeometry().getExtent());
            });
            this.Circle.on('drawend', function (evt) {
                var feature = evt.feature;
                console.log("created Circle: " + feature.getGeometry().getExtent());
            });
            this.Rectangle.on('drawend', function (evt) {
                var feature = evt.feature;
                console.log("created Rectangle: " + feature.getGeometry().getExtent());
            });
        }
    };
    Draw.init();

    Modify.setActive(false);

    var snap = new ol.interaction.Snap({
        source: vector.getSource()
    });
    map.addInteraction(snap);
}

function setLayerVisible(layerIndex, isVisible) {
    //  map.getLayers()[layerIndex].setVisible(isVisible);
    map.getLayers().forEach(function (layer, idx) {
        if (idx == layerIndex)
            layer.setVisible(isVisible);
    });
};

function initializeMap() {

    // default center position
    var lat = 46.78;
    var lon = 9.3;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            ga.layer.create('ch.bazl.luftfahrtkarten-icao'),
            vector
        ],
        crossOrigin: 'null',

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})

    });

    map.getView().setCenter(loc);
    map.getView().setResolution(500);

    setLayerVisible(1, false);
}

function setView(loc) {
    map.getView().setCenter(loc);
    map.getView().setResolution(50);
}

function submitApplication() {

    var data = {};

    $('.data').each(
        function (index) {
            var input = $(this);
            data[input.attr('name')] = input.val();
        }
    );
    data["heightType"] = $('input[name=heightType]:checked').val();

    // get the time ranges
    var startArray = [];
    var endArray = [];
    var times = [];

    $.each($('input[name^="start"]'), function (i, time) {
        if ($(this).val() != "")
            startArray.push($(this).val() + ":00");
    });

    $.each($('input[name^="end"]'), function (i, time) {
        if ($(this).val() != "")
            endArray.push($(this).val()+ ":00");
    });

    $.each(startArray, function (i, item) {
        times.push({"start": startArray[i], "end": endArray[i]});
    });

    data['times'] = times;
    data['drawings'] = drawings;

    if (data['heightType'] == undefined)
        data['heightType'] = 'none';

    if (data['aircraftType'] == '')
        data['aircraftType'] = 'none';

    var success = '{"email":"jalil.hashemi@students.fhnw.ch", "drawings":[' + drawings + '], "times" : [{"start":"12:00", "end":"13:00"}], "name":"adsf","company":"Mfddfarco", "activityType" : "Airshow", "aircraftType" : "RPAS", "heightType": "m GND", "location" : "Windisch"}';
    console.log(data);

    // submit to server
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/applications',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        dataType: 'json'
    })
        .done(function (json) {
            console.log(json);
            hideAllFields();
            $('#submit_success').modal('show');

        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });

}
