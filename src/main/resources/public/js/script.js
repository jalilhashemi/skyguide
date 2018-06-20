// FORM --------------------------------------------------------------------------------------------------------
/**
 * Global information JSON data with all the field configurations.
 * @type {data} JSON
 */
let informationJSON;

/**
 * Time measurement for user interaction.
 * @type {number}
 */
let startTime;

/**
 * Global list of the current aircraft types.
 * @type {data} JSON
 */
let aircraftTypes;

/**
 * Current global time index.
 * @type {number}
 */
let timeIndex = 0;

/**
 * Current global drawing index.
 * @type {number}
 */
let drawingIndex = 0;

/**
 * Initialize the whole form.
 */
function initializeForm() {

    hideGeoFields();
    hideMap();
    hideAircraftType();
    hideLoading();

    getFormInformation();
    initDateRangePicker();
    initTooltips();
    initMap();
    initDrawTool();
    initTelTool();

    initializeChangeHandlers();

}

/**
 * Initialize the phone library.
 */
function initTelTool() {
    $("#input_applicant_phone").intlTelInput({
        nationalMode: true,
        initialCountry: "auto",
        geoIpLookup: function (callback) {
            $.get('https://ipinfo.io', function () {
            }, "jsonp").always(function (resp) {
                var countryCode = (resp && resp.country) ? resp.country : "";
                callback(countryCode);
            });
        },
        utilsScript: "lib/utils.js"
    });
}

/**
 * Initialize the Bootstrap tooltips for the height type.
 */
function initTooltips() {
    $('[data-toggle="tooltip"]').tooltip();
}

/**
 * Initialize the date range picker library.
 */
function initDateRangePicker() {
    $(function () {
        $('input[name="dateFromUntil"]').daterangepicker({
            locale: {
                format: 'DD.MM.YYYY'
            }
        });
    });
}

/**
 * Fetch the information JSON data and save to global variable.
 */
function getFormInformation() {
    asyncRequest('GET', restUrl + '/information',
        function (data) {
            appendSelection(data);
            informationJSON = data;
        },
        function (jqXHR) {
            showErrorModal(jqXHR);
        });
}

/**
 * Shows a modal with the request of the altitude and the height type.
 */
function showAltitudeModal() {
    resetInputField($('#input-altitude'));
    $('#modal-altitude').modal('show');
    $('#height-type').text($('input[name=heightType]:checked').val());
}

/**
 * Reset the input field to init state.
 * @param field
 */
function resetInputField(field) {
    resetInputValidation(field);
    field.val("");
}

/**
 * Reset the input validation markup of a field.
 * @param field
 */
function resetInputValidation(field) {
    field.removeClass("is-invalid");
    field.removeClass("is-valid");
}

/**
 * Insert text and optional title into the instruction alert.
 * @param text
 * @param title
 */
function showInstruction(text, title) {
    $('#map-instructions').show();
    $('#map-instructions-text').text(text);
    if (title !== undefined)
        $('#map-instructions-title').text(title);
}

/**
 * Hide the loading icon.
 */
function hideLoading() {
    $("#icon_loading").hide();
}

/**
 * Initialize the activity type dropdown with the activity type list.
 * @param data activity type list
 */
function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.label
        }));
    });
}

/**
 * Remove all additional time fields.
 */
function removeTimeFields() {
    $('.time_field').remove();
}

/**
 * Empty the whole form data.
 */
function emptyForm() {
    $('#container_fields').find('input').each(function () {
        $(this).removeClass('is-invalid');
        $(this).removeClass('is-valid');
        if ($(this).attr('name') == 'heightType') {
            $(this).checked = false;
        } else {
            $(this).val("");
        }

    });
    removeTimeFields();
    resetMap();
}

/**
 * Show an error modal with the error response and optional data of the form.
 * @param jqXHR
 * @param data
 */
function showErrorModal(jqXHR, data) {
    $('#modal-error').modal('show');
    $('#btn-report').attr('error-log', JSON.stringify(jqXHR.responseJSON) + "\n" + JSON.stringify(data));
}

/**
 * Resets all input fields.
 */
function resetAllInputFields() {
    $('input.data.activity-data').val('');
    $('input.data.activity-data').prop('required', false);
}

/**
 * Resets the state of the radio buttons.
 */
function resetRadio() {
    $('.height-type-radio').prop('checked', false);
    $('.height-type-radio').prop('required', false);
}

/**
 * Show all active geographic fields and initialize.
 * @param fieldList
 */
function showGeoFields(fieldList) {
    // quick hack for types without map
    if ($('#type_of_activity').val() === 'Sky Lantern' || $('#type_of_activity').val() === 'Toy Balloon'
        || $('#type_of_activity').val() === 'Sky Light / Laser' || $('#type_of_activity').val() === 'Weather Balloon'
        || $('#type_of_activity').val() === 'Firework' || $('#type_of_activity').val() === 'Captive Balloon'
        || $('#type_of_activity').val() === 'Kite' || $('#type_of_activity').val() === 'Model Rocket'
        || $('#type_of_activity').val() === 'Gas Balloon' || $('#type_of_activity').val() === 'Hot Air Balloon') {
        $('.geo-map').hide();
    } else {
        $('.geo-map').show();
    }

    $('.geo-static').show();
    map.updateSize();


    $.each(fieldList, function (i, field) {
        processField(field);
    });
}


/**
 * Hide all geographic fields.
 */
function hideGeoFields() {
    $('.geo').hide();
    hideMap();
}

/**
 * Hide and reset the aircraft type dropdown.
 */
function hideAircraftType() {
    $('.aircraft-type').hide();
    $('#type_of_aircraft').prop('required', false);
    $('#type_of_aircraft').val('');
    validateField($('#type_of_aircraft'), false);
}

/**
 * Hide the map container.
 */
function hideMap() {
    $('#map-container').hide();
}

/**
 * Initialize the aircraft type dropdown.
 * @param activityType
 */
function showAircraftType(activityType) {
    $('.aircraft-type').show();

    // remove all options and append default text
    $('#type_of_aircraft').find('option').remove().end().append("<option value>Select the aircraft type</option>");

    // append all aircraft types
    $.each(activityType.aircraftTypeList, function (i, aircraftType) {
        $('#type_of_aircraft').append($('<option>', {
            text: aircraftType.label
        }));
    });

    $('#type_of_aircraft').prop('required', true);
}


/**
 * Displays a field with the label and the mandatory * sign
 * @param field The JSON field object from /information service
 */
function showField(field) {
    $('#' + field.id).parent().show();
    $('#' + field.id).parent().children('label').remove();
    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.label + (field.mandatory ? '*' : '')
        + '</label>\n');
    $('#' + field.id)
        .attr('placeholder', field.placeholder)
        .prop('required', field.mandatory ? true : false);
}

/**
 * Create a new coordinate field within that drawing div.
 * @param drawingDiv
 */
function addCoordinateField(drawingDiv) {
    var template = $('#coordinate_path_polygon_template'),
        clone = template
            .clone()
            .removeAttr('id')
            .insertAfter(drawingDiv.children().last());

}

/**
 * Remove the given coordinate field.
 * @param gpsRow
 */
