package roomescape.reservation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcReservationRepository(
    private val jdbcTemplate: JdbcTemplate,
) : ReservationRepository {
    private val simpleJdbcInsert =
        SimpleJdbcInsert(jdbcTemplate)
            .withTableName(TABLE)
            .usingGeneratedKeyColumns(COLUMN_ID)

    override fun insert(reservation: ReservationCreateRequest): Reservation {
        val params =
            mapOf(
                COLUMN_NAME to reservation.name,
                COLUMN_DATE to reservation.date,
                COLUMN_TIME_ID to reservation.timeId,
            )

        val generatedId = simpleJdbcInsert.executeAndReturnKey(params).toLong()
        return getById(generatedId)!!
    }

    fun getById(id: Long): Reservation? {
        val sql = generateSelectByIdSql()
        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ -> mapRowToReservation(rs) }.firstOrNull()
    }

    override fun get(): List<Reservation> {
        val sql = generateSelectSql()
        return jdbcTemplate.query(sql) { rs, _ -> mapRowToReservation(rs) }
    }

    override fun delete(id: Long) {
        val sql = generateDeleteSql()
        jdbcTemplate.update(sql, id)
    }

    private fun generateSelectByIdSql(): String =
        """
        SELECT $COLUMN_ID, $COLUMN_NAME, $COLUMN_DATE, $COLUMN_TIME_ID FROM $TABLE WHERE $COLUMN_ID = ? 
        """.trimIndent()

    private fun generateDeleteSql(): String =
        """
        DELETE FROM $TABLE WHERE $COLUMN_ID = ?
        """.trimIndent()

    private fun generateSelectSql() =
        """
        SELECT $COLUMN_ID, $COLUMN_NAME, $COLUMN_DATE, $COLUMN_TIME_ID 
        FROM $TABLE
        """.trimIndent()

    companion object {
        private const val TABLE = "reservation"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME_ID = "time_id"

        private fun mapRowToReservation(rs: ResultSet): Reservation =
            Reservation(
                id = rs.getLong(COLUMN_ID),
                name = rs.getString(COLUMN_NAME),
                date = rs.getString(COLUMN_DATE),
                timeId = rs.getLong(COLUMN_TIME_ID),
            )
    }
}
