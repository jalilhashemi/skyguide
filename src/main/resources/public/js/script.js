var informationJSON;
var actualAircraftTypeList;
var map;
var layer;
var lyr2 = ga.layer.create('ch.bazl.luftfahrtkarten-icao');

var iconGeometry;

$(document).ready(function () {
    initializeForm();
    initializeChangeHandlers();
    initializeMap();

    $.ajax({
        crossOrigin: true,
        url: 'http://localhost:8080/applications',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: '{"name":"adsf","company":"Mfddfarco"}',
        dataType: 'json'
    })
        .done(function (json) {
            console.log(json);
        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });

    $.ajax({
        crossOrigin: true,
        url: 'http://86.119.37.77:8080/information',
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
});

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.name
        }));
    });
}

function initializeForm() {
    $('[data-toggle="tooltip"]').tooltip();
    $(function () {
        $('input[name="daterange"]').daterangepicker({
            locale: {
                format: 'MM.DD.YYYY'
            }
        });
    });
}

function initializeChangeHandlers() {
    window.addEventListener('load', function () {
        var form = document.getElementById('needs-validation');
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    }, false);

    $(document).on('change', '#type_of_activity', function () {
        $('#container_fields').children('div .form-group').addClass('display-none');
        $('#container_fields').children('div .form-row').children('div .form-group').addClass('display-none');
        $('#map-container').addClass('display-none');

        $('.form-check-inline').parent().addClass('display-none');
        $('.form-check-inline').addClass('display-none');

        $('#type_of_aircraft').parent().hide();

        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.name) {
                if (activityType.aircraftTypeList.length > 1) {
                    $('#type_of_aircraft').parent().show();
                    $('#type_of_aircraft').find('option').remove().end().append("<option value='-1'>Select the aircraft type</option>");
                    actualAircraftTypeList = activityType.aircraftTypeList;
                    $.each(activityType.aircraftTypeList, function (i, aircraftType) {
                        $('#type_of_aircraft').append($('<option>', {
                            text: aircraftType.name
                        }));
                    });
                }
                else {
                    $('#type_of_aircraft').parent().hide();
                    $.each(activityType.aircraftTypeList[0].fieldList, function (i, field) {
                        if (field.active) {
                            if (field.id.substring(0, 6) === 'radio_') {
                                $('#' + field.id).parent().parent().removeClass('display-none');
                                $('#' + field.id).parent().parent().parent().removeClass('display-none');
                            }
                            else {
                                $('#' + field.id).parent().removeClass('display-none');
                                $('#map-container').removeClass('display-none');
                                map.updateSize();

                                if (field.mandatory) {
                                    $('#' + field.id).parent().children('label').remove();
                                    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.name + '*</label>')
                                    $('#' + field.id).attr('placeholder', field.name).prop('required', true);
                                }
                                else {
                                    $('#' + field.id).parent().children('label').remove();
                                    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.name + '</label>')
                                    $('#' + field.id).attr('placeholder', field.name);
                                }

                            }
                        }
                    });
                }
            }
        });
    });

    $(document).on('change', '#type_of_aircraft', function () {
        // $('#container_fields').children('div').addClass('display-none');
        $('#container_fields').children('div .form-group').addClass('display-none');
        $('#container_fields').children('div .form-row').children('div .form-group').addClass('display-none');
        $('#map-container').addClass('display-none');

        $('.form-check-inline').parent().addClass('display-none');
        $('.form-check-inline').addClass('display-none');

        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.name) {
                $.each(aircraftType.fieldList, function (i, field) {
                    if (field.active) {
                        if (field.id.substring(0, 6) === 'radio_') {
                            $('#' + field.id).parent().parent().removeClass('display-none');
                            $('#' + field.id).parent().parent().parent().removeClass('display-none');
                        }
                        else {
                            $('#' + field.id).parent().removeClass('display-none');
                            $('#map-container').removeClass('display-none');
                            map.updateSize();

                            if (field.mandatory) {
                                $('#' + field.id).parent().children('label').remove();
                                $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.name + '*</label>\n')
                                $('#' + field.id).attr('placeholder', field.name).prop('required', true);
                            }
                            else {
                                $('#' + field.id).parent().children('label').remove();
                                $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.name + '</label>\n')
                                $('#' + field.id).attr('placeholder', field.name);
                            }
                        }
                    }
                });
            }
        });

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
        if (position != null)
            setMarker(position);

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

    // middle of the map (init state)
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
            ga.layer.create('ch.swisstopo.fixpunkte-agnes'),
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
