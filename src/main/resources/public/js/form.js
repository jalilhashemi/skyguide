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
        const template = $('#time_template'),
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
        let row = $(this).parents('.form-row');
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
                const countryCode = (resp && resp.country) ? resp.country : "";
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
    const template = $('#coordinate_path_polygon_template'),
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
    let template;
    if (type === 'Path') {
        template = $('#path_template');
    } else if (type === 'Circle') {
        template = $('#circle_template');
    } else if (type === 'Polygon') {
        template = $('#polygon_template');
    }

    const clone = template
        .clone()
        .addClass('drawing')
        .attr('id', 'drawing' + drawingIndex)
        .prepend('<div class="row"><div class="col-md-2">' +
            '<h3>' + type + ' ' + drawingIndex + '</h3></div>' +
            '<div class="col-md-2"><button tabindex="-1" type="button" class="btn btn-primary btn-red-sky mini remove-drawing">' +
            'Remove</button></div></div>');


    clone.find('.altitude').addClass('data');
    clone.find('input').prop('required', true);

    clone.insertBefore($('#map-container'));


    return drawingIndex;
}