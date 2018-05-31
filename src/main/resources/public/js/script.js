var informationJSON;
var actualAircraftTypeList;
var map;
var restUrl = 'http://localhost:8080';
var timeIndex = 0;
var drawingIndex = 0;
var Modify;
var Draw;
var drawings = [];
var source = new ol.source.Vector();
var vector = new ol.layer.Vector({
    source: source,
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 0, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: '#FF0000',
            width: 2
        }),
        image: new ol.style.Circle({
            radius: 7,
            fill: new ol.style.Fill({
                color: '#FF0000'
            })
        })
    })
});
var ctr = new ol.layer.Vector({
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(0, 255, 0, 0.3)'
        }),
        stroke: new ol.style.Stroke({
            color: '#00FF00',
            width: 2
        }),
        image: new ol.style.Circle({
            radius: 7,
            fill: new ol.style.Fill({
                color: '#00FF00'
            })
        })
    }),
    source: new ol.source.Vector({
        url: 'ctr.kml',
        format: new ol.format.KML({
            projection: 'EPSG:4326'
        })
    })
});

var tma = new ol.layer.Vector({
    source: new ol.source.Vector({
        url: 'tma.kml',
        format: new ol.format.KML({
            projection: 'EPSG:4326'
        }),
        style: new ol.style.Style({
            fill: new ol.style.Fill({
                color: 'rgba(0, 255, 0, 0.5)'
            }),
            stroke: new ol.style.Stroke({
                color: '#00FF00',
                width: 10
            }),
            image: new ol.style.Circle({
                radius: 7,
                fill: new ol.style.Fill({
                    color: '#00FF00'
                })
            })
        })
    })
});
var validForm = true;

$(document).ready(function () {
    $("#icon_loading").hide();
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
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
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
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
        });
}

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
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
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
    initializeDropdowns();
    initializeDateRangePicker();
    initializeTooltips();
    initializeMap();
    initDrawTool();

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
    $('#altitude_label').addClass('display-none');
    $('.drawing').addClass('display-none');
    $('#addScnt').addClass('display-none');
    $('.time_field').addClass('display-none');

    $('.custom-control-input').parent().addClass('display-none');
    $('.custom-control-input').addClass('display-none');
    $('.custom-control-input').prop('checked', false);
    $('.custom-control-input').prop('required', false);

    // empty all fields
    $('input.data.activity-data').val('');
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
            $('#addScnt').removeClass('display-none');
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

    // TODO:  changed activity / aircraft type makes fields invalid, validate time when more than one


    $('.data').each(function (index, item) {
        var isValid;
        if ($(this).hasClass('heightType')) {
            isValid = $('input[name=heightType]:checked').val() != undefined;
            formValidate(isValid);
            $(this).find('input').each(function () {
                validateField($(this), isValid);
            });
        }
        else {
            isValid = item.checkValidity();
            validateField($(this), isValid);
            formValidate(isValid);
            if ($(this).hasClass('time')) {
                formValidate(validateTimes($(this)));

            }

            console.log($(this).attr('id') + ": " + isValid);
        }
    });

    validateDrawings();


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


    var ctrZone = ctr.getSource().getFeatures();
    var tmaZone = tma.getSource().getFeatures();
    var features = source.getFeatures();

    var tmaIntersections = [];
    var ctrIntersections = [];

    for (var i = 0; i < features.length; i++) {
        var feature = features[i];
        var doIntersect = false;
        for (var j = 0; j < ctrZone.length; j++) {
            var zone = ctrZone[j];
            if (ol.extent.intersects(feature.getGeometry().getExtent(), zone.getGeometry().getExtent())) {
                doIntersect = true;
            }
        }
        if (doIntersect)
            ctrIntersections.push(feature.getId());
    }
    for (var i = 0; i < features.length; i++) {
        var feature = features[i];
        var doIntersect = false;
        for (var j = 0; j < tmaZone.length; j++) {
            var zone = tmaZone[j];
            if (ol.extent.intersects(feature.getGeometry().getExtent(), zone.getGeometry().getExtent())) {
                doIntersect = true;
            }
        }
        if (doIntersect)
            tmaIntersections.push(feature.getId());
    }

    // no need of SUA
    if (ctrIntersections.length == 0 && tmaIntersections.length == 0) {
        if (validForm)
            $('#not-needed_success').modal('show');
    }

    // else an SUA is needed
    /* else {
         var msg = "CTR intersections: ";
         ctrIntersections.forEach(function (a) {
             msg += a + " ";
         });
         msg += "<br>TMA intersections: ";
         tmaIntersections.forEach(function (a) {
             msg += a + " ";
         });

     }*/

}

