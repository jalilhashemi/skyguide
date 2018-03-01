package ch.fhnw.skyguide.domain;

import java.util.List;

public class DrawingDTO {
    private String drawingType;
    private List<CoordinateDTO> coordinates;

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
