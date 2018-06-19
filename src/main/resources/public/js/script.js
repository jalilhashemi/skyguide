/**
 * The measure tooltip element.
 * @type {Element}
 */
let measureTooltipElement;

var startTime

/**
 * Overlay to show the measurement.
 * @type {ol.Overlay}
 */
let measureTooltip;


let informationJSON;

let aircraftTypes;

/**
 * Openlayer Geo Admin map.
 * @type {ga.Map}
 */
let map;

var restUrl = 'http://localhost:8080';

const heightServiceUrl = 'https://api3.geo.admin.ch/rest/services/height';

const lv03toLv95Url = 'http://geodesy.geo.admin.ch/reframe/lv03tolv95';

var timeIndex = 0;
var drawingIndex = 0;
var Modify;
var Draw;

// default center position
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
    // measure the time to fill the application until submit
    startTime = new Date();

    initializeForm();
});

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

function appendSelection(data) {
    $.each(data, function (i, item) {
        $('#type_of_activity').append($('<option>', {
            text: item.label
        }));
    });
}

function hideLoading() {
    $("#icon_loading").hide();
}

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

function initTooltips() {
    $('[data-toggle="tooltip"]').tooltip();
}

function initDateRangePicker() {
    $(function () {
        $('input[name="dateFromUntil"]').daterangepicker({
            locale: {
                format: 'DD.MM.YYYY'
            }
        });
    });
}

function resetAllInputFields() {
    $('input.data.activity-data').val('');
    $('input.data.activity-data').prop('required', false);
}

function resetRadio() {
    $('.height-type-radio').prop('checked', false);
    $('.height-type-radio').prop('required', false);
}

function hideGeoFields() {
    $('.geo').hide();
    hideMap();
}

function hideAircraftType() {
    $('.aircraft-type').hide();
    $('#type_of_aircraft').prop('required', false);
    $('#type_of_aircraft').val('');
}

function hideMap() {
    $('#map-container').hide();
}


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
 * Displays a field with the label and the mandatory * sign
 * @param field The JSON field object from /information service
 */
function showField(field) {
    $('#' + field.id).parent().show();
    $('#' + field.id).parent().children('label').remove();
    $('#' + field.id).parent().prepend('<label for="' + field.id + '">' + field.label + (field.mandatory ? '*' : '') + '</label>\n')
    $('#' + field.id)
        .attr('placeholder', field.placeholder)
        .prop('required', field.mandatory ? true : false);
}


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

function showSubmitButton() {
    $('#btn_submit').show();
}

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
        if (!validateDrawings())
            isValid = false;
    }


    return isValid;
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

function lv03toWgs84(coordinates) {
    var tmp = ol.proj.transform([coordinates[0], coordinates[1]], 'EPSG:21781', 'EPSG:4326');
    return [tmp[1], tmp[0]];
}

function listLv03toWgs84(coordinates) {
    coordinates.forEach(function (item, index) {
        gps[index] = lv03toWgs84(item);
    });
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
    let isValid = true;

    if (source.getFeatures().length) {

        $('.drawing').each(function () {
            let validData = true;
            $(this).find('.gps').each(function (index, item) {
                validData = (validateCoordinate($(this)) != null)

            });
            validData = $(this).find('.altitude')[0].checkValidity();
            validateField($('.altitude'), validData);


            if ($(this).find('.radius').length) {
                validData = $(this).find('.radius')[0].checkValidity();
                validateField($('.radius'), validData);
            }
            if (!validData)
                isValid = false;
        });
        validateMap(true);
    }
    else {
        isValid = false;
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

function initializeChangeHandlers() {

// prevent enter for submission
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
    });

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
            + encodeURIComponent($('#btn-report').attr('error-log'))
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


    $(document).on('change', '#type_of_activity', function () {
        emptyForm();
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

    $(document).on('change', '#type_of_aircraft', function () {
        emptyForm();
        hideGeoFields();

        $.each(aircraftTypes, function (i, aircraftType) {
            if ($('#type_of_aircraft').find('option:selected').text() == aircraftType.label) {
                showGeoFields(aircraftType.fieldList);
            }
        });

    });

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

    $(document).on('click', '.remove_time_button', function () {
        var row = $(this).parents('.form-row');
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
        resetMap();
    });


    $(document).on('click', '#add_polygon_btn', function () {
        addDrawingDiv('Polygon');
    });

    $(document).on('click', '#add_circle_btn', function () {
        addDrawingDiv('Circle');
    });

    $(document).on('click', '#add_path_btn', function () {
        addDrawingDiv('Path');
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
        showInstruction("You can now start drawing by clicking into the map at the desired point.","Drawing " + type + "!");
    }
    else {
        showInstruction("Select a drawing you want to modify by clicking on it.","Modify!");
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

function removeTimeFields() {
    $('.time_field').remove();
}

function resetMap() {
    setMapView(centerPos, 400);
}

function clearMap() {
    // remove drawings
    source.clear();
    $('.drawing').each(function () {
        $(this).remove();
    });
    drawingIndex = 0;
    resetMap();
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
    removeTimeFields();
    resetMap();
}


function validateRadius(field) {
    var isValid = field.val() > 0 && field.val() <= 500;
    validateField(field, isValid);
    return isValid;
}

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

function addCoordinateField(drawingDiv) {
    var template = $('#coordinate_path_polygon_template'),
        clone = template
            .clone()
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

        asyncRequest('GET', lv03toLv95Url + '?easting=' + position[0] + '&northing=' + position[1],
            function (pos) {
                if (ol.extent.containsCoordinate(extent, pos)) {
                    position = roundCoordinates(pos);
                }
            },
            function (jqXHR) {
                showErrorModal(jqXHR);
            });

        // Match LV03 coordinates
        /*  $.ajax({
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

              });*/

    }

    validateField(field, position != null);
    return position;
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
                            listLv03toWgs84(geometry[0]);

                            // remove last duplicate of first coordinate
                            gps.splice(gps.length - 1, 1);
                        }

                        else if (drawingDiv.hasClass('path') || drawingDiv.hasClass('circle') && coordinates.length > 0) {
                            listLv03toWgs84(geometry);
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

function showAltitudeModal() {
    resetInputField($('#input-altitude'));
    $('#modal-altitude').modal('show');
    $('#height-type').text($('input[name=heightType]:checked').val());
}

function resetInputField(field) {
    resetInputValidation(field);
    field.val("");
}

function resetInputValidation(field) {
    field.removeClass("is-invalid");
    field.removeClass("is-valid");
}

function showInstruction(text, title) {
    $('#map-instructions-text').text(text);
    if (title !== undefined)
        $('#map-instructions-title').text(title);
}

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

    // return drawingId;
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

function setMapView(gps, res) {
    map.getView().setCenter(wgs84toLv03(gps));
    map.getView().setResolution(res);
}

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
            if (checkIntersections()) {
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

function showErrorModal(jqXHR, data) {
    $('#modal-error').modal('show');
    $('#btn-report').attr('error-log', JSON.stringify(jqXHR.responseJSON) + "\n" + JSON.stringify(data));
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