function removeCoordinateField(gpsRow) {
    gpsRow.remove();
}

/**
 * Decide type of field and initialize.
 * @param field
 */
function processField(field) {
    if (field.active) {
        if (field.id.substring(0, 6) === 'radio_') {
            $('#' + field.id).parent().show();
            $('#' + field.id).parent().parent().parent().show();
            $('#' + field.id).prop('required', true);
        }
        else {
            showField(field);
        }
    }

}

/**
 * Creates new drawing input form of type and returns id.
 * @param type
 * @returns {number}
 */
function addDrawingDiv(type) {
    drawingIndex++;
    var template;
    if (type === 'Path') {
        template = $('#path_template');
    } else if (type === 'Circle') {
        template = $('#circle_template');
    } else if (type === 'Polygon') {
        template = $('#polygon_template');
    }

    var clone = template
        .clone()
        .addClass('drawing')
        .attr('id', 'drawing' + drawingIndex)
        //.prop('required', true)
        // .attr('data-drawing-index', drawingIndex)
        .prepend('<div class="row"><div class="col-md-2">' +
            '<h3>' + type + ' ' + drawingIndex + '</h3></div>' +
            '<div class="col-md-2"><button tabindex="-1" type="button" class="btn btn-primary btn-red-sky mini remove-drawing">' +
            'Remove</button></div></div>');


    clone.find('.altitude').addClass('data');
    clone.find('input').prop('required', true);

    clone.insertBefore($('#map-container'));


    return drawingIndex;
}

/*
function addPathDrawingDiv() {
    drawingIndex++;
    var template = $('#path_template'),
        clone = template
            .clone()
            .addClass('drawing')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            // .attr('data-drawing-index', drawingIndex)
            .prepend('<div class="row"><div class="col-md-2">' +
                '<h3>Path ' + drawingIndex + '</h3></div>' +
                '<div class="col-md-2"><button tabindex="-1" type="button" class="btn btn-primary btn-red-sky mini remove-drawing">' +
                'Remove</button></div></div>');


    clone.find('.altitude').addClass('data');
    clone.find('input').prop('required', true);

    clone.insertBefore($('#map-container'));


    return drawingIndex;
}

function addPolygonDrawingDiv() {
    drawingIndex++;
    var template = $('#polygon_template'),
        clone = template
            .clone()
            .addClass('drawing')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            // .attr('data-drawing-index', drawingIndex)
            .prepend('<div class="row"><div class="col-md-2">' +
                '<h3>Polygon ' + drawingIndex + '</h3></div>' +
                '<div class="col-md-2"><button tabindex="-1" type="button" class="btn btn-primary btn-red-sky mini remove-drawing">' +
                'Remove</button></div></div>');
    clone.find('.altitude').addClass('data');
    clone.find('input').prop('required', true);

    clone.insertBefore($('#map-container'));

    return drawingIndex;
}


function addCircleDrawingDiv() {
    drawingIndex++;
    var template = $('#circle_template'),
        clone = template
            .clone()
            .addClass('drawing')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            //  .attr('data-drawing-index', drawingIndex)
            .prepend('<div class="row"><div class="col-md-2">' +
                '<h3>Circle ' + drawingIndex + '</h3></div>' +
                '<div class="col-md-2"><button tabindex="-1" type="button" class="btn btn-primary btn-red-sky mini remove-drawing">' +
                'Remove</button></div></div>');
    clone.find('.altitude').addClass('data');
    clone.find('input').prop('required', true);

    clone.insertBefore($('#map-container'));

    return drawingIndex;
}*/


// submit ?

/**
 * Get all data from input fields.
 */
