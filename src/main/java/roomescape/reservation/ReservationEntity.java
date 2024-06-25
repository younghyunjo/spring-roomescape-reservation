package roomescape.reservation;


public class ReservationEntity {
    private Long id;
    private String name;
    private String date;
    private Long timeId;
    private Long themeId;

    public static ReservationEntity of(String name, String date, Long timeId, Long themeId) {
        return new ReservationEntity(null, name, date, timeId, themeId);
    }

    private ReservationEntity(Long id, String  name, String date, Long timeId, Long themeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
