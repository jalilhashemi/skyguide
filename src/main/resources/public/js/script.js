var informationJSON;
var actualAircraftTypeList;
var map;
var layer;
var lyr2 = ga.layer.create('ch.bazl.luftfahrtkarten-icao');
var iconGeometry;
var restUrl = 'http://localhost:8080';


$(document).ready(function () {
    initializeForm();
    initializeChangeHandlers();

    //submitApplication();

    // for hash link
    var url = new URL(window.location.href);
    var key = url.searchParams.get("key");
    console.log('url key: ' + key);
    var edit = url.searchParams.get("edit");
    console.log('url edit: '+edit);

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
            text: item.name
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
        $('input[name="daterange"]').daterangepicker({
            locale: {
                format: 'MM.DD.YYYY'
            }
        });
    });
}

function hideAllFields() {
    $('#container_fields').children('div .form-group').addClass('display-none');
    $('#container_fields').children('div .form-row').children('div .form-group').addClass('display-none');
    $('#map-container').addClass('display-none');
    $('#addScnt').addClass('display-none');
    $('.time_field').addClass('display-none');

    $('.form-check-inline').parent().addClass('display-none');
    $('.form-check-inline').addClass('display-none');
}

/**
 * Displays a field with the label and the mandatory * sign
 * @param field The JSON field object from /information service
 */
function showField(field) {
    $('#' + field.id).parent().children('label').remove();
    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.name + (field.mandatory ? '*' : '') + '</label>\n')
    $('#' + field.id).attr('placeholder', field.placeholder).prop('required', field.mandatory ? true : false);
}

function processField(field) {
    if (field.active) {
        if (field.id.substring(0, 6) === 'radio_') {
            $('#' + field.id).parent().parent().removeClass('display-none');
            $('#' + field.id).parent().parent().parent().removeClass('display-none');
            $('#addScnt').removeClass('display-none');
        }
        else {
            $('#' + field.id).parent().removeClass('display-none');
            showField(field);
        }
    }
}

function initializeChangeHandlers() {

    $(document).on('submit', '#needs-validation', function() {
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

        // and hide the aircraft type selection
        $('#type_of_aircraft').parent().hide();

        //
        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.name) {
                // it's a activity type with multiple aircraft type
                if (activityType.aircraftTypeList.length > 1) {
                    actualAircraftTypeList = activityType.aircraftTypeList;

                    // show the dropdown
                    $('#type_of_aircraft').parent().show();

                    // remove all options and append default text
                    $('#type_of_aircraft').find('option').remove().end().append("<option value=''>Select the aircraft type</option>");

                    // append all aircraft types
                    $.each(activityType.aircraftTypeList, function (i, aircraftType) {
                        $('#type_of_aircraft').append($('<option>', {
                            text: aircraftType.name
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
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.name) {
                $('#map-container').removeClass('display-none');
                map.updateSize();
                $('#addScnt').removeClass('display-none');
                $.each(aircraftType.fieldList, function (i, field) {
                    processField(field);
                });
            }
        });

    });

    $(document).on('click', '#addScnt', function() {
        event.preventDefault();
        var $template = $('#time_template'),
            $clone    = $template
                .clone()
                .removeClass('display-none')
                .removeAttr('id')
                .addClass('time_field')
                .insertBefore($template),
            $option   = $clone.find('[name="option[]"]');

        // Add new field
        //$('#time_container').formValidation('addField', option);
    });

    $(document).on('click', '.remove_time_button', function() {
        var $row    = $(this).parents('.form-row'),
            $option = $row.find('[name="option[]"]');

        // Remove element containing the option
        $row.remove();
    });

    $(document).on('change', '#check-layer-icao', function () {
        if ($('#check-layer-icao').is(':checked')) {
            map.addLayer(lyr2);
        }
        else {
            map.removeLayer(lyr2);
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
}

function initializeMap() {

    var lat = 46.78;
    var lon = 9.3;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    iconGeometry = new ol.geom.Point(loc);

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
        /*Icon(({
            anchor: [0.5, 46],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            opacity: 0.9,
            src: 'img/marker.png'
        }))*/
    });

    var pointMarker = new ol.Feature({
        geometry: iconGeometry,
        style: iconStyle
        /*,name: 'Null Island',
        population: 4000,
        rainfall: 500*/
    });

    pointMarker.setStyle(iconStyle);


    var modify = new ol.interaction.Modify({
        features: new ol.Collection([pointMarker]),
        style: iconStyle
    });

    // pointMarker.set('visible', false);

    var vectorLayer = new ol.source.Vector({
        features: [pointMarker]
    });

    layer = new ol.layer.Vector({
        source: vectorLayer
    });

    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            layer
        ],
        crossOrigin: 'null',
        // Create a view
        //  view: view,

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})
    });

    layer.setVisible(false);

    //layer.redraw();

    map.getView().setCenter(loc);
    map.getView().setResolution(500);

    //   map.addInteraction(modify);

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
}

function setView(loc) {
    map.getView().setCenter(loc);
    map.getView().setResolution(50);
}

function setMarker(pos) {
    layer.setVisible(true);
    //var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    setView([pos[0], pos[1]]);
    iconGeometry.setCoordinates([pos[0], pos[1]]);
}

function submitApplication() {


    // submit to server
    $.ajax({
        crossOrigin: true,
        url: url + '/applications',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: '{"email":"jalil.hashemi@students.fhnw.ch","name":"adsf","company":"Mfddfarco", "activityType" : "Airshow", "aircraftType" : "RPAS", "heightType": "m GND", "location" : "Windisch", "coordinates" : [{"lat":"46.6", "lon":"7.3"},{"lat":"45.5", "lon":"8.7"}]}',
        dataType: 'json'
    })
        .done(function (json) {
            console.log(json);
        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });
}