function getAllInputData() {
    let arr = {};

    $('.data').each(
        function (index) {
            if (!$(this).hasClass('time') && !$(this).hasClass('altitude') && !$(this).hasClass('phone')) {
                var input = $(this);
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
    var startArray = [];
    var endArray = [];
    var times = [];

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
    var drawings = [];

    var i = 1;
    $('.drawing').each(function () {

        var drawing = {};

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

            var coord = lv03toWgs84(validateCoordinate($(this)));
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
    var data = {};

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


// VALIDATION --------------------------------------------------------------------------------------------------------

/**
 * Check the whole form's valid state.
 * @returns {boolean}
 */
function validateForm() {

    let isValid = true;

    $('.data').each(function (index, item) {
        let validData = true;
        if ($(this).hasClass('heightType')) {
            validData = $('input[name=heightType]:checked').val() != undefined;
            $(this).find('input').each(function () {
                validateField($(this), validData);
            });
        } else if ($(this).hasClass('phone')) {
            if ($.trim($(this).val())) {
                validData = $(this).intlTelInput("isValidNumber")
                validateField($(this), validData);
            }
        }
        else {
            validData = item.checkValidity();
            validateField($(this), validData);
            if ($(this).hasClass('time')) {
                validData = validateTimes($(this));

            }

            console.log($(this).attr('id') + ": " + isValid);
            console.log($(this));
        }
        if (!validData)
            isValid = false;
    });

    // quick hack for types without map
    if ($('#type_of_activity').val() === 'Sky Lantern' || $('#type_of_activity').val() === 'Toy Balloon'
        || $('#type_of_activity').val() === 'Sky Light / Laser' || $('#type_of_activity').val() === 'Weather Balloon'
        || $('#type_of_activity').val() === 'Firework' || $('#type_of_activity').val() === 'Captive Balloon'
        || $('#type_of_activity').val() === 'Kite' || $('#type_of_activity').val() === 'Model Rocket'
        || $('#type_of_activity').val() === 'Gas Balloon' || $('#type_of_activity').val() === 'Hot Air Balloon') {
        // no map
    }
    else {
        if (!validateAllDrawings())
            isValid = false;
    }


    return isValid;
}


function validateDrawing(drawingId) {
    let isValid = true;

    const drawingDiv = $('#' + drawingId);

    let validData = true;
    drawingDiv.find('.gps').each(function (index, item) {
        validData = (validateCoordinate($(this)) != null)

    });
    validData = drawingDiv.find('.altitude')[0].checkValidity();
    validateField(drawingDiv.find('.altitude'), validData);


    if (drawingDiv.find('.radius').length) {
        validData = drawingDiv.find('.radius')[0].checkValidity();
        validateField(drawingDiv.find('.radius'), validData);
    }
    if (!validData)
        isValid = false;


    return isValid;
}


/**
 * Check the valid state of all drawings.
 */
function validateAllDrawings() {
    let isValid = true;

    if (!source.getFeatures().length) {
        isValid = false;
        validateMap(false, 'Please draw at least one drawing.');
    }

    let validDrawing = true;
    $('.drawing').each(function () {
        validDrawing = validateDrawing($(this).attr('id'));
        if (!validDrawing)
            isValid = false;
    });
    validateMap(isValid);


    return isValid;
}

/**
 * Set the map's validation markup and error message.
 * @param isValid
 * @param message
 */
function validateMap(isValid, message) {
    if (isValid) {
        $('canvas').css("border", 'none');
        $('#map-feedback').hide();
    }
    else {
        $('canvas').css("border", '1px solid #dc3545');
        $('#map-feedback').show();
        $('#map-feedback').html(message);
    }
}

/**
 * Check weather the time fields are valid.
 * @param field
 * @returns {boolean}
 */
function validateTimes(field) {
    var type = field.attr("name").split('[')[0];
    var end, start;

    if (type == "start") {
        start = field;
        end = start.parent().parent().find($('input[name^="end"]'));
    }
    else if (type == "end") {
        end = field;
        start = end.parent().parent().find($('input[name^="start"]'));
    }
    if (start[0].checkValidity() && end[0].checkValidity()) {
        var startDt = new Date(2010, 12, 21, 9, start.val().split(':')[0], start.val().split(':')[1]);
        var endDt = new Date(2010, 12, 21, 9, end.val().split(':')[0], end.val().split(':')[1]);
        if (startDt.getTime() >= endDt.getTime()) {
            end.parent().find('.invalid-feedback').html("The until time must be after the from time. If you want midnight, just provide 23:59");
            validateField(end, false);
            return false;
        }
        else {
            validateField(start, true);
            return true;
        }
    }
}

/**
 * Check weather the radius field is valid.
 * @param field
 * @returns {boolean}
 */
function validateRadius(field) {
    let isValid = field.val() > 0 && field.val() <= 500;
    validateField(field, isValid);
    return isValid;
}

/**
 * Validate and convert coordinate field from every type of projection.
 * @param field
 * @returns {*} LV03 coordinate
 */
function validateCoordinate(field) {
    const defaultEpsg = 'EPSG:21781';
    const extent = map.getView().getProjection().getExtent();

    let position;

    const query = field.val();

    // source of the search transition code
    // https://github.com/geoadmin/mf-geoadmin3/blob/17ba14f3047bf4692752b36a1295172fa396177d/src/components/search/SearchService.js

    const DMSDegree = '[0-9]{1,2}[°|º]\\s*';
    const DMSMinute = '[0-9]{1,2}[\'|′]';
    const DMSSecond = '(?:\\b[0-9]+(?:\\.[0-9]*)?|\\.' +
        '[0-9]+\\b)("|\'\'|′′|″)';
    const DMSNorth = '[N]';
    const DMSEast = '[E]';
    const regexpDMSN = new RegExp(DMSDegree +
        '(' + DMSMinute + ')?\\s*' +
        '(' + DMSSecond + ')?\\s*' +
        DMSNorth, 'g');
    const regexpDMSE = new RegExp(DMSDegree +
        '(' + DMSMinute + ')?\\s*' +
        '(' + DMSSecond + ')?\\s*' +
        DMSEast, 'g');
    const regexpDMSDegree = new RegExp(DMSDegree, 'g');
    const regexpCoordinate = new RegExp(
        '([\\d\\.\']+)[\\s,]+([\\d\\.\']+)' +
        '([\\s,]+([\\d\\.\']+)[\\s,]+([\\d\\.\']+))?');

    const roundCoordinates = function (coords) {
        return [Math.round(coords[0] * 1000) / 1000,
            Math.round(coords[1] * 1000) / 1000];
    };

    // Parse degree EPSG:4326 notation
    const matchDMSN = query.match(regexpDMSN);
    const matchDMSE = query.match(regexpDMSE);
    if (matchDMSN && matchDMSN.length === 1 &&
        matchDMSE && matchDMSE.length === 1) {
        let northing = parseFloat(matchDMSN[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
        let easting = parseFloat(matchDMSE[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
        const minuteN = matchDMSN[0].match(DMSMinute) ?
            matchDMSN[0].match(DMSMinute)[0] : '0';
        northing = northing +
            parseFloat(minuteN.replace('\'', '').replace('′', '')) / 60;
        const minuteE = matchDMSE[0].match(DMSMinute) ?
            matchDMSE[0].match(DMSMinute)[0] : '0';
        easting = easting +
            parseFloat(minuteE.replace('\'', '').replace('′', '')) / 60;
        const secondN =
            matchDMSN[0].match(DMSSecond) ?
                matchDMSN[0].match(DMSSecond)[0] : '0';
        northing = northing + parseFloat(secondN.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
        const secondE = matchDMSE[0].match(DMSSecond) ?
            matchDMSE[0].match(DMSSecond)[0] : '0';
        easting = easting + parseFloat(secondE.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
        position = ol.proj.transform([easting, northing],
            'EPSG:4326', defaultEpsg);
        if (ol.extent.containsCoordinate(extent, position)) {
            position = roundCoordinates(position);

        }
    }

    const match = query.match(regexpCoordinate);
    if (match) {
        let left = parseFloat(match[1].replace(/'/g, ''));
        let right = parseFloat(match[2].replace(/'/g, ''));
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

// not working at the moment

        // Match LV03 coordinates
        asyncRequest('GET', lv03toLv95Url + '?easting=' + position[0] + '&northing=' + position[1],
            function (pos) {
                if (ol.extent.containsCoordinate(extent, pos)) {
                    position = roundCoordinates(pos);
                }
            },
            function (jqXHR) {
                //showErrorModal(jqXHR);
            });

        /*
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
                      .fail(function (jqXHR) {

                          $('#modal-error').modal('show');
                          errorLog = JSON.stringify(jqXHR.responseJSON);

                      });
        */
    }

    validateField(field, position != null);
    return position;
}

/**
 * Set the valid state of the field markup.
 * @param field
 * @param isValid
 */
function validateField(field, isValid) {
    if (isValid) {
        field.addClass('is-valid');
        field.removeClass('is-invalid');

    }
    else {
        field.removeClass('is-valid');
        field.addClass('is-invalid');
    }
}


// MAP --------------------------------------------------------------------------------------------------------

/**
 * The measure tooltip element.
 * @type {Element}
 */
let measureTooltipElement;

/**
 * Overlay to show the measurement.
 * @type {ol.Overlay}
 */
let measureTooltip;

/**
 * Openlayer Geo Admin map.
 * @type {ga.Map}
 */
let map;

/**
 * Modify Tool.
 */
let Modify;

/**
 * Draw Tool.
 */
let Draw;

/**
 * Default position coordinates.
 * @type {number[]}
 */
const centerPos = [46.81, 8.31];

/**
 * Vector Source where the drawings are saved.
 * @type {ol.source.Vector}
 */
let source = new ol.source.Vector();

/**
 * Vector based Layer.
 * @type {ol.layer.Vector}
 */
let vector = new ol.layer.Vector({
    source: source
});

/**
 * CTR vector layer from KML.
 * @type {ol.layer.Vector}
 */
const ctr = new ol.layer.Vector({
    source: new ol.source.Vector({
        url: 'data/ctr.kml',
        format: new ol.format.KML({
            extractStyles: false,
            projection: 'EPSG:4326'
        })
    }),
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 100, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: 'rgba(255, 100, 0, 0)',
            width: 2
        })
    })
});

/**
 * TMA vector layer from KML.
 * @type {ol.layer.Vector}
 */
const tma = new ol.layer.Vector({
    source: new ol.source.Vector({
        url: 'data/tma.kml',
        format: new ol.format.KML({
            extractStyles: false,
            projection: 'EPSG:4326'
        })

    }),
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 255, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: 'rgba(255, 255, 0, 0)',
            width: 2
        })
    })
});

/**
 * Set the map into the init state.
 */
function resetMap() {
    setMapView(centerPos, 400);
}

/**
 * Clear all drawings from the map and reset.
 */
function clearMap() {
    // remove drawings
    source.clear();
    $('.drawing').each(function () {
        $(this).remove();
    });
    drawingIndex = 0;
    resetMap();
}

/**
 * Update the drawing in the map from the form with id and form div.
 * @param drawingId
 * @param drawingDiv
 */
function updateDrawings(drawingId, drawingDiv) {
    var coordinates = [];

    drawingDiv.find('.gps').each(function (index, item) {
        var gps = validateCoordinate($(this));
        if (gps) {
            coordinates.push(gps);
        }
    });

    if (drawingDiv.hasClass('polygon') && coordinates.length > 2) {
        coordinates.push(coordinates[0]);

        console.log("polygon " + coordinates);
        if (source.getFeatureById(drawingId) != null) {
            source.getFeatureById(drawingId).getGeometry().setCoordinates([coordinates]);
            map.getView().fitExtent(source.getFeatureById(drawingId).getGeometry().getExtent(), map.getSize());
        }
        else {
            var feature = new ol.Feature({
                geometry: new ol.geom.Polygon([coordinates])
            });
            feature.setId(drawingId);
            styleDrawing(feature, drawingId.split("drawing")[1]);
            source.addFeature(feature);
            map.getView().fitExtent(feature.getGeometry().getExtent(), map.getSize());// {padding: [170, 50, 30, 150], constrainResolution: false});

        }

    }

    else if (drawingDiv.hasClass('path') && coordinates.length > 1) {
        console.log("path " + coordinates);

        if (source.getFeatureById(drawingId) != null) {
            source.getFeatureById(drawingId).getGeometry().setCoordinates(coordinates);
            map.getView().fitExtent(source.getFeatureById(drawingId).getGeometry().getExtent(), map.getSize());

        }
        else {
            var feature = new ol.Feature({
                geometry: new ol.geom.LineString(coordinates)
            });
            feature.setId(drawingId);
            styleDrawing(feature, drawingId.split("drawing")[1]);

            source.addFeature(feature);
            map.getView().fitExtent(feature.getGeometry().getExtent(), map.getSize());// {padding: [170, 50, 30, 150], constrainResolution: false});

        }

    } else if (drawingDiv.hasClass('circle') && coordinates.length > 0 && drawingDiv.find('.radius')[0].checkValidity()) {
        console.log("circle " + coordinates);

        var radius = calculateRadius(drawingDiv.find('.radius').val(), coordinates[0]);
        if (source.getFeatureById(drawingId) != null) {
            source.getFeatureById(drawingId).getGeometry().setCenterAndRadius(coordinates[0], radius);
            map.getView().fitExtent(source.getFeatureById(drawingId).getGeometry().getExtent(), map.getSize());
        }
        else {

            var feature = new ol.Feature({
                geometry: new ol.geom.Circle(coordinates[0], radius)
            });
            feature.setId(drawingId);
            styleDrawing(feature, drawingId.split("drawing")[1]);
            source.addFeature(feature);
            map.getView().fitExtent(feature.getGeometry().getExtent(), map.getSize());// {padding: [170, 50, 30, 150], constrainResolution: false});

        }
    }

    validateDrawing(drawingId);
    Modify.setActive(false);
}

/**
 * Calculates the real radius of a circle in Meters.
 * @param value
 * @param coordinate
 * @returns {number}
 */
function calculateRadius(value, coordinate) {
    var view = map.getView();
    var projection = view.getProjection();
    var resolutionAtEquator = view.getResolution();
    var pointResolution = projection.getPointResolution(resolutionAtEquator, coordinate);
    var resolutionFactor = resolutionAtEquator / pointResolution;

    return (value / ol.proj.METERS_PER_UNIT.m) * resolutionFactor;
}

/**
 * Sets layer with an index visible or hidden.
 * @param layerIndex
 * @param isVisible
 */
function setLayerVisible(layerIndex, isVisible) {
    //  map.getLayers()[layerIndex].setVisible(isVisible);
    map.getLayers().forEach(function (layer, idx) {
        if (idx == layerIndex)
            layer.setVisible(isVisible);
    });
};

/**
 * Cleans a KML layer for using with Geo Admin.
 * @param layer
 */
function cleanUpKml(layer) {
    layer.getSource().getFeatures().forEach(function (feature) {
        var coordinates = [];
        feature.getGeometry().getCoordinates()[0].forEach(function (coords) {
            coords.splice(2, 1)
            coordinates.push(coords);
        });
        feature.getGeometry().setCoordinates([coordinates]);
    });
}

/**
 * Initialize the Geo Admin map with all layers and options.
 */
function initMap() {

    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            ga.layer.create('ch.bazl.luftfahrtkarten-icao'),
            tma,
            ctr,
            vector,
        ],
        crossOrigin: 'null',

        // disable scrolling on map
        // interactions: ol.interaction.defaults({mouseWheelZoom: false})

    });

    resetMap();
    setLayerVisible(1, false);

}

/**
 * Set the maps view to a given coordinate and resolution.
 * @param gps
 * @param res
 */
function setMapView(gps, res) {
    map.getView().setCenter(wgs84toLv03(gps));
    map.getView().setResolution(res);
}


// drawing tool.. ?

/**
 * Fill the form inputs with a given feature.
 * @param feature
 * @param drawingId
 * @param type Drawing type
 */
function fillDrawingDiv(feature, drawingId, type) {

    var drawingDiv = $('#drawing' + drawingId);
    var coordinates;
    var radius;

    if (type === 'Polygon') {
        coordinates = feature.getGeometry().getCoordinates()[0];
        coordinates.splice(coordinates.length - 1, 1);
    } else if (type === 'Path') {
        coordinates = feature.getGeometry().getCoordinates();
    } else if (type === 'Circle') {
        coordinates = [feature.getGeometry().getCenter()];
        radius = parseInt(feature.getGeometry().getRadius());
    }

    while (drawingDiv.find('.gps').length < coordinates.length)
        addCoordinateField(drawingDiv);

    coordinates.forEach(function (item, index) {
        coordinates[index] = lv03toWgs84(item);
    });

    drawingDiv.find('.gps').each(function (index) {
        $(this).val(parseFloat((coordinates[index][0]).toFixed(3)) + ', ' + parseFloat((coordinates[index][1]).toFixed(3)));
        validateCoordinate($(this));
    });

    drawingDiv.find('.radius').val(radius);
    validateRadius(drawingDiv.find('.radius'));
}

/*
function fillDrawingPathDiv(feature, drawingId) {
    //  feature.setId("drawing" + drawingId);
    var coordinates = feature.getGeometry().getCoordinates();
    var drawingDiv = $('#drawing' + drawingId);

    while (drawingDiv.find('.gps').length < coordinates.length)
        addCoordinateField(drawingDiv);

    coordinates.forEach(function (item, index) {
        coordinates[index] = ol.proj.transform(item, 'EPSG:21781', 'EPSG:4326');
    });

    drawingDiv.find('.gps').each(function (index) {
        $(this).val(parseFloat((coordinates[index][1]).toFixed(3)) + ', ' + parseFloat((coordinates[index][0]).toFixed(3)));
        validateCoordinate($(this));
    });

}

function fillDrawingCircleDiv(feature, drawingId) {
    feature.setId("drawing" + drawingId);
    var coordinate = feature.getGeometry().getCenter();
    var radius = parseInt(feature.getGeometry().getRadius());
    var drawingDiv = $('#drawing' + drawingId);

    coordinate = ol.proj.transform(coordinate, 'EPSG:21781', 'EPSG:4326');

    drawingDiv.find('.gps').val(parseFloat((coordinate[1]).toFixed(3)) + ', ' + parseFloat((coordinate[0]).toFixed(3)))
    drawingDiv.find('.radius').val(radius);
    validateRadius(drawingDiv.find('.radius'));
    validateCoordinate(drawingDiv.find('.gps'));
}*/

/**
 * Adds a feature added by the map into the application.
 * @param feature
 * @param type
 */
function addDrawing(feature, type) {
    const drawingId = addDrawingDiv(type);
    styleDrawing(feature, drawingId);
    feature.setId("drawing" + drawingId);

    fillDrawingDiv(feature, drawingId, type)

    measureTooltipElement.parentNode.removeChild(measureTooltipElement);
    showAltitudeModal();

    showInstruction('You finally added a new ' + type + ' to your drawings.\nYou can modify it with the ' +
        (type !== 'Circle' ? 'Modify tool or ' : '') + 'in the fields above.', 'Created ' + type + '!');
}

/**
 * Creates a new measure tooltip.
 */
function createMeasureTooltip() {
    measureTooltipElement = document.createElement('div');
    measureTooltipElement.className = 'tooltip tooltip-measure';
    measureTooltip = new ol.Overlay({
        element: measureTooltipElement,
        offset: [0, -15],
        positioning: 'bottom-center'
    });
    map.addOverlay(measureTooltip);
}

/**
 * Initialize the drawing and modifying tools.
 */
function initDrawTool() {

    Modify = {
        init: function () {
            this.select = new ol.interaction.Select({
                layers: function (layer) {
                    return layer == vector;
                }
            });
            map.addInteraction(this.select);

            this.modify = new ol.interaction.Modify({
                features: this.select.getFeatures()
            });
            map.addInteraction(this.modify);


            this.setEvents();
        },
        setEvents: function () {
            var selectedFeatures = this.select.getFeatures();

            this.select.on('change:active', function () {
                selectedFeatures.forEach(selectedFeatures.remove, selectedFeatures);
            });

            selectedFeatures.on('remove', function (e) {
                styleDrawing(e.element, e.element.getId().split('drawing')[1]);
                showInstruction("Select a drawing you want to modify by clicking on it.");
            });

            selectedFeatures.on('add', function (e) {
                styleModify(e.element);

                showInstruction("Now you can drag a point. You can add a Point by dragging on a line. Remove a point by only click on it.")

                e.element.on('change', function (e) {

                    var drawingId = $(this)[0].getId();
                    var drawingDiv = $('#' + drawingId);

                    if (!drawingDiv.hasClass('circle')) {
                        var geometry = $(this)[0].getGeometry().getCoordinates();
                        var gps = [];


                        if (drawingDiv.hasClass('polygon')) {
                            gps = listLv03toWgs84(geometry[0]);

                            // remove last duplicate of first coordinate
                            gps.splice(gps.length - 1, 1);
                        }

                        else if (drawingDiv.hasClass('path') || drawingDiv.hasClass('circle') && coordinates.length > 0) {
                            gps = listLv03toWgs84(geometry);
                        }

                        while (gps.length > drawingDiv.find('.gps').length)
                            addCoordinateField(drawingDiv);

                        $(drawingDiv).find('.gps').each(function (index) {
                            if (gps[index] != undefined)
                                $(this).val(parseFloat((gps[index][0]).toFixed(3)) + ', ' + parseFloat((gps[index][1]).toFixed(3)));
                            else if (validateCoordinate($(this)))
                                removeCoordinateField($(this).parent().parent());
                        });
                    }
                });

            });
        },
        setActive: function (active) {
            this.select.setActive(active);
            this.modify.setActive(active);
        }
    };

    Modify.init();

    Draw = {
        init: function () {
            map.addInteraction(this.Point);
            this.Point.setActive(false);
            map.addInteraction(this.Path);
            this.Path.setActive(false);
            map.addInteraction(this.Polygon);
            this.Polygon.setActive(false);
            map.addInteraction(this.Circle);
            this.Circle.setActive(false);
            map.addInteraction(this.Rectangle);
            this.Rectangle.setActive(false);
            this.setEvents();
        },
        Point: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Point'
        }),
        Path: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'LineString'
        }),
        Polygon: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Polygon'
        }),
        Circle: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'Circle'
        }),
        Rectangle: new ol.interaction.Draw({
            source: vector.getSource(),
            type: 'LineString',
            geometryFunction: function (coordinates, geometry) {
                if (!geometry) {
                    geometry = new ol.geom.Polygon(null);
                }
                var start = coordinates[0];
                var end = coordinates[1];
                geometry.setCoordinates([
                    [start, [start[0], end[1]], end, [end[0], start[1]], start]
                ]);
                return geometry;
            },
            maxPoints: 2
        }),


        getActive: function () {
            return this.activeType ? this[this.activeType].getActive() : false;
        },
        setActive: function (active, type) {
            if (active) {
                this.activeType && this[this.activeType].setActive(false);
                this[type].setActive(true);
                this.activeType = type;
            } else {
                this.activeType && this[this.activeType].setActive(false);
                this.activeType = null;
            }
        },
        setEvents: function () {
            this.Path.on('drawstart', function (evt) {

                updateMeasureTooltip(evt.feature, evt.coordinate, 'Path');
                showInstruction('You can now add as many points as you want by clicking again at a position.\n' +
                    'To close your drawing, double click at this point.');
            });
            this.Path.on('drawend', function (evt) {
                addDrawing(evt.feature, 'Path');
            });
            this.Polygon.on('drawstart', function (evt) {
                updateMeasureTooltip(evt.feature, evt.coordinate, 'Polygon');
                showInstruction('You can now add as many points as you want by clicking again at a position.\n' +
                    'To close your drawing, double click at this point.');
            });
            this.Polygon.on('drawend', function (evt) {
                addDrawing(evt.feature, 'Polygon');
            });
            this.Circle.on('drawstart', function (evt) {
                updateMeasureTooltip(evt.feature, evt.coordinate, 'Circle');
                showInstruction('Click at the point you want to finish your Circle.');
            });
            this.Circle.on('drawend', function (evt) {
                addDrawing(evt.feature, 'Circle');
            });
            this.Rectangle.on('drawstart', function (evt) {
                updateMeasureTooltip(evt.feature, evt.coordinate, 'Rectangle')
                showInstruction("You can set your Rectangle by clicking at the desired end point.");
            });
            this.Rectangle.on('drawend', function (evt) {
                addDrawing(evt.feature, 'Polygon');
            });
        }
    };
    Draw.init();

    Modify.setActive(false);

    var snap = new ol.interaction.Snap({
        source: vector.getSource()
    });
    map.addInteraction(snap);
}

