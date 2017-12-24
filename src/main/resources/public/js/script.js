var informationJSON;
var actualAircraftTypeList;
var map;
var layer;
var iconGeometry;
var restUrl = 'http://localhost:8080';
var timeIndex = 0;

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
    initializeForm();
    initializeChangeHandlers();

    //submitApplication();

    // for hash link
    var url = new URL(window.location.href);
    var key = url.searchParams.get("key");
    console.log('url key: ' + key);
    var edit = url.searchParams.get("edit");
    console.log('url edit: ' + edit);
});

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

    /*
        $('input[name="dateFromUntil"]').on('apply.daterangepicker', function(ev, picker) {
            $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
        });

        $('input[name="dateFromUntil"]').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
        });*/

}

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
    $('input.data').val('');
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
        //.attr('name', field.name)
        .prop('required', field.mandatory ? true : false);
}

function processField(field) {
    if (field.active) {
        if (field.id.substring(0, 6) === 'radio_') {
            //$('#' + field.id).prop('required', true);
            $('#' + field.id).parent().removeClass('display-none');
            $('#' + field.id).parent().parent().removeClass('display-none');
            $('#' + field.id).prop('required', true);
            //$('#' + field.id).parent().attr('title', field.tooltip);
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
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            // here to send data
            event.preventDefault();
            submitApplication();
            console.log("submitted");
        }
        form.classList.add('was-validated');
    })

    /* var form = document.getElementById('needs-validation');
     form.addEventListener('submit', function (event) {
         if (form.checkValidity() === false) {
             event.preventDefault();
             event.stopPropagation();
         }
         else {
             // here to send data
             event.preventDefault();
             submitApplication();
             console.log("submitted");
         }
         form.classList.add('was-validated');
     }, false);*/


    $(document).on('change', '#type_of_activity', function () {
        hideAllFields();

        // and hide the aircraft type selection and empty value
        $('#type_of_aircraft').parent().hide()
            .prop('required', false);
        $('#type_of_aircraft').val('');


        //
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
        //event.preventDefault();
        var template = $('#time_template'),
            clone = template
                .clone()
                .removeClass('display-none')
                .removeAttr('id')
                //.prop('required', true)
                .attr('data-time-index', timeIndex)
                .addClass('time_field')
                .insertBefore(template);
        //$option = $clone.find('[name="option[]"]');

        clone
            .find('[name="start"]').attr('name', 'start[' + timeIndex + ']')
            .prop('required', true).end()
            .find('[name="end"]').attr('name', 'end[' + timeIndex + ']')
            .prop('required', true).end();

        // Add new field
        //$('#time_container').formValidation('addField', option);
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

    $(document).on('keyup', '#field_gps_coord', function () {

        /*map.geocode('Payerne');*/

        var lat = 46.84089;
        var lon = 9.3;

        var defaultEpsg = 'EPSG:21781';

        var position;
        var extent = map.getView().getProjection().getExtent();


        var query = $('#field_gps_coord').val();

        var DMSDegree = '[0-9]{1,2}[°|º]\\s*';
        var DMSMinute = '[0-9]{1,2}[\'|′]';
        var DMSSecond = '(?:\\b[0-9]+(?:\\.[0-9]*)?|\\.' +
            '[0-9]+\\b)("|\'\'|′′|″)';
        var DMSNorth = '[N]';
        var DMSEast = '[E]';
        var MGRS = '^3[123][\\sa-z]{3}[\\s\\d]*';
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
        var regexMGRS = new RegExp(MGRS, 'gi');
        // Grid zone designation for Switzerland + two 100km letters + two digits
        // It's a limitiation of proj4 and a sensible default (precision is 10km)
        var MGRSMinimalPrecision = 7;

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
            // validate the field
            $('#field_gps_coord').addClass('.is-valid');
            $('#field_gps_coord').removeClass('.is-invalid');
            setMarker(position);
        }
        else {
            $('#field_gps_coord').addClass('.is-invalid');
            $('#field_gps_coord').removeClass('.is-valid');
            //$('#field_gps_coord').css("border-color", '#dc3545');
        }

        /*
                    var sr = '?';
                    var epsgCode = map.getView().getProjection().getCode();
                    sr += 'sr=' + epsgCode.split(':')[1] + '&';

                    $.ajax({
                        crossOrigin: true,
                        url: 'https://api3.geo.admin.ch/rest/services/api/SearchServer' + sr + '&searchText=' + $('#field_latitude_longitude').val() + '&type=locations&lang=de',
                        type: 'GET',
                        dataType: 'json'
                    })
                        .done(function (json) {
                            console.log(json);
                            if (fuzzy)

                                setMarker(json.results[0].attrs.lat, json.results[0].attrs.lon)
                        })
                        .fail(function (xhr, status, errorThrown) {
                            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
                        });

        */
        // '/rest/services/{Topic}/MapServer/{Layer}/{Feature}' + sr

        /*
                lat = $('#field_latitude').val();
                lon = $(this).val();

                setMarker(lat, lon);
                */

    });

    $(document).on('click', '#btn_draw_rectangle', function () {

        var rect = new ol.interaction.Draw({
            source: source,
            //style: iconStyle,
            type: 'Circle',
            geometryFunction: ol.interaction.Draw.createRegularPolygon(4)
        });

        map.addInteraction(rect);


        rect.on('drawend', function (evt) {
            var feature = evt.feature;
            map.removeInteraction(rect);
            console.log("created Rectangle: " + feature.getGeometry().getExtent());
        });
        snap = new ol.interaction.Snap({source: source});
        map.addInteraction(snap);

    });

    $(document).on('click', '#btn_draw_point', function () {
        drawFigure('Point');
        /* var iconStyle = new ol.style.Style({
             image: new ol.style.Circle({
                 radius: 10,
                 fill: new ol.style.Fill({
                     color: 'rgba(255,0,0,0.3)'
                 }),
                 stroke: new ol.style.Stroke({
                     color: 'rgba(255,0,0,0.8)',
                     width: 3
                 })
             })
         });

         var point = new ol.interaction.Draw({
             source: source,
             style: iconStyle,
             type: 'Point',
             geometryFunction: function (coords, geom) {
                 if (!geom) {
                     geom = new ol.geom.Point(null);
                 }
                 console.info(coords);
                 geom.setCoordinates(coords);
                 return geom;
             }
         });
         map.addInteraction(point);


         point.on('drawend', function (evt) {
             console.info('drawend');
             //console.info(evt);
             var feature = evt.feature;
             map.removeInteraction(point);
             console.log(feature.getGeometry().getExtent());
         });

 */


        /*   var pointMarker = new ol.Feature({
               geometry: new ol.geom.Point(),
               style: iconStyle
           });
   */
        // overlay.addFeature(pointMarker);


        /*   var draw, snap; // global so we can remove them later
           // var typeSelect = document.getElementById('type');

           draw = new ol.interaction.Draw({
               source: source,
               type: 'Point'
           });

          // not working console.log(draw.getGeometry().getExtent());

             map.addInteraction(draw);
             snap = new ol.interaction.Snap({source: source});
             map.addInteraction(snap);
   */
    });

    $(document).on('click', '#btn_draw_polygon', function () {
        drawFigure('Polygon');
    });

    $(document).on('click', '#btn_draw_circle', function () {
        var circle = new ol.interaction.Draw({
            source: source,
            //style: iconStyle,
            type: 'Circle'
        });

        map.addInteraction(circle);

        snap = new ol.interaction.Snap({source: source});
        map.addInteraction(snap);

        circle.on('drawend', function (evt) {
            var geometry = evt.feature.getGeometry();
            console.log("created circle: " + geometry.getExtent());
        });
    });

}

