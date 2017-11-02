$(document).ready(function () {

// Create a GeoAdmin Map
    var map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            ga.layer.create('ch.swisstopo.pixelkarte-farbe-pk25.noscale'),
            ga.layer.create('ch.swisstopo.pixelkarte-farbe-pk50.noscale'),
            ga.layer.create('ch.swisstopo.pixelkarte-farbe-pk100.noscale'),
            ga.layer.create('ch.swisstopo.pixelkarte-farbe-pk200.noscale'),
            ga.layer.create('ch.swisstopo.pixelkarte-farbe-pk500.noscale')
        ],
        // Create a view
        view: new ol.View({

            // Define the default resolution
            // 10 means that one pixel is 10m width and height
            // List of resolution of the WMTS layers:
            // 650, 500, 250, 100, 50, 20, 10, 5, 2.5, 2, 1, 0.5, 0.25, 0.1
            resolution: 650,

            // Define a coordinate CH1903 (EPSG:21781) for the center of the view
            center: [660000, 190000]
        })
    });

    /******************  Select a layer ********************/
    var setOneLayerVisible = function (layerIndex) {
        map.getLayers().forEach(function (layer, idx) {
            layer.setVisible(idx == layerIndex);
        });
    };

// Makes the multiscale layer visible
    setOneLayerVisible(0);

// Add inputs radio events
    $("[name=layer]").click(function () {
        setOneLayerVisible(this.value);
    });
    /***************** end select a layer ***************/

    /***************** draw a rectangle ***************/
    var boxStyle = new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 0, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: '#FF0000',
            width: 2
        })
    });
    ;

    var overlay = new ol.FeatureOverlay({
        map: map,
        style: boxStyle
    });

    var dragBox = new ol.interaction.DragBox({
        style: boxStyle
    });

// Listeners dragbox interaction event
    dragBox.on('boxstart', function (evt) {
        overlay.getFeatures().clear();
    });

    dragBox.on('boxend', function (evt) {
        var bbox = dragBox.getGeometry().getExtent();
        $("#north").val(Math.round(bbox[3]));
        $("#south").val(Math.round(bbox[1]));
        $("#east").val(Math.round(bbox[2]));
        $("#west").val(Math.round(bbox[0]));
        overlay.addFeature(new ol.Feature(dragBox.getGeometry()));
        map.removeInteraction(dragBox);
        $("#map").removeClass("drawing");
    });

// Add keyboard events on inputs
    $(".coordinates input").keyup(function () {
        var north = $("#north")[0].value;
        var south = $("#south")[0].value;
        var east = $("#east")[0].value;
        var west = $("#west")[0].value;

        if (north && south && west && east) {
            var polygon = new ol.geom.Polygon(
                [[[west, north],
                    [west, south],
                    [east, south],
                    [east, north],
                    [west, north]]]
            );

            // Apply the new coordinates to the box
            overlay.getFeatures().clear();
            overlay.addFeature(new ol.Feature(polygon));
        }
    });

// Add new rectangle link click event
    $(".viewer-new-rectangle").click(function () {
        $("#map").addClass("drawing");
        if (dragBox) {
            map.removeInteraction(dragBox);
        }
        map.addInteraction(dragBox);
    });

// Add delete rectangle link click event
    $(".viewer-delete-rectangle").click(function () {
        overlay.getFeatures().clear();
        $("input[type=text]").val(undefined);
    });
    /*************** end draw a rectangle ***************/
});