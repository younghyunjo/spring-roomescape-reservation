package roomescape.time

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcTimeRepository(
    private val jdbcTemplate: JdbcTemplate,
) : TimeRepository {
    private val simpleJdbcInsert =
        SimpleJdbcInsert(jdbcTemplate)
            .withTableName(TABLE)
            .usingGeneratedKeyColumns(COLUMN_ID)

    override fun get(): List<Time> {
        val sql = generateSelectSql()
        return jdbcTemplate.query(sql) { rs, _ -> mapRowToTime(rs) }
    }

    override fun insert(time: Time): Time {
        val params =
            mapOf(
                COLUMN_START_AT to time.startAt,
            )
        val generatedId = simpleJdbcInsert.executeAndReturnKey(params)
        return time.copy(id = generatedId.toLong())
    }

    override fun delete(id: Long) {
        val sql = generateDeleteSql()
        jdbcTemplate.update(sql, id)
    }

    private fun generateSelectSql() =
        """
        SELECT $COLUMN_ID, $COLUMN_START_AT 
        FROM $TABLE
        """.trimIndent()

    private fun generateDeleteSql() =
        """
        DELETE FROM $TABLE WHERE $COLUMN_ID = ?
        """.trimIndent()

    companion object {
        const val TABLE = "reservation_time"
        const val COLUMN_ID = "id"
        const val COLUMN_START_AT = "start_at"

        private fun mapRowToTime(rs: ResultSet): Time =
            Time(
                id = rs.getLong(COLUMN_ID),
                startAt =
                    rs.getString(
                        COLUMN_START_AT,
                    ),
            )
    }
}
