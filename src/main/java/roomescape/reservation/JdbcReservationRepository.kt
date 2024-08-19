package roomescape.reservation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class JdbcReservationRepository(
    private val jdbcTemplate: JdbcTemplate,
) : ReservationRepository {
    override fun insert(reservation: Reservation): Reservation {
        val sql = generateInsertSql()
        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, arrayOf(COLUMN_ID))
            ps.setString(1, reservation.name)
            ps.setString(2, reservation.date)
            ps.setString(3, reservation.time)
            ps
        }, keyHolder)

        val generatedId = keyHolder.key?.toLong() ?: throw SQLException("Failed to generate ID")
        return reservation.copy(id = generatedId)
    }

    override fun get(): List<Reservation> {
        val sql = generateSelectSql()
        return jdbcTemplate.query(sql) { rs, _ -> mapRowToReservation(rs) }
    }

    override fun delete(id: Long) {
        val sql = generateDeleteSql()
        jdbcTemplate.update(sql, id)
    }

    private fun generateDeleteSql(): String =
        """
        DELETE FROM $TABLE WHERE $COLUMN_ID = ?
        """.trimIndent()

    private fun generateSelectSql() =
        """
        SELECT $COLUMN_ID, $COLUMN_NAME, $COLUMN_DATE, $COLUMN_TIME 
        FROM $TABLE
        """.trimIndent()

    private fun generateInsertSql() =
        """
        INSERT INTO $TABLE 
        ($COLUMN_NAME, $COLUMN_DATE, $COLUMN_TIME)  
        VALUES (?, ?, ?)
        """.trimIndent()

    companion object {
        private const val TABLE = "reservation"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"

        private fun mapRowToReservation(rs: ResultSet): Reservation =
            Reservation(
                id = rs.getLong(COLUMN_ID),
                name = rs.getString(COLUMN_NAME),
                date = rs.getString(COLUMN_DATE),
                time = rs.getString(COLUMN_TIME),
            )
    }
}
