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


let informationJSON;

let actualAircraftTypeList;

/**
 * Openlayer Geo Admin map.
 * @type {ga.Map}
 */
let map;

var intersectionLayer = new ol.layer.Vector({
    source: new ol.source.Vector()
});

var restUrl = 'http://localhost:8080';
var timeIndex = 0;
var drawingIndex = 0;
var Modify;
var Draw;
var errorLog = "";

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
        url: 'ctr.kml',
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
        url: 'tma.kml',
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
 * Global form validation state.
 * @type {boolean}
 */
let validForm = true;

/**
 * Creates a new measure tooltip
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

$(document).ready(function () {

    $("#icon_loading").hide();

    // TODO: refactor that
    // get the url params
    var url = new URL(window.location.href);
    var edit = url.searchParams.get("edit");
    var key = url.searchParams.get("key");
    if (key != null) {
        console.log('url key: ' + key);
        console.log('url edit: ' + edit);
        if (edit != null)
            showAdminView(key);
        else
            showUserView(key);
    }
    // standard form view
    else {
        initializeForm();
        initializeChangeHandlers();
    }

    // prevent enter for submission
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
    });
});

function showAdminView(key) {
    // not implemented yet
    alert("admin");
}

function showUserView(key) {
    disableAllFields();
    fillAllFields(key);

    initializeMap();
    $('#map-container').removeClass('display-none');
    map.updateSize();
}

function disableAllFields() {
    $('fieldset').prop('disabled', true);
}

function fillAllFields(key) {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/applications/' + key,
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            fillFields(json);
        })
        .fail(function (jqXHR) {
            $('#modal-error').modal('show');
            errorLog = JSON.stringify(jqXHR.responseJSON);
        });

}

function fillFields(data) {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/information',
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            initializeDisabledInputs(json, data.activityType, data.aircraftType, data);
        })
        .fail(function (jqXHR) {
            $('#modal-error').modal('show');
            errorLog = JSON.stringify(jqXHR.responseJSON);
        });
}

//
function initializeDisabledInputs(information, activityType, aircraftType, data) {
    $('#type_of_activity').append($('<option>', {
        text: activityType,
        selected: true
    }));
    if (aircraftType != null) {
        $('#type_of_aircraft').parent().show();
        $('#type_of_aircraft').prop('required', true);
        $('#type_of_aircraft').append($('<option>', {
            text: aircraftType,
            selected: true
        }));

        $.each(information, function (i, infoItem) {
            if ($('#type_of_activity').val() == infoItem.label) {
                $.each(infoItem.aircraftTypeList, function (j, aircraftTypeItem) {
                    if ($('#type_of_aircraft').val() == aircraftTypeItem.label) {
                        $.each(aircraftTypeItem.fieldList, function (i, field) {
                            processField(field);
                        });
                    }
                });
            }
        });

        $('.data').each(function (index) {
                var input = $(this);
                input.val(data[input.attr('name')]);
            }
        );

        $('input[name=heightType]').each(function (index) {
            if ($(this).val() == data["heightType"])
                $(this).prop('checked', true);
        });

        var gps = [data["drawings"][0]["coordinates"][0]["lat"], data["drawings"][0]["coordinates"][0]["lon"]];

        $('#field_gps_coord').val(gps[0] + ", "
            + gps[1]);

        var position = ol.proj.transform(gps, 'EPSG:4326', 'EPSG:21781');

        setView([position[0], position[1]]);

        var feature = new ol.Feature({
            geometry: new ol.geom.Point(position)
        });
        source.addFeature(feature);

        $.each(data["times"], function (i, time) {
            if (i > 0) {
                var template = $('#time_template'),
                    clone = template
                        .clone()
                        .removeClass('display-none')
                        .removeAttr('id')
                        //.prop('required', true)
                        .attr('data-time-index', i)
                        .addClass('time_field')
                        .insertBefore(template);

                clone
                    .find('[name="start"]').attr('name', 'start[' + i + ']')
                    .prop('required', true).end()
                    .find('[name="end"]').attr('name', 'end[' + i + ']')
                    .prop('required', true).end();
            }
            $('input[name^="start[' + i + ']"]').val(time["start"].substring(0, 5));
            $('input[name^="end[' + i + ']"]').val(time["end"].substring(0, 5));
        });

    }
}

function getFormInformation() {
    asyncRequest('GET', restUrl + '/information',
        function (data) {
            appendSelection(data);
            informationJSON = data;
        },
        function (jqXHR) {
            $('#modal-error').modal('show');
            errorLog = JSON.stringify(jqXHR.responseJSON);
        });
}

function asyncRequest(type, url, doneFunction, failFunction, data) {
    $.ajax({
        crossOrigin: true,
        url: url,
        type: type,
        dataType: 'json',
        data: data
    })
        .done(doneFunction)
        .fail(failFunction);
}


function initializeDropdowns() {
    $.ajax({
        crossOrigin: true,
        url: restUrl + '/information',
        type: 'GET',
        dataType: 'json'
    })
        .done(function (json) {
            appendSelection(json);
            informationJSON = json;
        })
        .fail(function (jqXHR) {
            $('#modal-error').modal('show');
            errorLog = JSON.stringify(jqXHR.responseJSON);
        });
}

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.label
        }));
    });
}

function initializeForm() {
    getFormInformation();
    // initializeDropdowns();
    initializeDateRangePicker();
    initializeTooltips();
    initializeMap();
    initDrawTool();

    $("#input_applicant_phone").intlTelInput({
        nationalMode: true,
        initialCountry: "auto",
        geoIpLookup: function(callback) {
            $.get('https://ipinfo.io', function() {}, "jsonp").always(function(resp) {
                var countryCode = (resp && resp.country) ? resp.country : "";
                callback(countryCode);
            });
        },
        utilsScript: "lib/utils.js"
    });

}

function initializeTooltips() {
    $('[data-toggle="tooltip"]').tooltip();
}

function initializeDateRangePicker() {
    $(function () {
        $('input[name="dateFromUntil"]').daterangepicker({
            //autoUpdateInput: false,
            locale: {
                format: 'DD.MM.YYYY'
            }
        });
    });
}

/**
 * Hide all custom fields.
 */
