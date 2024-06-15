package roomescape.theme;

public class ThemeEntity {
    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public static ThemeEntity of(String name, String description, String thumbnail) {
        return new ThemeEntity(null, name, description, thumbnail);
    }

    public ThemeEntity(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