/**
 * Updates the measure tooltip with the updated value.
 * @param feature
 * @param tooltipCoord
 * @param type
 */
function updateMeasureTooltip(feature, tooltipCoord, type) {

    createMeasureTooltip();

    feature.getGeometry().on('change', function (evt) {
        var geom = evt.target;
        let tooltipText;
        if (type === 'Circle') {
            var radius = parseInt(feature.getGeometry().getRadius());
            if (radius > 500)
                measureTooltipElement.classList.add("radius-invalid");
            else
                measureTooltipElement.classList.remove("radius-invalid");

            tooltipText = radius + " m";

        } else if (type === 'Rectangle') {
            var area = parseInt(geom.getArea() / 1000) / 1000;
            tooltipText = area + " km&sup2;";
        } else {
            var coords;
            if (type === 'Polygon') {
                coords = feature.getGeometry().getCoordinates()[0];
            }
            else {
                coords = feature.getGeometry().getCoordinates();
            }

            var line = new ol.geom.LineString([coords[coords.length - 2], coords[coords.length - 1]]);
            var length = parseInt(line.getLength()) / 1000;

            tooltipText = length + " km";
        }

        measureTooltipElement.innerHTML = tooltipText;
        tooltipCoord = geom.getLastCoordinate();
        measureTooltip.setPosition(tooltipCoord);
    });
}

