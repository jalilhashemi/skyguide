var informationJSON;
var actualAircraftTypeList;
var map;
var view;
var lyr2 = ga.layer.create('ch.bazl.luftfahrtkarten-icao');

var pointFeature = new ol.Feature(new ol.geom.Point([700000, 190000]));
var dragInteraction = new ol.interaction.Modify({
    features: new ol.Collection([pointFeature]),
    style: new ol.style.Style({
        image: new ol.style.Icon(({
            src: 'img/marker.png'
        }))
    })
});

$(document).ready(function () {
    initializeForm();
    initializeChangeHandlers();
    initializeMap();

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
    $('#type_of_aircraft').parent().hide();
    $('#container_fields').empty();
}

function initializeChangeHandlers() {
    $(document).on('change', '#type_of_activity', function () {
        $('#container_fields').empty();
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
                    $('#container_fields').empty();

                    $.each(activityType.aircraftTypeList[0].fieldList, function (i, field) {
                        if (field.active) {

                            //$('#container_fields').append('<div class="form-group">\n' +
                            //     '        <label for="' + field.id + '">' + field.name + (field.mandatory ? '*' : '') + '</label>\n' +
                            //     '        <input type="text" class="form-control" id="' + field.id + '" placeholder= '+ field.name + (field.mandatory ? 'required':'')+'>' +
                            //     '<div class="invalid-feedback">\n' +
                            //     '            Please fill in all field with a * \n' +
                            //     '</div>    ' +
                            //     '</div>'
                            // );
                            if (field.mandatory) {
                                $('#container_fields').append('<div class="form-group">\n' +
                                    '        <label for="' + field.id + '">' + field.name + '*</label>\n' +
                                    '        <input type="text" class="form-control" id="' + field.id + '" placeholder= "' + field.name + '" required>' +
                                    '<div class="invalid-feedback">\n' +
                                    '            Please fill in all field with a * \n' +
                                    '</div>    ' +
                                    '</div>'
                                );
                            }
                            else {
                                $('#container_fields').append('<div class="form-group">\n' +
                                    '        <label for="' + field.id + '">' + field.name + '</label>\n' +
                                    '        <input type="text" class="form-control" id="' + field.id + '" placeholder= "' + field.name + '">' +
                                    '</div>');
                            }
                            var counter = 0;
                            $.each(field.options, function (i, option) {
                                if (option.active)
                                    counter++;
                            });
                            if (counter > 0) {
                                var radioDiv = $("<div>").attr('class', 'form-group');
                                $.each(field.options, function (i, radio) {
                                    if (radio.active) {
                                        radioDiv.append('<div class="form-check form-check-inline" data-toggle="tooltip" data-placement="top"\n' +
                                            '             title="' + radio.tooltip + '"><label class="form-check-label">\n' +
                                            '<input class="form-check-input" type="radio" name="inlineRadioOptions" id="' + radio.id + '"' +
                                            'value="' + radio.name + '"> ' + radio.name +
                                            '</label></div>');

                                    }
                                });
                                $('#container_fields').append(radioDiv);
                                $('[data-toggle="tooltip"]').tooltip();
                            }
                        }
                    });
                }
            }
        });
    });

    $(document).on('change', '#type_of_aircraft', function () {
        $('#container_fields').empty();


        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.name) {
                $.each(aircraftType.fieldList, function (i, field) {
                    if (field.active) {
                        // $('#container_fields').append('<div class="form-group">\n' +
                        //     '        <label for="' + field.id + '">' + field.name + (field.mandatory ? ' *' : '') + '</label>\n' +
                        //     '        <input type="text" class="form-control" id="' + field.id + '" placeholder="' + field.name + '">\n' +
                        //     '    </div>'
                        // );

                        if (field.mandatory) {
                            $('#container_fields').append('<div class="form-group">\n' +
                                '        <label for="' + field.id + '">' + field.name + '* </label>\n' +
                                '        <input type="text" class="form-control" id="' + field.id + '" placeholder= "' + field.name + '" required>' +
                                '<div class="invalid-feedback">\n' +
                                '            Please fill in all field with a * \n' +
                                '</div>    ' +
                                '</div>'
                            );
                        }
                        else {
                            $('#container_fields').append('<div class="form-group">\n' +
                                '        <label for="' + field.id + '">' + field.name + '</label>\n' +
                                '        <input type="text" class="form-control" id="' + field.id + '" placeholder= "' + field.name + '">' +
                                '</div>');
                        }


                        var counter = 0;
                        $.each(field.options, function (i, option) {
                            if (option.active)
                                counter++;
                        });
                        if (counter > 0) {
                            var radioDiv = $("<div>").attr('class', 'form-group');
                            $.each(field.options, function (i, radio) {
                                if (radio.active) {
                                    radioDiv.append('<div class="form-check form-check-inline" data-toggle="tooltip" data-placement="top"\n' +
                                        '             title="' + radio.tooltip + '"><label class="form-check-label">\n' +
                                        '<input class="form-check-input" type="radio" name="inlineRadioOptions" id="' + radio.id + '"' +
                                        'value="' + radio.name + '"> ' + radio.name +
                                        '</label></div>');

                                }
                            });
                            $('#container_fields').append(radioDiv);
                            $('[data-toggle="tooltip"]').tooltip();
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

    $(document).on('focusout', '#field_location', function () {

        /*map.geocode('Payerne');*/

        var lat = 46.84089;
        var lon = 7.46485;

        lat = $(this).val().split(',')[0];
        lon = $(this).val().split(',')[1];

        var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

        // Zoom on the position
        map.getView().setCenter(loc);
        map.getView().setResolution(50);

        var mapVectorSource = new ol.source.Vector({
            features: []
        });

        var mapVectorLayer = new ol.layer.Vector({
            source: mapVectorSource
        });

        map.addLayer(mapVectorLayer);

        iconStyle = new ol.style.Style({
            image: new ol.style.Icon(({
                anchor: [0.5, 1],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                src: 'img/marker.png',
            }))
        });

        var marker = createMarker(loc, iconStyle);

        mapVectorSource.addFeature(marker);

    });
}

function initializeMap() {
    view = new ol.View({
        resolution: 250,

        // Define a coordinate CH1903 (EPSG:21781) for the center of the view
        center: [700000, 190000]
    });
    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [pointFeature]
                }),
                style: new ol.style.Style({
                    image: new ol.style.Icon(({
                        src: 'img/marker.png'
                    }))
                })
            })
        ],
        // Create a view
        view: view,

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})
    });

    map.addInteraction(dragInteraction)

    pointFeature.on('change',function(){
        console.log('Feature Moved To:' + this.getGeometry().getCoordinates());
    },pointFeature);
}

function createMarker(location, style){
    var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(location)
    });
    iconFeature.setStyle(style);

    return iconFeature
}