var informationJSON;
var actualAircraftTypeList;
$(document).ready(function () {
    initializeForm();
    initializeChangeHandlers();

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
        $.each(informationJSON[0].aircraftTypeList[0].fieldList, function (j, field) {
            $("#" + field.name).hide();
        });
      /*  if($('#type_of_activity').val() == -1) {
            $('#type_of_aircraft').hide();
            $.each(informationJSON[0].aircraftTypeList[0].fieldList, function (j, field) {
                $("#" + field.name).hide();
            });
        }*/
        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.name) {
                if (activityType.aircraftTypeList.length > 1) {
                    $('#type_of_aircraft').show();
                    $('#select_type_of_aircraft').find('option').remove().end().append("<option value='-1'>Select the aircraft type</option>");
                    actualAircraftTypeList = activityType.aircraftTypeList;
                    $.each(activityType.aircraftTypeList, function (i, aircraftType) {
                        $('#select_type_of_aircraft').append($('<option>', {
                            text: aircraftType.name
                        }));
                    });
                }
                else {
                    $('#type_of_aircraft').hide();
                    $.each(activityType.aircraftTypeList[0].fieldList, function (i, field) {
                        if(field.active)
                            $("#" + field.name).show();
                        else
                            $("#" + field.name).hide();
                    });
                }
            }
        });
    });

    $(document).on('change', '#type_of_aircraft', function () {
        console.log($('#type_of_aircraft').val());
        if($('#type_of_aircraft').val() == "") {
            $.each(actualAircraftTypeList[0].fieldList, function (j, field) {
                $("#" + field.name).hide();
            });
        }

        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.name) {
                console.log(aircraftType.name);
                $.each(aircraftType.fieldList, function (j, field) {
                    if(field.active)
                        $("#" + field.name).show();
                    else
                        $("#" + field.name).hide();
                });
            }
        });

    });
}