/**
 * Activate the draw tool button and init the instructions.
 * @param button
 * @param type
 */
function setDrawButtonActive(button, type) {
    $('#draw-tool').find('button').each(function () {
        $(this).css("background", "rgba(1, 89, 160, 0.5)");
    });
    button.css("background", "rgba(41, 128, 196, 0.95)");
    if (type != "Modify") {
        showInstruction("You can now start drawing by clicking into the map at the desired point.", "Drawing " + type + "!");
    }
    else {
        showInstruction("Select a drawing you want to modify by clicking on it.", "Modify!");
    }
}


// intersection ?

/**
 * Checks weather any drawing intersects with a TMA or CTR zone.
 * @returns {boolean}
 */
function checkIntersections() {

    // need to be shown and hidden
    setLayerVisible(2, false);
    setLayerVisible(3, false);

    setLayerVisible(2, true);
    setLayerVisible(3, true);

    cleanUpKml(ctr);
    cleanUpKml(tma);


    var ctrZone = ctr.getSource().getFeatures();
    var tmaZone = tma.getSource().getFeatures();
    var features = source.getFeatures();

    for (var i = 0; i < features.length; i++) {
        var doIntersect = false;
        for (var j = 0; j < tmaZone.length; j++) {
            if (intersectsArea(tmaZone[j], features[i]))
                doIntersect = true;
        }
        for (var j = 0; j < ctrZone.length; j++) {
            if (intersectsArea(ctrZone[j], features[i]))
                doIntersect = true;
        }
        if (doIntersect)
            return true;
    }
}

