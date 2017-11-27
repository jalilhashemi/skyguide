var informationJSON;
var actualAircraftTypeList;
var map;
var lyr2 = ga.layer.create('ch.bazl.luftfahrtkarten-icao');
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
                            $('#container_fields').append('<div class="form-group">\n' +
                                '        <label for="' + field.id + '">' + field.name + '</label>\n' +
                                '        <input type="text" class="form-control" id="' + field.id + '" placeholder="' + field.name + '">\n' +
                                '    </div>'
                            );
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
                        $('#container_fields').append('<div class="form-group">\n' +
                            '        <label for="' + field.id + '">' + field.name + '</label>\n' +
                            '        <input type="text" class="form-control" id="' + field.id + '" placeholder="' + field.name + '">\n' +
                            '    </div>'
                        );
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
}

function initializeMap() {
    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe')
        ],
        // Create a view
        view: new ol.View({

            // Define the default resolution
            // 10 means that one pixel is 10m width and height
            // List of resolution of the WMTS layers:
            // 650, 500, 250, 100, 50, 20, 10, 5, 2.5, 2, 1, 0.5, 0.25, 0.1
            resolution: 250,

            // Define a coordinate CH1903 (EPSG:21781) for the center of the view
            center: [660000, 190000]
        }),

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})
    });
}