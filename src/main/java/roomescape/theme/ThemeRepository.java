package roomescape.theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeRepository {
    private final static String TABLE_NAME = "theme";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_DESCRIPTION = "description";
    private final static String COLUMN_THUMBNAIL = "thumbnail";

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public ThemeEntity add(ThemeEntity entity) {
        String sql = String.format("insert into %s (%s, %s, %s) values(?, ?, ?)",
                TABLE_NAME, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_THUMBNAIL);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{COLUMN_ID}
            );

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getThumbnail());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        entity.setId(generatedId);
        return entity;
    }

    public List<ThemeEntity> themes() {
        String sql = String.format("select %s, %s, %s, %s from %s",
                COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_THUMBNAIL, TABLE_NAME);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(Long id) {
        String sql = String.format("delete from %s where %s = ?", TABLE_NAME, COLUMN_ID);
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<ThemeEntity> rowMapper = (resultSet, rowNum) ->
            new ThemeEntity(
                    resultSet.getLong(COLUMN_ID),
                    resultSet.getString(COLUMN_NAME),
                    resultSet.getString(COLUMN_DESCRIPTION),
                    resultSet.getString(COLUMN_THUMBNAIL)
            );

    public ThemeEntity theme(Long themeId) {
        String sql = String.format("select %s, %s, %s, %s from %s where id = ?",
                COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_THUMBNAIL, TABLE_NAME);
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new ThemeEntity(
                resultSet.getLong(COLUMN_ID),
                resultSet.getString(COLUMN_NAME),
                resultSet.getString(COLUMN_DESCRIPTION),
                resultSet.getString(COLUMN_THUMBNAIL)
        ), themeId);
    }
}