/**
 * Checks weather a feature intersetcs on plain with a zone feature.
 * @param zone
 * @param feature
 * @returns {boolean}
 */
function intersectsArea(zone, feature) {
    var poly1 = turf.polygon(zone.getGeometry().getCoordinates());
//    var polyAltitude = parseKmlElevation(zone);

    var figure;

    if (feature.getGeometry().getType() === "Polygon") {
        figure = turf.polygon(feature.getGeometry().getCoordinates());
    } else if (feature.getGeometry().getType() === "Circle") {
        // convert to wgs84
        var center = ol.proj.transform(feature.getGeometry().getCenter(), 'EPSG:21781', 'EPSG:4326');
        figure = turf.circle([center[1], center[0]], feature.getGeometry().getRadius() / 1000);
        // convert back
        figure.geometry.coordinates[0].forEach(function (item, index) {
            figure.geometry.coordinates[0][index] = ol.proj.transform([item[1], item[0]], 'EPSG:4326', 'EPSG:21781');
        });
    } else if (feature.getGeometry().getType() === "LineString") {
        figure = turf.lineString(feature.getGeometry().getCoordinates());
    }


    // check if intersect
    var intersection = turf.lineIntersect(poly1, figure);
    if (intersection)
        return true;
    //  if (intersectsAltitude(feature, zone))


    return false;
}

/**
 * Checks weather a feature intersetcs in the third dimension with a zone feature.
 * @param feature Drawing
 * @param zone Zone of TMA or CTR
 * @returns {boolean}
 */
function intersectsAltitude(feature, zone) {

    var poly1 = turf.polygon(zone.getGeometry().getCoordinates());
    //  if circle error
    var featurePoly = turf.polygon(feature.getGeometry().getCoordinates());

    var extent = turf.bbox(featurePoly);

    var min = lv03toWgs84([extent[0], extent[1]]);

    var max = lv03toWgs84([extent[2], extent[3]]);

    extent[0] = min[1];
    extent[1] = min[0];
    extent[2] = max[1];
    extent[3] = max[0];

    poly1.geometry.coordinates[0].forEach(function (item, index) {
        poly1.geometry.coordinates[0][index] = lv03toWgs84(item);
    });

    var options = {mask: poly1, units: 'kilometers'};
    // ca 10 m raster
    var points = turf.pointGrid(extent, 0.01, options);

    var doIntersect = false;
    points.features.forEach(function (item) {
        coords = wgs84toLv03(item.geometry.coordinates);

        var featureAltitude;
        var lowerLimit;
        var upperLimit;

        var lowerType = zone.getProperties().lowerLimitType;
        var lowerValue = zone.getProperties().lowerLimitValue;

        var upperType = zone.getProperties().upperLimitType;
        var upperValue = zone.getProperties().upperLimitValue;

        featureAltitude = toMeterAmsl(feature, coords);
        lowerLimit = getLimit(lowerType, lowerValue, coords);
        upperLimit = getLimit(upperType, upperValue, coords);

        if (featureAltitude > lowerLimit && featureAltitude < upperLimit)
            doIntersect = true;

    });

    return doIntersect;
}

/**
 * Gets the upper and lower limitation of an TMA or CTR zone in Meters AMSL.
 * @param limitType Type of the limit (AGL, AMSL, FL).
 * @param value
 * @param coordinates GPS coordinates
 * @returns {*} Meter AMSL
 */
function getLimit(limitType, value, coordinates) {
    if (limitType === 'AGL') {
        var agl = aglToAmsl(coordinates);
        value = parseInt(agl) + parseInt(feetToMeter(value));
    } else if (limitType === 'AMSL') {
        value = parseInt(feetToMeter(value));
    } else if (limitType === 'FL') {
        value = parseInt(flToMeter(value))
    } else {
        return null;
    }
    return value;
}

// CONFIG & UTIL --------------------------------------------------------------------------------------------------------

/**
 * Url of the REST API.
 * @type {string}
 */
const restUrl = 'http://localhost:8080';

/**
 * Url of the Geodesy service to fetch the altitude of a given coordinate.
 * @type {string}
 */
