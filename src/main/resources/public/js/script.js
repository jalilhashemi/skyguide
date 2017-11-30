var informationJSON;
var actualAircraftTypeList;
var map;
var view;
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

        $('#radio_height_fl').parent().parent().removeClass('display-none');
        $('#radio_height_fl').parent().parent().parent().removeClass('display-none');

        $('#type_of_aircraft').parent().hide();
        /*  if($('#type_of_activity').val() == -1) {
              $('#type_of_aircraft').hide();
              $.each(informationJSON[0].aircraftTypeList[0].fieldList, function (j, field) {
                  $("#" + field.name).hide();
              });
          }*/
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
                            if (field.id.substring(0, 5) === 'radio_') {
                                /*  $('#' + field.id).parent().removeClass('display-none');
                                  $('#' + field.id).parent().parent().removeClass('display-none');

                                  $('#' + field.id).parent().attr('title', field.tooltip);
                                  */
                            }
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
                            /*       var counter = 0;
                                   $.each(field.options, function (i, option) {
                                       if (option.active)
                                           counter++;
                                   });
                                   if (counter > 0) {
                                       //var radioDiv = $("<div>").attr('class', 'form-group');
                                       $.each(field.options, function (i, radio) {
                                           if (radio.active) {
                                               // radioDiv.append('<div class="form-check form-check-inline" data-toggle="tooltip" data-placement="top"\n' +
                                               //     '             title="' + radio.tooltip + '"><label class="form-check-label">\n' +
                                               //     '<input class="form-check-input" type="radio" name="inlineRadioOptions" id="' + radio.id + '"' +
                                               //     'value="' + radio.name + '"> ' + radio.name +
                                               //     '</label></div>');

                                           }
                                       });
                                       //$('#container_fields').append(radioDiv);
                                       $('[data-toggle="tooltip"]').tooltip();
                                   }*/
                        }
                        else {
                            if (field.id.substring(0, 5) === 'radio_') {
                                //    $('#' + field.id).parent().addClass('display-none');
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


        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.name) {
                $.each(aircraftType.fieldList, function (i, field) {
                    if (field.active) {
                        if (field.id.substring(0, 5) === 'radio_') {
                            /*  $('#' + field.id).parent().removeClass('display-none');
                              $('#' + field.id).parent().parent().removeClass('display-none');
                              $('#' + field.id).parent().attr('title', field.tooltip);
                              */
                        }
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


                        /*   var counter = 0;
                           $.each(field.options, function (i, option) {
                               if (option.active)
                                   counter++;
                           });
                           if (counter > 0) {
                               //var radioDiv = $("<div>").attr('class', 'form-group');
                               $.each(field.options, function (i, radio) {
                                   if (radio.active) {
                                       // radioDiv.append('<div class="form-check form-check-inline" data-toggle="tooltip" data-placement="top"\n' +
                                       //     '             title="' + radio.tooltip + '"><label class="form-check-label">\n' +
                                       //     '<input class="form-check-input" type="radio" name="inlineRadioOptions" id="' + radio.id + '"' +
                                       //     'value="' + radio.name + '"> ' + radio.name +
                                       //     '</label></div>');

                                   }
                               });
                               //$('#container_fields').append(radioDiv);
                               $('[data-toggle="tooltip"]').tooltip();
                           }
   */

                    }
                    else {
                        if (field.id.substring(0, 5) === 'radio_') {
                            // $('#' + field.id).parent().addClass('display-none');
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
        var lon = 7.46485;

        lat = $('#field_latitude').val();
         lon = $(this).val();

        var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

        // Zoom on the position
        map.getView().setCenter(loc);
        map.getView().setResolution(50);

        iconGeometry.setCoordinates(loc);

    });
}

function initializeMap() {

    var lat = 46.84089;
    var lon = 7.46485;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    iconGeometry = new ol.geom.Point(loc);

    var iconFeature = new ol.Feature({
        geometry: iconGeometry,
        name: 'Null Island',
        population: 4000,
        rainfall: 500
    });

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

    iconFeature.setStyle(iconStyle);

    var vectorSource = new ol.source.Vector({
        features: [iconFeature]
    });

    var vectorLayer = new ol.layer.Vector({
        source: vectorSource
    });

    var modify = new ol.interaction.Modify({
        features: new ol.Collection([iconFeature]),
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


    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            vectorLayer
        ],
        // Create a view
        view: view,

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})
    });

    map.addInteraction(modify);

    map.on('singleclick', function (evt) {
        iconGeometry.setCoordinates(evt.coordinate);

        var gps = ol.proj.transform(evt.coordinate, 'EPSG:21781', 'EPSG:4326');

        $('#field_latitude').val(gps[1]);
        $('#field_longitude').val(gps[0]);

        map.removeInteraction(modify);
        modify = new ol.interaction.Modify({
            features: new ol.Collection([iconFeature]),
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


    }, iconFeature);

    iconFeature.on('change', function () {
        var gps = ol.proj.transform(iconGeometry.getCoordinates(), 'EPSG:21781', 'EPSG:4326');

        $('#field_latitude').val(gps[1]);
        $('#field_longitude').val(gps[0]);


    });


}
