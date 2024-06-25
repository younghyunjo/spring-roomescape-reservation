package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.theme.ThemeRepository;
import roomescape.time.TimeRepository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final static String TABLE_NAME = "reservation";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_DATE = "date";
    private final static String COLUMN_TIME_ID = "time_id";
    private final static String COLUMN_THEME_ID = "theme_id";
    private final static String COLUMN_ID = "id";

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity addReservation(ReservationEntity reservationEntity) {
        String sql = String.format("insert into %s (%s, %s, %s, %s) values(?,?,?,?)", TABLE_NAME, COLUMN_NAME, COLUMN_DATE, COLUMN_TIME_ID, COLUMN_THEME_ID);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{COLUMN_ID});

            ps.setString(1, reservationEntity.getName());
            ps.setString(2, reservationEntity.getDate());
            ps.setLong(3, reservationEntity.getTimeId());
            ps.setLong(4, reservationEntity.getThemeId());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        reservationEntity.setId(generatedId);

        return reservationEntity;
    }

    public void deleteReservation(Long id) {
        String sql = String.format("delete from %s where %s = ?",TABLE_NAME, COLUMN_ID);
        jdbcTemplate.update(sql, id);
    }

    public List<Reservation> reservations() {
        String sql = String.format(
                "SELECT " +
                    "r.id as reservation_id, " +
                    "r.name as reservation_name, " +
                    "r.date as reservation_date, " +
                    "t.id as time_id, " +
                    "t.start_at as time_start_at, " +
                    "h.id as theme_id, "  +
                    "h.name as theme_name, " +
                    "h.description as theme_description, " +
                    "h.thumbnail as theme_thumbnail " +
                "FROM %s as r " +
                "inner join %s as h on r.theme_id = h.id " +
                "inner join %s as t " +
                "on r.time_id = t.id ", TABLE_NAME, ThemeRepository.getTableName(), TimeRepository.getTableName());
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
        new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getString("reservation_date"),
                resultSet.getLong("time_id"),
                resultSet.getString("time_start_at"),
                resultSet.getLong("theme_id"),
                resultSet.getString("theme_name"),
                resultSet.getString("theme_description"),
                resultSet.getString("theme_thumbnail")
        );
}
