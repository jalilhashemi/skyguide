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
    $('[data-toggle="tooltip"]').tooltip();

    $.ajax({
        crossOrigin: true,
        url: 'http://localhost:8080/information',
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
}

function initializeChangeHandlers() {
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

    $(document).on('focusout', '#field_longitude', function () {

        /*map.geocode('Payerne');*/

        var lat = 46.84089;
        var lon = 9.3;

        lat = $('#field_latitude').val();
        lon = $(this).val();

        setMarker(lat, lon);
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
            layer
        ],
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

function setMarker(lat, lon) {
    layer.setVisible(true);
    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    setView(loc);
    iconGeometry.setCoordinates(loc);

}
