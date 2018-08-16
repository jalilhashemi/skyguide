package ch.fhnw.skyguide.domain;

import java.util.List;

public class DrawingDTO {
    private Integer radius;
    private Integer altitude;
    private String drawingType;
    private List<CoordinateDTO> coordinates;

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getDrawingType() {
        return drawingType;
    }

    public void setDrawingType(String drawingType) {
        this.drawingType = drawingType;
    }

    public List<CoordinateDTO> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinateDTO> coordinates) {
        this.coordinates = coordinates;
    }
}
