package roomescape.reservation;

import roomescape.theme.Theme;
import roomescape.theme.ThemeEntity;
import roomescape.time.Time;
import roomescape.time.TimeEntity;

public class Reservation {
    private Long id;
    private String name;
    private Time time;
    private final ReservationDate date;
    private final Theme theme;

    public static Reservation createdByEntity(ReservationEntity reservationEntity, TimeEntity time, ThemeEntity theme) {
        return new Reservation(reservationEntity.getId(),
                reservationEntity.getName(),
                new ReservationDate(reservationEntity.getDate()),
                Time.from(time), Theme.from(theme));
    }

    public Reservation(Long id, String name, String date, Long timeId, String startAt, Long themeId, String themeName, String themeDescription, String thumbnailUrl) {
        this(id, name, new ReservationDate(date), new Time(timeId, startAt), new Theme(themeId, themeName, themeDescription, thumbnailUrl));
    }

    public Reservation(Long id, String name, ReservationDate date, Time time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public ReservationDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