function drawFigure(figureType) {

    var iconStyle = new ol.style.Style({
        image: new ol.style.Circle({
            radius: 10,
            fill: new ol.style.Fill({
                color: 'rgba(255,0,0,0.3)'
            }),
            stroke: new ol.style.Stroke({
                color: 'rgba(255,0,0,0.8)',
                width: 3
            })
        })
    });

    var type;
    if (figureType === 'Rectangle') {
        type = 'Circle';
    }
    else {
        type = figureType;
    }

    var point = new ol.interaction.Draw({
        source: source,
        //style: iconStyle,
        type: figureType,
        geometryFunction: function (coords, geom) {
            if (!geom) {
                if (figureType === 'Point')
                    geom = new ol.geom.Point(null);
                /* else if(figureType === 'Rectangle') {
                     geom = ol.interaction.Draw.createBox();
                 }*/
                if (figureType === 'Polygon')
                    geom = new ol.geom.Polygon(null);
                else if (figureType === 'Circle')
                    geom = new ol.geom.Circle(null);
            }
            //console.info(coords);
            geom.setCoordinates(coords);
            return geom;
        }
    });

    map.addInteraction(point);

    snap = new ol.interaction.Snap({source: source});
    map.addInteraction(snap);

    point.on('drawend', function (evt) {
        //console.info(evt);
        var feature = evt.feature;
        map.removeInteraction(point);
        /*
                // Create a select interaction and add it to the map:
                selectInteraction = new ol.interaction.Select();
                map.addInteraction(selectInteraction);



                // select feature:
                selectInteraction.getFeatures().push(feature);
                // do something after drawing (e.g. saving):

        */
        snap = new ol.interaction.Snap({source: source});
        map.addInteraction(snap);

        console.log("created " + figureType + ": " + feature.getGeometry().getExtent());
        $('#field_gps_coord').val(feature.getGeometry().getExtent());
        /*
                // Create a modify interaction and add to the map:
                modifyInteraction = new ol.interaction.Modify({ features: selectInteraction.getFeatures() });
                map.addInteraction(modifyInteraction);

                // set listener on "modifyend":
                modifyInteraction.on('modifyend', function(e) {
                    // get features:
                  //  var collection = e.features;
                    // There's only one feature, so get the first and only one:
                 //   var featureClone = collection.item(0).clone();
                    // do something after modifying (e.g. saving):
                    console.log("modified: " +feature.getGeometry().getExtent());
                });
        */
    });
}