const heightServiceUrl = 'https://api3.geo.admin.ch/rest/services/height';

/**
 * Url of the Geodesy service to convert coordinates in LV03 to LV95.
 * @type {string}
 */
const lv03toLv95Url = 'http://geodesy.geo.admin.ch/reframe/lv03tolv95';

/**
 * Fetches an asynchronous request.
 * @param type CRUD type
 * @param url
 * @param doneFunction
 * @param failFunction
 * @param data JSON data
 */
function asyncRequest(type, url, doneFunction, failFunction, data) {
    $.ajax({
        crossOrigin: true,
        url: url,
        type: type,
        dataType: 'json',
        data: data,
        contentType: "application/json; charset=utf-8"
    })
        .done(doneFunction)
        .fail(failFunction);
}

/**
 * Styles a feature with the red style and gives an id label.
 * @param feature
 * @param id
 */
function styleDrawing(feature, id) {

    feature.setStyle(new ol.style.Style({

        fill: new ol.style.Fill({
            color: 'rgba(255, 0, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: '#FF0000',
            width: 4
        }),
        text: new ol.style.Text({
            font: '20px Calibri,sans-serif',
            fill: new ol.style.Fill({color: '#000000'}),
            stroke: new ol.style.Stroke({
                color: '#FFFFFF',
                width: 4
            }),
            text: id
        })
    }));
}

/**
 * Styles a feature with the blue style.
 * @param feature
 */
function styleModify(feature) {
    var id = feature.getId().split('drawing')[1];
    feature.setStyle(new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: '#3E99F7',
            width: 3
        }),
        fill: new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.3)'
        }),
        text: new ol.style.Text({
            font: '20px Calibri,sans-serif',
            fill: new ol.style.Fill({color: '#000000'}),
            stroke: new ol.style.Stroke({
                color: '#FFFFFF',
                width: 4
            }),
            text: id
        })
    }));
}

/**
 * Converts a features altitude to Meteres AMSL.
 * @param feature
 * @param coordinates
 * @returns {*}
 */
function toMeterAmsl(feature, coordinates) {
    var type = $('input[name=heightType]:checked').val();
    var value = $('#' + feature.getId()).find('.altitude').val();

    if (type == 'ft AMSL') {
        value = feetToMeter(value);
    } else if (type == 'ft GND') {
        var agl = aglToAmsl(coordinates);
        value = parseInt(agl) + feetToMeter(value);
    } else if (type == 'm GND') {
        var agl = aglToAmsl(coordinates);
        value = parseInt(agl) + parseInt(value);
    } else if (type == 'FL') {
        value = flToMeter(value);
    } else {
        return null;
    }
    return value;

}

/**
 * Converts a coordinates altitude in AGL to AMSL.
 * @param coordinates
 * @returns {*}
 */
function aglToAmsl(coordinates) {
    var res;
    asyncRequest('GET', heightServiceUrl + '?easting=' + coordinates[0] + '&northing=' + coordinates[1],
        function (pos) {
            res = pos.height;
        },
        function (jqXHR) {
            showErrorModal(jqXHR);
        });

    /* $.ajax({
         crossOrigin: true,
         url: 'https://api3.geo.admin.ch/rest/services/height?easting=' + coordinates[0] + '&northing=' + coordinates[1],
         type: 'GET',
         dataType: 'json',
         async: false
     })
         .done(function (pos) {
             res = pos.height;
         })
         .fail(function (jqXHR) {
             $('#modal-error').modal('show');
             errorLog = JSON.stringify(jqXHR.responseJSON);
         });*/
    return res;
}

/**
 * Converts
 * @param coordinates
 * @returns {*[]}
 */
function lv03toWgs84(coordinates) {
    var tmp = ol.proj.transform([coordinates[0], coordinates[1]], 'EPSG:21781', 'EPSG:4326');
    return [tmp[1], tmp[0]];
}

/**
 * Converts a list of LV03 coordinates into GPS (wgs84).
 * @param coordinates
 */
function listLv03toWgs84(coordinates) {
    let gps = [];
    coordinates.forEach(function (item, index) {
        gps[index] = lv03toWgs84(item);
    });
    return gps;
}

/**
 * Converts GPS (wgs84) coordinates to LV03.
 * @param coordinates
 * @returns {boolean | * | void}
 */
function wgs84toLv03(coordinates) {
    return ol.proj.transform([coordinates[1], coordinates[0]], 'EPSG:4326', 'EPSG:21781');
}

/**
 * Converts Feet to Meters.
 * @param feet
 * @returns {number}
 */
function feetToMeter(feet) {
    return feet * 0.3048;
}

/**
 * Converts Meter to Feet.
 * @param meter
 * @returns {number}
 */
function meterToFeet(meter) {
    return meter * 3.28084;
}

/**
 * Converts Flight Level to Meters.
 * @param fl Flight Level
 * @returns {number}
 */
function flToMeter(fl) {
    return fl * 100;
}


// MAIN --------------------------------------------------------------------------------------------------------


/**
 * Entry point when the Website is ready.
 */
$(document).ready(function () {
    // measure the time to fill the application until submit
    startTime = new Date();

    initializeForm();
    // initialize Map

});


/**
 * Initialize all Change Handlers in the Application.
 */
