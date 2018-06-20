
/**
 * Get all data from input fields.
 */
function getAllInputData() {
    let arr = {};

    $('.data').each(
        function (index) {
            if (!$(this).hasClass('time') && !$(this).hasClass('altitude') && !$(this).hasClass('phone')) {
                const input = $(this);
                arr[input.attr('name')] = input.val();
            }
        }
    );

    return arr;
}

/**
 * Get all time data.
 * @returns {Array}
 */
function getTimes() {
    // get the time ranges
    let startArray = [];
    let endArray = [];
    let times = [];

    $.each($('input[name^="start"]'), function (i, time) {
        if ($(this).val() != "")
            startArray.push($(this).val() + ":00");
    });

    $.each($('input[name^="end"]'), function (i, time) {
        if ($(this).val() != "")
            endArray.push($(this).val() + ":00");
    });

    $.each(startArray, function (i, item) {
        times.push({"start": startArray[i], "end": endArray[i]});
    });

    return times;
}

/**
 * Get all drawing data.
 * @returns {Array}
 */
function getDrawings() {
    let drawings = [];

    let i = 1;
    $('.drawing').each(function () {

        let drawing = {};

        drawing['altitude'] = $(this).find('.altitude').val();

        if ($(this).hasClass('path')) {
            drawing['drawingType'] = 'Path';


        } else if ($(this).hasClass('circle')) {
            drawing['drawingType'] = 'Circle';
            drawing['radius'] = $(this).find('.radius').val();
        }
        else if ($(this).hasClass('polygon')) {
            drawing['drawingType'] = 'Polygon';

        }
        drawing['coordinates'] = [];

        $(this).find('.gps').each(function (index) {

            const coord = lv03toWgs84(validateCoordinate($(this)));
            drawing['coordinates'][index] = {
                'lat': coord[0],
                'lon': coord[1]
            };
        });
        drawings.push(drawing);
        i++;

    });

    return drawings;
}

/**
 * Get all data, submit to server and give feedback.
 */
function submitApplication() {
    $("#icon_loading").show();
    let data = {};

    data = getAllInputData();
    data['times'] = getTimes();
    data['drawings'] = getDrawings();
    data["heightType"] = $('input[name=heightType]:checked').val();
    data['phone'] = $('#input_applicant_phone').intlTelInput("getNumber", intlTelInputUtils.numberFormat.INTERNATIONAL);
    data['filltime'] = (new Date() - startTime) / 1000; // seconds

    if (data['heightType'] == undefined)
        data['heightType'] = 'none';

    if (data['aircraftType'] == '')
        data['aircraftType'] = 'none';

    console.log(data);

    asyncRequest('POST', restUrl + '/applications',
        function (json) {
            console.log(json);
            $("#icon_loading").hide();

            // quick hack for some activities
            if ($('#type_of_activity').val() === 'Sky Lantern' || $('#type_of_activity').val() === 'Toy Balloon'
                || $('#type_of_activity').val() === 'Sky Light / Laser' || $('#type_of_activity').val() === 'Weather Balloon'
                || $('#type_of_activity').val() === 'Firework' || $('#type_of_activity').val() === 'Captive Balloon'
                || $('#type_of_activity').val() === 'Kite' || $('#type_of_activity').val() === 'Model Rocket'
                || $('#type_of_activity').val() === 'Gas Balloon' || $('#type_of_activity').val() === 'Hot Air Balloon') {
                $('#submit_success').modal('show');
            } else if (checkIntersections()) {
                $('#submit_success').modal('show');
            }
            else {
                // no need of SUA
                $('#modal-success-nosua').modal('show');
            }
        },
        function (jqXHR) {
            showErrorModal(jqXHR, data);
        },
        JSON.stringify(data));

// submit to server
    /*  $.ajax({
          crossOrigin: true,
          url: restUrl + '/applications',
          type: 'POST',
          contentType: "application/json; charset=utf-8",
          data: JSON.stringify(data),
          dataType: 'json'
      })
          .done(function (json) {
                  console.log(json);
                  $("#icon_loading").hide();
                  if (checkIntersections()) {
                      $('#submit_success').modal('show');
                  }
                  else {
                      // no need of SUA
                      $('#modal-success-nosua').modal('show');

                  }

              }
          )
          .fail(function (jqXHR) {
              $("#icon_loading").hide();
              $('#modal-error').modal('show');
              errorLog = JSON.stringify(jqXHR.responseJSON) + "\n" + JSON.stringify(data);
          });*/
}