function hideAllFields() {
    $('#container_fields').children('div .form-group').addClass('display-none');
    $('#container_fields').children('div .form-row').children('div .form-group').addClass('display-none');
    $('#map-container').addClass('display-none');
    $('#add_area_dropdown').addClass('display-none');
    $('#draw-instructions').addClass('display-none');
    $('#altitude_label').addClass('display-none');
    $('#btn-add-time').addClass('display-none');
    $('.time_field').addClass('display-none');

    $('.custom-control-input').parent().addClass('display-none');
    $('.custom-control-input').addClass('display-none');
    $('.custom-control-input').prop('checked', false);
    $('.custom-control-input').prop('required', false);

    // empty all fields
    $('input.data.activity-data').val('');
    $('input.data.activity-data').prop('required', false);


}

/**
 * Displays a field with the label and the mandatory * sign
 * @param field The JSON field object from /information service
 */
function showField(field) {
    $('#' + field.id).parent().removeClass('display-none');
    $('#' + field.id).parent().children('label').remove();
    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.label + (field.mandatory ? '*' : '') + '</label>\n')
    $('#' + field.id)
        .attr('placeholder', field.placeholder)
        .prop('required', field.mandatory ? true : false);
}


function processField(field) {
    if (field.active) {
        if (field.id.substring(0, 6) === 'radio_') {
            $('#' + field.id).parent().removeClass('display-none');
            $('#' + field.id).parent().parent().parent().removeClass('display-none');
            $('#' + field.id).prop('required', true);
            // $('#' + field.id).parent().attr('title', field.tooltip);
            initializeTooltips();
            $('#btn-add-time').removeClass('display-none');
        }
        else {
            showField(field);
        }
    }

}

function formValidate(isValid) {
    if (!isValid && validForm) {
        validForm = false;
    }

}

function validateForm() {

    $('.data').each(function (index, item) {
        var isValid;
        if ($(this).hasClass('heightType')) {
            isValid = $('input[name=heightType]:checked').val() != undefined;
            formValidate(isValid);
            $(this).find('input').each(function () {
                validateField($(this), isValid);
            });
        }else if($(this).hasClass('phone')) {
            if ($.trim($(this).val())) {
                if ($(this).intlTelInput("isValidNumber")) {
                    validateField($(this), true);
                }
                else {
                    validateField($(this), false);
                    formValidate(false);
                }
            }
        }
        else {
            isValid = item.checkValidity();
            validateField($(this), isValid);
            formValidate(isValid);
            if ($(this).hasClass('time')) {
                formValidate(validateTimes($(this)));

            }

            console.log($(this).attr('id') + ": " + isValid);
            console.log($(this));
        }
    });

// dirty hack for types without drawings
    if ($('#type_of_activity').val() === 'Sky Lantern' || $('#type_of_activity').val() === 'Toy Balloon'
        || $('#type_of_activity').val() === 'Sky Light / Laser' || $('#type_of_activity').val() === 'Weather Balloon'
        || $('#type_of_activity').val() === 'Firework' || $('#type_of_activity').val() === 'Captive Balloon'
        || $('#type_of_activity').val() === 'Kite' || $('#type_of_activity').val() === 'Model Rocket'
        || $('#type_of_activity').val() === 'Gas Balloon' || $('#type_of_activity').val() === 'Hot Air Balloon') {
        // no map
    }
    else {
        validateDrawings();
    }


    /*  var features1 = ctr.getSource().features;
      var features2 = source.features;

      for (var i=0; i < features1.length-1; i++){
          var feature1 = features1[i];
          for (var j=0; j < features2.length-1; j++){
              var feature2 = features2[j];
              if (feature1.geometry.intersects(feature2.geometry)){
                  console.log("vector features 1 " + i + " intersects vector features 2 " + j);
              }
          }
      }
  */


    return validForm;
}

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

