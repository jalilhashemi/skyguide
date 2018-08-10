
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


    const ctrZone = ctr.getSource().getFeatures();
    const tmaZone = tma.getSource().getFeatures();
    const features = source.getFeatures();

    for (let i = 0; i < features.length; i++) {
        let doIntersect = false;
        for (let j = 0; j < tmaZone.length; j++) {
            if (intersectsArea(tmaZone[j], features[i]))
                doIntersect = true;
        }
        for (let j = 0; j < ctrZone.length; j++) {
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
    const poly1 = turf.polygon(zone.getGeometry().getCoordinates());
    let figure;

    if (feature.getGeometry().getType() === "Polygon") {
        figure = turf.polygon(feature.getGeometry().getCoordinates());
    } else if (feature.getGeometry().getType() === "Circle") {
        // convert to wgs84
        const center = ol.proj.transform(feature.getGeometry().getCenter(), 'EPSG:21781', 'EPSG:4326');
        figure = turf.circle([center[1], center[0]], feature.getGeometry().getRadius() / 1000);
        // convert back
        figure.geometry.coordinates[0].forEach(function (item, index) {
            figure.geometry.coordinates[0][index] = ol.proj.transform([item[1], item[0]], 'EPSG:4326', 'EPSG:21781');
        });
    } else if (feature.getGeometry().getType() === "LineString") {
        figure = turf.lineString(feature.getGeometry().getCoordinates());
    }


    // check if intersect
    const intersection = turf.lineIntersect(poly1, figure);
    if (intersection)
        return true;

    return false;
}

/**
 * Not implemented in release version because of time consuming calculation. See documentation.
 * Checks weather a feature intersetcs in the third dimension with a zone feature.
 * @param feature Drawing
 * @param zone Zone of TMA or CTR
 * @returns {boolean}
 */
function intersectsAltitude(feature, zone) {

    const poly1 = turf.polygon(zone.getGeometry().getCoordinates());
    //  if circle error
    const featurePoly = turf.polygon(feature.getGeometry().getCoordinates());

    const extent = turf.bbox(featurePoly);

    const min = lv03toWgs84([extent[0], extent[1]]);

    const max = lv03toWgs84([extent[2], extent[3]]);

    extent[0] = min[1];
    extent[1] = min[0];
    extent[2] = max[1];
    extent[3] = max[0];

    poly1.geometry.coordinates[0].forEach(function (item, index) {
        poly1.geometry.coordinates[0][index] = lv03toWgs84(item);
    });

    const options = {mask: poly1, units: 'kilometers'};
    // ca 10 m raster
    const points = turf.pointGrid(extent, 0.01, options);

    let doIntersect = false;
    points.features.forEach(function (item) {
        coords = wgs84toLv03(item.geometry.coordinates);

        let featureAltitude;
        let lowerLimit;
        let upperLimit;

        const lowerType = zone.getProperties().lowerLimitType;
        const lowerValue = zone.getProperties().lowerLimitValue;

        const upperType = zone.getProperties().upperLimitType;
        const upperValue = zone.getProperties().upperLimitValue;

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
        const agl = aglToAmsl(coordinates);
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

/**
 * Cleans a KML layer for using with Geo Admin.
 * @param layer
 */
function cleanUpKml(layer) {
    layer.getSource().getFeatures().forEach(function (feature) {
        let coordinates = [];
        feature.getGeometry().getCoordinates()[0].forEach(function (coords) {
            coords.splice(2, 1)
            coordinates.push(coords);
        });
        feature.getGeometry().setCoordinates([coordinates]);
    });
}
