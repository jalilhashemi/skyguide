package ch.fhnw.skyguide.domain;

public class Specialflight {
    private final long id;
    private final String content;

    public Specialflight(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
