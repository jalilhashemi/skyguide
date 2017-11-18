var informationJSON;
$(document).ready(function () {

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
            alert("Fail!\nerror: " + errorThrown + "\nstatus: " + status);
        });
    /* $('#type_of_activity').append($('<option>', {
         text: 'text'
     }));*/


    $(document).on('change', '#type_of_activity', function () {
        // what happens on change of the activity type dropdown
        if ($('#type_of_activity').val() == informationJSON[0].name) {
            //alert(informationJSON[0].fieldList[1].active);
            if (informationJSON[0].fieldList[1].active)
                $('#localTimeField').show();
        }
    });
});

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.name
        }));
    });
}