function validateDrawings() {
    var isValid;
    if ($('#drawing1').length) {

        for (var i = 1; $('#drawing' + i).length != 0; i++) {
            $('#drawing' + i).find('.gps').each(function (index, item) {
                formValidate(validateCoordinate($(this)) != null)

            });
            isValid = $('#drawing' + i).find('.altitude')[0].checkValidity();
            validateField($('.altitude'), isValid);
            formValidate(isValid);


            if ($('#drawing' + i).find('.radius').length) {
                isValid = $('#drawing' + i).find('.radius')[0].checkValidity();
                validateField($('.radius'), isValid);
                formValidate(isValid);

            }
        }
        formValidate(true);
        validateMap(true);

        checkIntersections();
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


function initializeChangeHandlers() {


    $(document).on('click', '#btn_submit', function () {
        event.preventDefault();

        validForm = true;
        if (validateForm()) {
            console.log("submit form");

            submitApplication();

            // surveymokey call
            (function (t, e, s, o) {
                var n, c, l;
                t.SMCX = t.SMCX || [], e.getElementById(o) || (n = e.getElementsByTagName(s), c = n[n.length - 1], l = e.createElement(s), l.type = "text/javascript", l.async = !0, l.id = o, l.src = ["https:" === location.protocol ? "https://" : "http://", "widget.surveymonkey.com/collect/website/js/tRaiETqnLgj758hTBazgdyWCzZc0WCTHJj5wcZa9Sy55DklTrOQ9l8n_2F2szZz4B9.js"].join(""), c.parentNode.insertBefore(l, c))
            })
            (window, document, "script", "smcx-sdk");
        }
        else {
            $('html,body').scrollTop(0);
            $('#form-feedback').show()
        }

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
        if (!($(this).hasClass('gps') || $(this).hasClass('radius'))) {
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
                    $('#altitude_label').removeClass('display-none');
                    map.updateSize();
                    // add time button
                    $('#addScnt').removeClass('display-none');

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
                $('#altitude_label').removeClass('display-none');
                map.updateSize();
                $('#addScnt').removeClass('display-none');
                $.each(aircraftType.fieldList, function (i, field) {
                    processField(field);
                });
            }
        });

    });

    $(document).on('click', '#addScnt', function () {
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
            .prop('required', true).end();
    });

    $(document).on('click', '.remove_time_button', function () {
        var row = $(this).parents('.form-row');

        // Remove element containing the option
        row.remove();
    });

    $(document).on('change', '#check-layer-icao', function () {
        if ($('#check-layer-icao').is(':checked')) {
            setLayerVisible(1, true);
        }
        else {
            setLayerVisible(1, false);
        }
    });

    $(document).on('change', '#check-layer-ctr', function () {
        if ($('#check-layer-ctr').is(':checked')) {
            setLayerVisible(2, true);
        }
        else {
            setLayerVisible(2, false);
        }
    });


    $(document).on('change', '#check-layer-tma', function () {
        if ($('#check-layer-tma').is(':checked')) {
            setLayerVisible(3, true);
        }
        else {
            setLayerVisible(3, false);
        }
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

    $(document).on('click', '#btn_draw_rectangle', function () {
        Draw.setActive(true, 'Rectangle');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_point', function () {
        Draw.setActive(true, 'Point');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_polygon', function () {
        Draw.setActive(true, 'Polygon');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_circle', function () {
        Draw.setActive(true, 'Circle');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_path', function () {
        Draw.setActive(true, 'Path');
        Modify.setActive(false);
    });

    $(document).on('click', '#btn_draw_modify', function () {
        Draw.setActive(false);
        Modify.setActive(true);
    });
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

    Modify.setActive(false);

}

function emptyForm() {
    $('#container_fields').find('input').each(function () {
        $(this).removeClass('is-invalid');
        $(this).removeClass('is-valid');
        if ($(this).attr('name') == 'heightType') {
            console.log("height");
            $(this).checked = false;
        } else {
            $(this).val("");

        }


    });

    // remove drawings
    source.clear();
}

function validateRadius(field) {
    var isValid = field.val() > 0 && field.val() < 500;
    validateField(field, isValid);
    return isValid;
}

function addPathDrawingDiv() {
    drawingIndex++;
    var template = $('#path_template'),
        clone = template
            .clone()
            .removeClass('display-none')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            .attr('data-drawing-index', drawingIndex)
            .prepend('<h3>Path ' + drawingIndex + '</h3>')
            //  .addClass('time_field')
            .insertBefore($('#map-container'))
            .find('input').prop('required', true);

    return drawingIndex;
}

function addPolygonDrawingDiv() {
    drawingIndex++;
    var template = $('#polygon_template'),
        clone = template
            .clone()
            .removeClass('display-none')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            .attr('data-drawing-index', drawingIndex)
            .prepend('<h3>Polygon ' + drawingIndex + '</h3>')
            //  .addClass('time_field')
            .insertBefore($('#map-container'))
            .find('input').prop('required', true);
    return drawingIndex;
}


function addCircleDrawingDiv() {
    drawingIndex++;
    var template = $('#circle_template'),
        clone = template
            .clone()
            .removeClass('display-none')
            .attr('id', 'drawing' + drawingIndex)
            //.prop('required', true)
            .attr('data-drawing-index', drawingIndex)
            .prepend('<h3>Circle ' + drawingIndex + '</h3>')
            //  .addClass('time_field')
            .insertBefore($('#map-container'))
            .find('input').prop('required', true);
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
            .fail(function (xhr, status, errorThrown) {
                console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
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
    /*   feature.setStyle(new ol.style.Style({
           fill: new ol.style.Fill({
               color: 'rgba(255, 0, 0, 0.3)'
           }),
           stroke: new ol.style.Stroke({
               color: '#FF0000',
               width: 2
           }),
           image: new ol.style.Circle({
               radius: 7,
               fill: new ol.style.Fill({
                   color: '#FF0000'
               })
           }),
           text: new ol.style.Text({
               font: '20px Calibri,sans-serif',
               fill: new ol.style.Fill({color: '#000000'}),
               stroke: new ol.style.Stroke({color: '#FFFFFF'}),
               text: id
           })
       }));*/
}

function initDrawTool() {

    Modify = {
        init: function () {
            this.select = new ol.interaction.Select();
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

            selectedFeatures.on('add', function (e) {

                e.element.on('change', function (e) {

                    var drawingId = $(this)[0].getId();
                    var drawingDiv = $('#' + drawingId);
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
                });
            });
        },
        setActive: function (active) {
            this.select.setActive(active);
            this.modify.setActive(active);
        }
    }
    ;

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
            /*this.Point.on('drawend', function (evt) {
                var feature = evt.feature;
                var geometry = feature.getGeometry().getCoordinates();
                var gps = ol.proj.transform(geometry, 'EPSG:21781', 'EPSG:4326');
                drawings.push({"drawingType": "Point", "coordinates": [{"lat": gps[0], "lon": gps[1]}]});
                console.log(drawings);
                $('#field_gps_coord').val(gps);
            });*/
            this.Path.on('drawend', function (evt) {
                var drawingId = addPathDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingPathDiv(evt.feature, drawingId);

            });
            this.Polygon.on('drawend', function (evt) {
                var drawingId = addPolygonDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingPolygonDiv(evt.feature, drawingId);
            });
            this.Circle.on('drawend', function (evt) {
                var drawingId = addCircleDrawingDiv();
                styleDrawing(evt.feature, drawingId);
                fillDrawingCircleDiv(evt.feature, drawingId);
            });
            this.Rectangle.on('drawend', function (evt) {
                var drawingId = addPolygonDrawingDiv();
                styleDrawing(evt.feature, drawingId);

                fillDrawingPolygonDiv(evt.feature, drawingId);
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

    // TODO: points
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

function initializeMap() {

    // default center position
    var lat = 46.78;
    var lon = 9.3;

    var loc = ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:21781');

    map = new ga.Map({

        // Define the div where the map is placed
        target: 'map',

        // Define the layers to display
        layers: [
            ga.layer.create('ch.swisstopo.pixelkarte-farbe'),
            ga.layer.create('ch.bazl.luftfahrtkarten-icao'),
            ctr,
            tma,
            vector
        ],
        crossOrigin: 'null',

        // disable scrolling on map
        interactions: ol.interaction.defaults({mouseWheelZoom: false})

    });

    map.getView().setCenter(loc);
    map.getView().setResolution(500);

    setLayerVisible(1, false);

}

function setView(loc) {
    map.getView().setCenter(loc);
    map.getView().setResolution(50);
}

function submitApplication() {
    $("#icon_loading").show();

    var data = {};

    $('.data').each(
        function (index) {
            var input = $(this);
            data[input.attr('name')] = input.val();
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

    var drawings = [];

    var i = 1;
    while ($('#drawing' + i).val() != undefined) {
        var drawing = {};

        drawing['altitude'] = $('#drawing' + i + ' .altitude').val();

        if ($('#drawing' + i).hasClass('path')) {
            drawing['drawingType'] = 'Path';


        } else if ($('#drawing' + i).hasClass('circle')) {
            drawing['drawingType'] = 'Circle';
            drawing['radius'] = $('#drawing' + i + ' .radius').val();
        }
        else if ($('#drawing' + i).hasClass('polygon')) {
            drawing['drawingType'] = 'Polygon';

        }
        drawing['coordinates'] = [];

        $('#drawing' + i + ' .gps').each(function (index) {

            drawing['coordinates'][index] = {
                'lat': $(this).val(),
                'lon': $(this).val()
            };
        });
        drawings.push(drawing);
        i++;
    }

    data['drawings'] = drawings;

    if (data['heightType'] == undefined)
        data['heightType'] = 'none';

    if (data['aircraftType'] == '')
        data['aircraftType'] = 'none';

// var success = '{"email":"jalil.hashemi@students.fhnw.ch", "drawings":[' + drawings + '], "times" : [{"start":"12:00", "end":"13:00"}], "name":"adsf","company":"Mfddfarco", "activityType" : "Airshow", "aircraftType" : "RPAS", "heightType": "m GND", "location" : "Windisch"}';
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
            $('#submit_success').modal('show');

        })
        .fail(function (xhr, status, errorThrown) {
            console.error(("Fail!\nerror: " + errorThrown + "\nstatus: " + status));
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
            end.parent().find('.invalid-feedback').html("The until time must be after the from time.");
            validateField(end, false);
            return false;
        }
        else {
            validateField(start, true);
            return true;
        }
    }

}