function aglToAmsl(coordinates) {
    var res;
    $.ajax({
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
        });
    return res;
}

function lv03toWgs84(coordinates) {
    var tmp = ol.proj.transform([coordinates[0], coordinates[1]], 'EPSG:21781', 'EPSG:4326');
    return [tmp[1], tmp[0]];
}

function wgs84toLv03(coordinates) {
    return ol.proj.transform([coordinates[1], coordinates[0]], 'EPSG:4326', 'EPSG:21781');
}

function feetToMeter(feet) {
    return feet * 0.3048;
}

function meterToFeat(meter) {
    return meter * 3.28084;
}

function flToMeter(fl) {
    return fl * 100;
}

function validateDrawings() {
    var isValid;

    if (source.getFeatures().length) {

        $('.drawing').each(function () {
            $(this).find('.gps').each(function (index, item) {
                formValidate(validateCoordinate($(this)) != null)

            });
            isValid = $(this).find('.altitude')[0].checkValidity();
            validateField($('.altitude'), isValid);
            formValidate(isValid);


            if ($(this).find('.radius').length) {
                isValid = $(this).find('.radius')[0].checkValidity();
                validateField($('.radius'), isValid);
                formValidate(isValid);

            }
        });
        formValidate(true);
        validateMap(true);
    }
    else {
        formValidate(false);
        validateMap(false, 'Please draw at least one drawing.');
    }
}

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

function showSurvey() {
    setTimeout(function () {
        (function (t, e, s, o) {
            var n, c, l;
            t.SMCX = t.SMCX || [], e.getElementById(o) || (n = e.getElementsByTagName(s), c = n[n.length - 1], l = e.createElement(s), l.type = "text/javascript", l.async = !0, l.id = o, l.src = ["https:" === location.protocol ? "https://" : "http://", "widget.surveymonkey.com/collect/website/js/tRaiETqnLgj758hTBazgdyWCzZc0WCTHJj5wcZa9Sy55DklTrOQ9l8n_2F2szZz4B9.js"].join(""), c.parentNode.insertBefore(l, c))
        })
        (window, document, "script", "smcx-sdk");
    }, 2000);
}