function initializeChangeHandlers() {


    // submit


    /**
     * Prevent pressing enter from submission.
     */
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
    });


    /**
     * Handle clicking the submit button.
     */
    $(document).on('click', '#btn_submit', function (event) {
        event.preventDefault();
        event.stopPropagation();

        if (validateForm()) {
            $('#form-feedback').hide();
            submitApplication();
        }
        else {
            $('html,body').scrollTop(0);
            $('#form-feedback').show();
        }

    });

    /**
     * Handle clicking the report button. Send email.
     */
    $(document).on('click', '#btn-report', function () {
        $(location).attr('href', 'mailto:marco.ghilardelli@students.fhnw.ch?subject='
            + encodeURIComponent("Report Problem: Skyguide Web Application")
            + "&body="
            + encodeURIComponent($('#btn-report').attr('error-log'))
        );
    });

    /**
     * Handle clicking the add time button. Add the time fields.
     */
    $(document).on('click', '#btn-add-time', function () {
        timeIndex++;
        var template = $('#time_template'),
            clone = template
                .clone()
                .removeAttr('id')
                //.prop('required', true)
                .attr('data-time-index', timeIndex)
                .addClass('time_field')
                .insertBefore(template.parent());

        clone
            .find('[name="start"]').attr('name', 'start[' + timeIndex + ']')
            .prop('required', true).end()
            .find('[name="end"]').attr('name', 'end[' + timeIndex + ']')
            .prop('required', true).end()
            .find('input').each(function () {
            $(this).addClass("data");
        });
    });

    /**
     * Handle clicking the remove time button. Remove the time input fields.
     */
    $(document).on('click', '.remove_time_button', function () {
        var row = $(this).parents('.form-row');
        row.remove();
    });

    /**
     * Handle clicking the add polygon drawing button. Add empty fields.
     */
    $(document).on('click', '#add_polygon_btn', function () {
        addDrawingDiv('Polygon');
    });

    /**
     * Handle clicking the add circle drawing button. Add empty fields.
     */
    $(document).on('click', '#add_circle_btn', function () {
        addDrawingDiv('Circle');
    });

    /**
     * Handle clicking the add path drawing button. Add empty fields.
     */
    $(document).on('click', '#add_path_btn', function () {
        addDrawingDiv('Path');
    });

    /**
     * Handle clicking the add coordinate drawing button. Add empty fields.
     */
    $(document).on('click', '.add_coordinate_path_polygon', function () {
        addCoordinateField($(this).parent().parent());
    });

    // map


    /**
     * Handle the change of the ICAO layer checkbox.
     */
    $(document).on('change', '#check-layer-icao', function () {
        if ($('#check-layer-icao').is(':checked')) {
            setLayerVisible(1, true);
        }
        else {
            setLayerVisible(1, false);
        }
    });


    /**
     * Handle the change of the CTR layer checkbox.
     */
    $(document).on('change', '#check-layer-ctr', function () {
        if ($('#check-layer-ctr').is(':checked')) {
            setLayerVisible(3, true);
        }
        else {
            setLayerVisible(3, false);
        }
    });


    /**
     * Handle the change of the TMA layer checkbox.
     */
    $(document).on('change', '#check-layer-tma', function () {
        if ($('#check-layer-tma').is(':checked')) {
            setLayerVisible(2, true);
        }
        else {
            setLayerVisible(2, false);
        }
    });

    /**
     * Handle clicking the altitude send button in the altitude modal. Validate and save.
     */
    $(document).on('click', '#btn-send-altitude', function () {
        const altitudeModalInput = $('#input-altitude');
        const altitudeInput = $('#drawing' + drawingIndex).find('.altitude');
        if (altitudeModalInput[0].checkValidity()) {
            altitudeInput.val(altitudeModalInput.val());
            validateField(altitudeInput, true);
            $('#modal-altitude').modal('hide');
        }
        else {
            validateField(altitudeInput, false);
        }
    });

    /**
     * Handle clicking the remove drawing button. Remove the drawing input fields.
     */
    $(document).on('click', '.remove-drawing', function (event) {
        //  todo  event.preventDefault();
        //    event.stopPropagation();
        const drawingDiv = $(this).parent().parent().parent();
        const drawingId = drawingDiv.attr("id");
        if (source.getFeatureById(drawingId))
            source.removeFeature(source.getFeatureById(drawingId));
        drawingDiv.remove();
        resetMap();
    });

    /**
     * Handle click the remove coordinate button. Remove coordinate input field.
     */
    $(document).on('click', '.remove_coordinate_path_polygon_button', function () {
        $(this).parent().find('.gps').val("");
        const drawingId = $(this).parent().parent().attr('id');
        const drawingDiv = $(this).parent().parent();
        removeCoordinateField($(this).parent());
        updateDrawings(drawingId, drawingDiv);
    });


    /**
     * Handle clicking the draw tool button.
     */
    $(document).on('click', '#draw-tool-btn', function () {
        showInstruction("Please choose your desired drawing type respectively modify.");
    });


    /**
     * Handle clicking the draw rectangle button. Set Drawing state.
     */
    $(document).on('click', '#btn_draw_rectangle', function () {
        const type = 'Rectangle';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    /**
     * Handle clicking the draw polygon button. Set Drawing state.
     */
    $(document).on('click', '#btn_draw_polygon', function () {
        const type = 'Polygon';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    /**
     * Handle clicking the draw circle button. Set Drawing state.
     */
    $(document).on('click', '#btn_draw_circle', function () {
        const type = 'Circle';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
        //createMeasureTooltip();
    });

    /**
     * Handle clicking the draw path button. Set Drawing state.
     */
    $(document).on('click', '#btn_draw_path', function () {
        const type = 'Path';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    /**
     * Handle clicking the modify button. Set Modify state.
     */
    $(document).on('click', '#btn_draw_modify', function () {
        const type = 'Modify';
        Draw.setActive(false);
        Modify.setActive(true);
        setDrawButtonActive($(this), type);
    });


    // validation

    /**
     * Handle pressed key on all input fields.
     */
    $(document).on('keyup', 'input', function () {
        if ($(this).attr('filled')) {
            const isValid = $(this)[0].checkValidity();
            validateField($(this), isValid);
            if ($(this).hasClass('time')) {
                validateTimes($(this));
            }
        }
    });

    /**
     * Handle if an input field is leaved. Validate.
     */
    $(document).on('focusout', 'input', function () {
        if (!($(this).hasClass('gps') || $(this).hasClass('radius') || $(this).hasClass('phone'))) {
            const isValid = $(this)[0].checkValidity();
            validateField($(this), isValid);
            $(this).attr("filled", true);
            if ($(this).hasClass('time')) {
                validateTimes($(this));
            }
            if ($(this).is($('#field_date_from_until')))
                validateField($(this), true);
        }
    });

    /**
     * Handle the change of the select fields. Validate.
     */
    $(document).on('change', 'select', function () {
        const isValid = $(this)[0].checkValidity();
        validateField($(this), isValid);
    });

    /**
     * Handle the change of the height type radio button. Validate.
     */
    $(document).on('change', 'input[name=heightType]:checked', function () {
        isValid = $('input[name=heightType]:checked').val() != undefined;
        $(this).parent().parent().parent().find('input').each(function () {
            validateField($(this), isValid);
        });
    });

    /**
     * Handle if the phone input is leaved. Validate.
     */
    $(document).on('keyup', '#input_applicant_phone', function () {
        if ($.trim($(this).val())) {
            if ($(this).intlTelInput("isValidNumber")) {
                validateField($(this), true);
            }
            else {
                validateField($(this), false);
            }
        }
    });

    /**
     * Handle the change of the activity type dropdown. Reset the form.
     */
    $(document).on('change', '#type_of_activity', function () {
        emptyForm();
        resetAllInputFields();
        hideGeoFields();
        hideAircraftType();

        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.label) {
                // it's a activity type with multiple aircraft type
                if (activityType.aircraftTypeList.length > 1) {
                    aircraftTypes = activityType.aircraftTypeList;
                    showAircraftType(activityType);
                }
                else {
                    showGeoFields(activityType.aircraftTypeList[0].fieldList);
                }
            }
        });
    });

    /**
     * Handle the change of the aircraft type dropdown. Reset the form.
     */
    $(document).on('change', '#type_of_aircraft', function () {
        emptyForm();
        resetAllInputFields();
        hideGeoFields();

        $.each(aircraftTypes, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.label) {
                showGeoFields(aircraftType.fieldList);
            }
        });

    });

    /**
     * Handle leaving the gps input field. Update drawings.
     */
    $(document).on('keyup', '.gps', function () {
        updateDrawings($(this).parent().parent().parent().attr('id'), $(this).parent().parent().parent());
    });

    /**
     * Handle leaving the radius input field. Update drawings.
     */
    $(document).on('keyup', '.radius', function () {
        if (validateRadius($(this))) {
            if ($(this).parent().parent().parent().find('.gps').val() != "")
                updateDrawings($(this).parent().parent().parent().attr('id'), $(this).parent().parent().parent());
        }
    });


}