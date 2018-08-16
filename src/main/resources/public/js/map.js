/**
 * Openlayer Geo Admin map.
 * @type {ga.Map}
 */
let map;

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
 * Set the maps view to a given coordinate and resolution.
 * @param gps
 * @param res
 */
function setMapView(gps, res) {
    map.getView().setCenter(wgs84toLv03(gps));
    map.getView().setResolution(res);
}