function setLayerVisible(layerIndex, isVisible) {
    //  map.getLayers()[layerIndex].setVisible(isVisible);
    map.getLayers().forEach(function (layer, idx) {
        if (idx == layerIndex)
            layer.setVisible(isVisible);
    });
};

function initializeMap() {

    var lat = 46.78;
    var lon = 9.3;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');
    //
    //    iconGeometry = new ol.geom.Point(loc);
    //
    //    var iconStyle = new ol.style.Style({
    //        image: new ol.style.Circle({
    //            radius: 10,
    //            fill: new ol.style.Fill({
    //                color: 'rgba(255,0,0,0.3)'
    //            }),
    //            stroke: new ol.style.Stroke({
    //                color: 'rgba(255,0,0,0.8)',
    //                width: 3
    //            })
    //        })
    //        /*Icon(({
    //            anchor: [0.5, 46],
    //            anchorXUnits: 'fraction',
    //            anchorYUnits: 'pixels',
    //            opacity: 0.9,
    //            src: 'img/marker.png'
    //        }))*/
    // });
    // /*
    //    var pointMarker = new ol.Feature({
    //        geometry: iconGeometry,
    //        style: iconStyle
    //        /*,name: 'Null Island',
    //        population: 4000,
    //        rainfall: 500*/
    //
    // pointMarker.setStyle(iconStyle);
    //
    //
    // var modify = new ol.interaction.Modify({
    //     features: new ol.Collection([pointMarker]),
    //     style: iconStyle
    // });
    //
    // // pointMarker.set('visible', false);
    //
    //
    // var vectorLayer = new ol.source.Vector({
    //     features: [pointMarker]
    // });
    //
    // layer = new ol.layer.Vector({
    //     source: vectorLayer
    // });

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
        // Create a view
        //  view: view,

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})

    });

    //layer.setVisible(false);

    map.getView().setCenter(loc);
    map.getView().setResolution(500);

    setLayerVisible(1, false);

    /*
    map.on('singleclick', function (evt) {
        layer.setVisible(true);
        iconGeometry.setCoordinates(evt.coordinate);

        var gps = ol.proj.transform(evt.coordinate, 'EPSG:21781', 'EPSG:4326');

        $('#field_latitude').val(gps[1]);
        $('#field_longitude').val(gps[0]);

        map.removeInteraction(modify);
        modify = new ol.interaction.Modify({
            features: new ol.Collection([pointMarker]),
            style: new ol.style.Style({
                image: new ol.style.Circle({
                    radius: 10,
                    fill: new ol.style.Fill({
                        color: 'rgba(255,0,0,0.8)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: 'rgba(255,0,0,0.8)',
                        width: 3
                    })
                })
            })

        });

        map.addInteraction(modify);


    }, pointMarker);


    pointMarker.on('change', function () {
        var gps = ol.proj.transform(iconGeometry.getCoordinates(), 'EPSG:21781', 'EPSG:4326');

        $('#field_latitude').val(gps[1]);
        $('#field_longitude').val(gps[0]);

    });
    */
}

// function setView(loc) {
//     map.getView().setCenter(loc);
//     map.getView().setResolution(50);
// }
//
// function setMarker(pos) {
//     layer.setVisible(true);
//     //var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');
//
//     setView([pos[0], pos[1]]);
//     iconGeometry.setCoordinates([pos[0], pos[1]]);
// }

function submitApplication() {

    var data = {};

    $('.data').each(
        function (index) {
            var input = $(this);
            data[input.attr('name')] = input.val();

            // console.log('Type: ' + input.attr('type') + 'Name: ' + input.attr('name') + 'Value: ' + input.val());
        }
    );
    data["heightType"] = $('input[name=heightType]:checked').val();

    // get the time ranges
    var startArray = [];
    var endArray = [];
    var times = [];

    $.each($('input[name^="start"]'), function (i, time) {
        if ($(this).val() != "")
            startArray.push($(this).val());
    });

    $.each($('input[name^="end"]'), function (i, time) {
        if ($(this).val() != "")
            endArray.push($(this).val());
    });

    $.each(startArray, function (i, item) {
        times.push('{"start":"' + startArray[i] + '","end":"' + endArray[i] + '"}');
    });

    data['times'] = times;

    // TODO: coordinate
    /* var coordinates = [];

     if ($('#field_gps_coord').val() != "") {
         var tmp = $('#field_gps_coord').val().split(';');
     }*/

    console.log(data);


    // submit to server
    /*   $.ajax({
           crossOrigin: true,
           url: restUrl + '/applications',
           type: 'POST',
           contentType: "application/json; charset=utf-8",
           data: '{"email":"jalil.hashemi@students.fhnw.ch", "times" : [{"start":"12:00", "end":"13:00"}], "name":"adsf","company":"Mfddfarco", "activityType" : "Airshow", "aircraftType" : "RPAS", "heightType": "m GND", "location" : "Windisch", "coordinates" : [{"lat":"46.6", "lon":"7.3"},{"lat":"45.5", "lon":"8.7"}]}',
           dataType: 'json'
       })
           .done(function (json) {
               console.log(json);
           })
           .fail(function (xhr, status, errorThrown) {
               console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
           });
   */
}