function initializeChangeHandlers() {


    $(document).on('click', '#btn_submit', function (event) {
        event.preventDefault();
        event.stopPropagation();

        validForm = true;
        if (validateForm()) {
            submitApplication();
        }
        else {
            $('html,body').scrollTop(0);
            $('#form-feedback').show();
        }

    });

    $(document).on('click', '#btn-try-again', function () {
        $('#modal-error').modal('hide');
        submitApplication();
    });

    $(document).on('change', '#check-layer-icao', function () {
        if ($('#check-layer-icao').is(':checked')) {
            setLayerVisible(1, true);
        }
        else {
            setLayerVisible(1, false);
        }
    });


    //
    $(document).on('change', '#check-layer-ctr', function () {
        if ($('#check-layer-ctr').is(':checked')) {
            setLayerVisible(3, true);
        }
        else {
            setLayerVisible(3, false);
        }
    });


    $(document).on('change', '#check-layer-tma', function () {
        if ($('#check-layer-tma').is(':checked')) {
            setLayerVisible(2, true);
        }
        else {
            setLayerVisible(2, false);
        }
    });

    $(document).on('click', '.btn-another-entry', function () {
        $('#modal-success-nosua').modal('hide');
        $('#submit_success').modal('hide');
        $('#type_of_activity').val("");
        $('#textfield_remark').val("");
        $('#type_of_aircraft').parent().hide()
        $('#type_of_aircraft').prop('required', false);
        $('#type_of_aircraft').val('');
        $('#type_of_activity').removeClass('is-invalid');
        $('#type_of_activity').removeClass('is-valid');
        $('#textfield_remark').removeClass('is-invalid');
        $('#textfield_remark').removeClass('is-valid');
        $('#type_of_aircraft').removeClass('is-invalid');
        $('#type_of_aircraft').removeClass('is-valid');
        hideAllFields();
        emptyForm();
    });


    $(document).on('click', '#btn-send-altitude', function () {
        if ($('#input-altitude')[0].checkValidity()) {
            $('#drawing' + drawingIndex).find('.altitude').val($('#input-altitude').val());
            $('#modal-altitude').modal('hide');
        }
        else {
            validateField($('#input-altitude'), false);
        }
    });

    $(document).on('click', '#btn-report', function () {
        $(location).attr('href', 'mailto:marco.ghilardelli@students.fhnw.ch?subject='
            + encodeURIComponent("Report Problem: Skyguide Web Application")
            + "&body="
            + encodeURIComponent(errorLog)
        );
    });

    $(document).on('keyup', 'input', function () {
        if ($(this).attr('filled')) {
            var isValid = $(this)[0].checkValidity();
            validateField($(this), isValid);
            if ($(this).hasClass('time')) {
                validateTimes($(this));
            }

        }
    });

    $(document).on('focusout', 'input', function () {
        if (!($(this).hasClass('gps') || $(this).hasClass('radius') || $(this).hasClass('phone'))) {
            var isValid = $(this)[0].checkValidity();
            validateField($(this), isValid);
            $(this).attr("filled", true);
            if ($(this).hasClass('time')) {
                validateTimes($(this));
            }
            if ($(this).is($('#field_date_from_until')))
                validateField($(this), true);
        }
    });

    $(document).on('change', 'select', function () {
        var isValid = $(this)[0].checkValidity();
        validateField($(this), isValid);
    });

    $(document).on('change', 'input[name=heightType]:checked', function () {
        isValid = $('input[name=heightType]:checked').val() != undefined;
        $(this).parent().parent().parent().find('input').each(function () {
            validateField($(this), isValid);
        });
    });

    $(document).on('keyup', '#input_applicant_phone', function() {
        if ($.trim($(this).val())) {
            if ($(this).intlTelInput("isValidNumber")) {
                validateField($(this), true);
            }
            else {
                validateField($(this), false);
                formValidate(false);
            }
        }
    });


    $(document).on('change', '#type_of_activity', function () {
        emptyForm();
        hideAllFields();


        // and hide the aircraft type selection and empty value
        $('#type_of_aircraft').parent().hide()
        $('#type_of_aircraft').prop('required', false);
        $('#type_of_aircraft').val('');

        $.each(informationJSON, function (j, activityType) {
            if ($('#type_of_activity').val() == activityType.label) {
                // it's a activity type with multiple aircraft type
                if (activityType.aircraftTypeList.length > 1) {
                    actualAircraftTypeList = activityType.aircraftTypeList;

                    // show the dropdown
                    $('#type_of_aircraft').parent().show();


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
                else {

                    // things showed anytime
                    $('#map-container').removeClass('display-none');
                    $('#add_area_dropdown').removeClass('display-none');
                    $('#draw-instructions').removeClass('display-none');
                    $('#altitude_label').removeClass('display-none');

                    // dirty hack for types without drawings
                    if ($('#type_of_activity').val() === 'Sky Lantern' || $('#type_of_activity').val() === 'Toy Balloon'
                        || $('#type_of_activity').val() === 'Sky Light / Laser' || $('#type_of_activity').val() === 'Weather Balloon'
                        || $('#type_of_activity').val() === 'Firework' || $('#type_of_activity').val() === 'Captive Balloon'
                        || $('#type_of_activity').val() === 'Kite' || $('#type_of_activity').val() === 'Model Rocket'
                        || $('#type_of_activity').val() === 'Gas Balloon' || $('#type_of_activity').val() === 'Hot Air Balloon') {
                        $('#map-container').addClass('display-none');
                        $('#add_area_dropdown').addClass('display-none');
                        $('#draw-instructions').addClass('display-none');
                    }
                    map.updateSize();
                    // add time button
                    $('#btn-add-time').removeClass('display-none');

                    $.each(activityType.aircraftTypeList[0].fieldList, function (i, field) {
                        processField(field);
                    });
                }
            }
        });
    });

    $(document).on('change', '#type_of_aircraft', function () {
        emptyForm();
        hideAllFields();

        $.each(actualAircraftTypeList, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.label) {
                $('#map-container').removeClass('display-none');
                $('#add_area_dropdown').removeClass('display-none');
                $('#draw-instructions').removeClass('display-none');
                $('#altitude_label').removeClass('display-none');
                map.updateSize();
                $('#btn-add-time').removeClass('display-none');
                $.each(aircraftType.fieldList, function (i, field) {
                    processField(field);
                });
            }
        });

    });

    $(document).on('click', '#btn-add-time', function () {
        timeIndex++;
        var template = $('#time_template'),
            clone = template
                .clone()
                .removeClass('display-none')
                .removeAttr('id')
                //.prop('required', true)
                .attr('data-time-index', timeIndex)
                .addClass('time_field')
                .insertBefore(template);

        clone
            .find('[name="start"]').attr('name', 'start[' + timeIndex + ']')
            .prop('required', true).end()
            .find('[name="end"]').attr('name', 'end[' + timeIndex + ']')
            .prop('required', true).end()
            .find('input').each(function () {
            $(this).addClass("data");
        });
    });

    $(document).on('click', '.remove_time_button', function () {
        var row = $(this).parents('.form-row');

        // Remove element containing the option
        row.remove();
    });

    $(document).on('click', '.remove-drawing', function (event) {
        event.preventDefault();
        event.stopPropagation();
        var drawingDiv = $(this).parent().parent().parent();
        var drawingId = drawingDiv.attr("id");
        if (source.getFeatureById(drawingId))
            source.removeFeature(source.getFeatureById(drawingId));
        drawingDiv.remove();
    });


    $(document).on('click', '#add_polygon_btn', function () {
        addPolygonDrawingDiv();
    });

    $(document).on('click', '#add_circle_btn', function () {
        addCircleDrawingDiv();

    });

    $(document).on('click', '#add_path_btn', function () {
        addPathDrawingDiv();
    });

    $(document).on('click', '.add_coordinate_path_polygon', function () {
        addCoordinateField($(this).parent().parent());
    });

    $(document).on('click', '.remove_coordinate_path_polygon_button', function () {
        $(this).parent().find('.gps').val("");
        var drawingId = $(this).parent().parent().attr('id');
        var drawingDiv = $(this).parent().parent();
        removeCoordinateField($(this).parent());
        updateDrawings(drawingId, drawingDiv);
    });

    $(document).on('keyup', '.gps', function () {
        updateDrawings($(this).parent().parent().parent().attr('id'), $(this).parent().parent().parent());
    });

    $(document).on('keyup', '.radius', function () {
        if (validateRadius($(this))) {
            if ($(this).parent().parent().parent().find('.gps').val() != "")
                updateDrawings($(this).parent().parent().parent().attr('id'), $(this).parent().parent().parent());
        }
    });

    $(document).on('click', '#draw-tool-btn', function () {
        $('#map-instructions').show();
        $('#map-instructions-text').text("Please choose your desired drawing type respectively modify.")
    });

    $(document).on('click', '#btn_draw_rectangle', function () {
        var type = 'Rectangle';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    $(document).on('click', '#btn_draw_point', function () {
        var type = 'Point';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    $(document).on('click', '#btn_draw_polygon', function () {
        var type = 'Polygon';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    $(document).on('click', '#btn_draw_circle', function () {
        var type = 'Circle';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
        //createMeasureTooltip();
    });

    $(document).on('click', '#btn_draw_path', function () {
        var type = 'Path';
        Draw.setActive(true, type);
        Modify.setActive(false);
        setDrawButtonActive($(this), type);
    });

    $(document).on('click', '#btn_draw_modify', function () {
        var type = 'Modify';
        Draw.setActive(false);
        Modify.setActive(true);
        setDrawButtonActive($(this), type);
    });
}


function setDrawButtonActive(button, type) {
    $('#draw-tool').find('button').each(function () {
        $(this).css("background", "rgba(1, 89, 160, 0.5)");
    });
    button.css("background", "rgba(41, 128, 196, 0.95)");
    if (type != "Modify") {
        $('#map-instructions-title').text("Drawing " + type + "!");
        $('#map-instructions-text').text("You can now start drawing by clicking into the map at the desired point.");
    }
    else {
        $('#map-instructions-title').text("Modify!");
        $('#map-instructions-text').text("Select a drawing you want to modify by clicking on it.");
    }
}


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

    validateDrawings();
    Modify.setActive(false);

}

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
    // remove times
    $('.time_field').remove();

    // remove drawings
    source.clear();
    $('.drawing').each(function () {
        $(this).remove();
    })
    drawingIndex = 0;

    // set Map projection
    var lat = 46.81;
    var lon = 8.31;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    map.getView().setCenter(loc);
    map.getView().setResolution(400);
}

function validateRadius(field) {
    var isValid = field.val() > 0 && field.val() <= 500;
    validateField(field, isValid);
    return isValid;
}

function addPathDrawingDiv() {
    drawingIndex++;
    var template = $('#path_template'),
        clone = template
            .clone()
            .removeClass('display-none')
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
            .removeClass('display-none')
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
            .removeClass('display-none')
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
}

function addCoordinateField(drawingDiv) {
    var template = $('#coordinate_path_polygon_template'),
        clone = template
            .clone()
            .removeClass('display-none')
            .removeAttr('id')
            .insertAfter(drawingDiv.children().last());

}

function removeCoordinateField(gpsRow) {
    gpsRow.remove();
}

function calculateRadius(value, coordinate) {
    var view = map.getView();
    var projection = view.getProjection();
    var resolutionAtEquator = view.getResolution();
    var pointResolution = projection.getPointResolution(resolutionAtEquator, coordinate);
    var resolutionFactor = resolutionAtEquator / pointResolution;

    return (value / ol.proj.METERS_PER_UNIT.m) * resolutionFactor;
}

function validateCoordinate(field) {
    var defaultEpsg = 'EPSG:21781';

    var position;
    var extent = map.getView().getProjection().getExtent();


    var query = field.val();

    // source of the search transition code
    // https://github.com/geoadmin/mf-geoadmin3/blob/17ba14f3047bf4692752b36a1295172fa396177d/src/components/search/SearchService.js

    var DMSDegree = '[0-9]{1,2}[°|º]\\s*';
    var DMSMinute = '[0-9]{1,2}[\'|′]';
    var DMSSecond = '(?:\\b[0-9]+(?:\\.[0-9]*)?|\\.' +
        '[0-9]+\\b)("|\'\'|′′|″)';
    var DMSNorth = '[N]';
    var DMSEast = '[E]';
    var regexpDMSN = new RegExp(DMSDegree +
        '(' + DMSMinute + ')?\\s*' +
        '(' + DMSSecond + ')?\\s*' +
        DMSNorth, 'g');
    var regexpDMSE = new RegExp(DMSDegree +
        '(' + DMSMinute + ')?\\s*' +
        '(' + DMSSecond + ')?\\s*' +
        DMSEast, 'g');
    var regexpDMSDegree = new RegExp(DMSDegree, 'g');
    var regexpCoordinate = new RegExp(
        '([\\d\\.\']+)[\\s,]+([\\d\\.\']+)' +
        '([\\s,]+([\\d\\.\']+)[\\s,]+([\\d\\.\']+))?');

    var roundCoordinates = function (coords) {
        return [Math.round(coords[0] * 1000) / 1000,
            Math.round(coords[1] * 1000) / 1000];
    };

    // Parse degree EPSG:4326 notation
    var matchDMSN = query.match(regexpDMSN);
    var matchDMSE = query.match(regexpDMSE);
    if (matchDMSN && matchDMSN.length === 1 &&
        matchDMSE && matchDMSE.length === 1) {
        var northing = parseFloat(matchDMSN[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
        var easting = parseFloat(matchDMSE[0].match(regexpDMSDegree)[0].replace('°', '').replace('º', ''));
        var minuteN = matchDMSN[0].match(DMSMinute) ?
            matchDMSN[0].match(DMSMinute)[0] : '0';
        northing = northing +
            parseFloat(minuteN.replace('\'', '').replace('′', '')) / 60;
        var minuteE = matchDMSE[0].match(DMSMinute) ?
            matchDMSE[0].match(DMSMinute)[0] : '0';
        easting = easting +
            parseFloat(minuteE.replace('\'', '').replace('′', '')) / 60;
        var secondN =
            matchDMSN[0].match(DMSSecond) ?
                matchDMSN[0].match(DMSSecond)[0] : '0';
        northing = northing + parseFloat(secondN.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
        var secondE = matchDMSE[0].match(DMSSecond) ?
            matchDMSE[0].match(DMSSecond)[0] : '0';
        easting = easting + parseFloat(secondE.replace('"', '').replace('\'\'', '').replace('′′', '').replace('″', '')) / 3600;
        position = ol.proj.transform([easting, northing],
            'EPSG:4326', defaultEpsg);
        if (ol.extent.containsCoordinate(extent, position)) {
            position = roundCoordinates(position);

        }
    }

    var match = query.match(regexpCoordinate);
    if (match) {
        var left = parseFloat(match[1].replace(/'/g, ''));
        var right = parseFloat(match[2].replace(/'/g, ''));
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

        // Match LV03 coordinates
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

    }

    if (position != null) {
        field.addClass('is-valid');
        field.removeClass('is-invalid');
        //var gps = ol.proj.transform(position, 'EPSG:21781', 'EPSG:4326');
        //return {"lat": gps[1], "lon": gps[0]};
        return position;

    }
    field.addClass('is-invalid');
    field.removeClass('is-valid');


    return null;
}

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
                $('#map-instructions-text').text("Select a drawing you want to modify by clicking on it.");
            });

            /*   this.select.on('select', function (evt) {
                   var selected = evt.selected;
                   var deselected = evt.deselected;

                   if (selected.length) {
                       selected.forEach(function (feature) {
                           console.info(feature);
                           feature.setStyle(style_modify);
                       });
                   }
                   else {
                       deselected.forEach(function (feature) {
                           console.info(feature);
                           styleDrawing(feature, feature.getId().split('drawing')[1]);
                       });
                   }

               })*/
            selectedFeatures.on('add', function (e) {
                styleModify(e.element);

                $('#map-instructions-text').text("Now you can drag a point. You can add a Point by dragging on a line. Remove a point by only click on it.");

                e.element.on('change', function (e) {

                    var drawingId = $(this)[0].getId();
                    var drawingDiv = $('#' + drawingId);

                    if (!drawingDiv.hasClass('circle')) {
                        var geometry = $(this)[0].getGeometry().getCoordinates();
                        var gps = [];


                        if (drawingDiv.hasClass('polygon')) {
                            geometry[0].forEach(function (item, index) {
                                gps[index] = ol.proj.transform(item, 'EPSG:21781', 'EPSG:4326');
                            });

                            // remove last duplicate of first coordinate
                            gps.splice(gps.length - 1, 1);
                        }

                        else if (drawingDiv.hasClass('path')) {
                            geometry.forEach(function (item, index) {
                                gps[index] = ol.proj.transform(item, 'EPSG:21781', 'EPSG:4326');
                            });

                        } else if (drawingDiv.hasClass('circle') && coordinates.length > 0) {
                            geometry.forEach(function (item, index) {
                                gps[index] = ol.proj.transform(item, 'EPSG:21781', 'EPSG:4326');
                            });
                        }

                        while (gps.length > drawingDiv.find('.gps').length)
                            addCoordinateField(drawingDiv);

                        $(drawingDiv).find('.gps').each(function (index) {
                            if (gps[index] != undefined)
                                $(this).val(parseFloat((gps[index][1]).toFixed(3)) + ', ' + parseFloat((gps[index][0]).toFixed(3)));
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
        // new ol.interaction.DragBox({
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
            var listener;
            this.Path.on('drawstart', function (evt) {
                $('#map-instructions-text').text("You can now add as many points as you want by clicking again at a position.\nTo close your drawing, double click at this point.")
                createMeasureTooltip();
                // set sketch
                sketch = evt.feature;

                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = evt.coordinate;

                listener = sketch.getGeometry().on('change', function (evt) {
                    var geom = evt.target;
                    var coords = sketch.getGeometry().getCoordinates();
                    var line = new ol.geom.LineString([coords[coords.length - 2], coords[coords.length - 1]]);
                    var length = parseInt(line.getLength()) / 1000;

                    tooltipCoord = geom.getLastCoordinate();
                    measureTooltipElement.innerHTML = length + " km";
                    measureTooltip.setPosition(tooltipCoord);
                });

            });
            this.Path.on('drawend', function (evt) {
                var drawingId = addPathDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingPathDiv(evt.feature, drawingId);
                measureTooltipElement.parentNode.removeChild(measureTooltipElement);
                $('#input-altitude').removeClass("is-invalid");
                $('#input-altitude').removeClass("is-valid");
                $('#input-altitude').val("");
                $('#input-altitude').trigger('focus');
                $('#modal-altitude').modal('show');
                $('#height-type').text($('input[name=heightType]:checked').val());
                $('#map-instructions-title').text("Created Path!");
                $('#map-instructions-text').text("You finally added a new Path to your drawings.\nYou can modify it with the Modify tool or in the fields above.");
            });
            this.Polygon.on('drawstart', function (evt) {
                $('#map-instructions-text').text("You can now add as many points as you want by clicking again at a position.\nTo close your drawing, double click at this point or click on the start point.")
                createMeasureTooltip();
                // set sketch
                sketch = evt.feature;

                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = evt.coordinate;

                listener = sketch.getGeometry().on('change', function (evt) {
                    var geom = evt.target;
                    var coords = sketch.getGeometry().getCoordinates()[0];
                    var line = new ol.geom.LineString([coords[coords.length - 2], coords[coords.length - 1]]);
                    var length = parseInt(line.getLength()) / 1000;

                    tooltipCoord = geom.getLastCoordinate();
                    measureTooltipElement.innerHTML = length + " km";
                    measureTooltip.setPosition(tooltipCoord);
                });

            });
            this.Polygon.on('drawend', function (evt) {
                var drawingId = addPolygonDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingPolygonDiv(evt.feature, drawingId);
                measureTooltipElement.parentNode.removeChild(measureTooltipElement);
                $('#input-altitude').removeClass("is-invalid");
                $('#input-altitude').removeClass("is-valid");
                $('#input-altitude').val("");
                $('#height-type').text($('input[name=heightType]:checked').val());
                $('#modal-altitude').modal('show');
                $('#map-instructions-title').text("Created Polygon!");
                $('#map-instructions-text').text("You finally added a new Polygon to your drawings.\nYou can modify it with the Modify tool or in the fields above.");

            });
            this.Circle.on('drawstart', function (evt) {

                $('#map-instructions-text').text("You can now set the Circle's radius .\nTo set it you can click at that position.");
                createMeasureTooltip();
                // set sketch
                sketch = evt.feature;

                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = evt.coordinate;

                listener = sketch.getGeometry().on('change', function (evt) {
                    var geom = evt.target;
                    var radius = parseInt(sketch.getGeometry().getRadius());
                    if (radius > 500)
                        measureTooltipElement.classList.add("radius-invalid");
                    else
                        measureTooltipElement.classList.remove("radius-invalid");

                    tooltipCoord = geom.getLastCoordinate();
                    measureTooltipElement.innerHTML = radius + " m";
                    measureTooltip.setPosition(tooltipCoord);
                });

            });
            this.Circle.on('drawend', function (evt) {
                var drawingId = addCircleDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingCircleDiv(evt.feature, drawingId);
                measureTooltipElement.parentNode.removeChild(measureTooltipElement);
                $('#input-altitude').removeClass("is-invalid");
                $('#input-altitude').removeClass("is-valid");
                $('#input-altitude').val("");
                $('#height-type').text($('input[name=heightType]:checked').val());

                $('#modal-altitude').modal('show');
                $('#map-instructions-title').text("Created Circle!");
                $('#map-instructions-text').text("You finally added a new Circle to your drawings.\nYou can modify it in the fields above.");
            });
            this.Rectangle.on('drawstart', function (evt) {
                $('#map-instructions-text').text("You can set your Rectangle by clicking at the desired end point.");
                createMeasureTooltip();
                // set sketch
                sketch = evt.feature;

                /** @type {ol.Coordinate|undefined} */
                var tooltipCoord = evt.coordinate;

                listener = sketch.getGeometry().on('change', function (evt) {
                    var geom = evt.target;
                    var area = parseInt(geom.getArea() / 1000) / 1000;
                    tooltipCoord = geom.getLastCoordinate();
                    measureTooltipElement.innerHTML = area + " km&sup2;";
                    measureTooltip.setPosition(tooltipCoord);
                });
            });
            this.Rectangle.on('drawend', function (evt) {
                var drawingId = addPolygonDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingPolygonDiv(evt.feature, drawingId);
                measureTooltipElement.parentNode.removeChild(measureTooltipElement);

                $('#input-altitude').removeClass("is-invalid");
                $('#input-altitude').removeClass("is-valid");
                $('#input-altitude').val("");
                $('#height-type').text($('input[name=heightType]:checked').val());

                $('#modal-altitude').modal('show');
                $('#map-instructions-title').text("Created Rectangle!");
                $('#map-instructions-text').text("You finally added a new Rectangle to your drawings.\nYou can modify it with the Modify tool or in the fields above.");
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

function fillDrawingPolygonDiv(feature, drawingId) {
    feature.setId("drawing" + drawingId);
    var coordinates = feature.getGeometry().getCoordinates()[0];
    var drawingDiv = $('#drawing' + drawingId);

    coordinates.splice(coordinates.length - 1, 1);
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

function fillDrawingPathDiv(feature, drawingId) {
    feature.setId("drawing" + drawingId);
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
}

function setLayerVisible(layerIndex, isVisible) {
    //  map.getLayers()[layerIndex].setVisible(isVisible);
    map.getLayers().forEach(function (layer, idx) {
        if (idx == layerIndex)
            layer.setVisible(isVisible);
    });
};

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

function initializeMap() {

    $('#map-instructions').hide();
    // default center position
    var lat = 46.81;
    var lon = 8.31;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

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
            intersectionLayer
        ],
        crossOrigin: 'null',

        // disable scrolling on map
        // interactions: ol.interaction.defaults({mouseWheelZoom: false})

    });

    map.getView().setCenter(loc);
    map.getView().setResolution(400);

    setLayerVisible(1, false);

}

function setView(loc) {
    map.getView().setCenter(loc);
    map.getView().setResolution(50);
}

function submitApplication() {
    $("#icon_loading").show();
    $('#form-feedback').hide();

    var data = {};

    $('.data').each(
        function (index) {
            if (!$(this).hasClass('time') && !$(this).hasClass('altitude')&& !$(this).hasClass('phone')) {
                var input = $(this);
                data[input.attr('name')] = input.val();
            }
        }
    );

    data["heightType"] = $('input[name=heightType]:checked').val();

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

    data['times'] = times;
    //data['drawings'] = drawings;

    data['phone'] = $('#input_applicant_phone').intlTelInput("getNumber");

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

    data['drawings'] = drawings;

    if (data['heightType'] == undefined)
        data['heightType'] = 'none';

    if (data['aircraftType'] == '')
        data['aircraftType'] = 'none';

    console.log(data);

// submit to server
    $.ajax({
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
        });
}

//validate time and its range
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
