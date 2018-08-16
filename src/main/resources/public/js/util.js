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
    const id = feature.getId().split('drawing')[1];
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
    const type = $('input[name=heightType]:checked').val();
    let value = $('#' + feature.getId()).find('.altitude').val();

    if (type == 'ft AMSL') {
        value = feetToMeter(value);
    } else if (type == 'ft GND') {
        const agl = aglToAmsl(coordinates);
        value = parseInt(agl) + feetToMeter(value);
    } else if (type == 'm GND') {
        const agl = aglToAmsl(coordinates);
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
    let res;
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
    const tmp = ol.proj.transform([coordinates[0], coordinates[1]], 'EPSG:21781', 'EPSG:4326');
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