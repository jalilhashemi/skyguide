
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

/**
 * Validate the drawing with id.
 * @param drawingId
 * @returns {boolean}
 */
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
    const type = field.attr("name").split('[')[0];
    let end, start;

    if (type == "start") {
        start = field;
        end = start.parent().parent().find($('input[name^="end"]'));
    }
    else if (type == "end") {
        end = field;
        start = end.parent().parent().find($('input[name^="start"]'));
    }
    if (start[0].checkValidity() && end[0].checkValidity()) {
        const startDt = new Date(2010, 12, 21, 9, start.val().split(':')[0], start.val().split(':')[1]);
        const endDt = new Date(2010, 12, 21, 9, end.val().split(':')[0], end.val().split(':')[1]);
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
