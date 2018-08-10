/**
 * Modify Tool.
 */
let Modify;

/**
 * Draw Tool.
 */
let Draw;

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
            let selectedFeatures = this.select.getFeatures();

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

                    const drawingId = $(this)[0].getId();
                    const drawingDiv = $('#' + drawingId);

                    if (!drawingDiv.hasClass('circle')) {
                        const geometry = $(this)[0].getGeometry().getCoordinates();
                        let gps = [];


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
                const start = coordinates[0];
                const end = coordinates[1];
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
                showInstruction('Click on a point to finish your circle.');
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

    const snap = new ol.interaction.Snap({
        source: vector.getSource()
    });
    map.addInteraction(snap);
}


/**
 * Fill the form inputs with a given feature.
 * @param feature
 * @param drawingId
 * @param type Drawing type
 */
function fillDrawingDiv(feature, drawingId, type) {

    const drawingDiv = $('#drawing' + drawingId);
    let coordinates;
    let radius;

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
 * Updates the measure tooltip with the updated value.
 * @param feature
 * @param tooltipCoord
 * @param type
 */
function updateMeasureTooltip(feature, tooltipCoord, type) {

    createMeasureTooltip();

    feature.getGeometry().on('change', function (evt) {
        const geom = evt.target;
        let tooltipText;
        if (type === 'Circle') {
            const radius = parseInt(feature.getGeometry().getRadius());
            if (radius > 500)
                measureTooltipElement.classList.add("radius-invalid");
            else
                measureTooltipElement.classList.remove("radius-invalid");

            tooltipText = radius + " m";

        } else if (type === 'Rectangle') {
            const area = parseInt(geom.getArea() / 1000) / 1000;
            tooltipText = area + " km&sup2;";
        } else {
            let coords;
            if (type === 'Polygon') {
                coords = feature.getGeometry().getCoordinates()[0];
            }
            else {
                coords = feature.getGeometry().getCoordinates();
            }

            const line = new ol.geom.LineString([coords[coords.length - 2], coords[coords.length - 1]]);
            const length = parseInt(line.getLength()) / 1000;

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


/**
 * Update the drawing in the map from the form with id and form div.
 * @param drawingId
 * @param drawingDiv
 */
function updateDrawings(drawingId, drawingDiv) {
    let coordinates = [];

    drawingDiv.find('.gps').each(function (index, item) {
        const gps = validateCoordinate($(this));
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
            let feature = new ol.Feature({
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
            let feature = new ol.Feature({
                geometry: new ol.geom.LineString(coordinates)
            });
            feature.setId(drawingId);
            styleDrawing(feature, drawingId.split("drawing")[1]);

            source.addFeature(feature);
            map.getView().fitExtent(feature.getGeometry().getExtent(), map.getSize());// {padding: [170, 50, 30, 150], constrainResolution: false});

        }

    } else if (drawingDiv.hasClass('circle') && coordinates.length > 0 && drawingDiv.find('.radius')[0].checkValidity()) {
        console.log("circle " + coordinates);

        const radius = calculateRadius(drawingDiv.find('.radius').val(), coordinates[0]);
        if (source.getFeatureById(drawingId) != null) {
            source.getFeatureById(drawingId).getGeometry().setCenterAndRadius(coordinates[0], radius);
            map.getView().fitExtent(source.getFeatureById(drawingId).getGeometry().getExtent(), map.getSize());
        }
        else {

            let feature = new ol.Feature({
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
    const view = map.getView();
    const projection = view.getProjection();
    const resolutionAtEquator = view.getResolution();
    const pointResolution = projection.getPointResolution(resolutionAtEquator, coordinate);
    const resolutionFactor = resolutionAtEquator / pointResolution;

    return (value / ol.proj.METERS_PER_UNIT.m) * resolutionFactor;
}