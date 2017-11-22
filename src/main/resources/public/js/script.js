var informationJSON;
$(document).ready(function () {

    $.ajax({
        crossOrigin: true,
        url: 'http://86.119.39.142/information',
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            appendSelection(json);
            informationJSON = json;
        })
        .fail(function (xhr, status, errorThrown) {
            alert("Fail!\nerror: " + errorThrown + "\nstatus: " + status);
        });

    $(document).on('change', '#type_of_activity', function () {
        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.name) {
                $.each(activityType.fieldList, function (i, field) {
                    if(field.active){
                        $("#" + field.name).show();
                        }
                    else {
                        $("#" + field.name).hide();
                    }
                });
            }
        });

    });
});

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.name
        }));
    });
}


