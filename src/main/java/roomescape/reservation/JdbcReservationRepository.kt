package roomescape.reservation

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.SQLException

@Repository
class JdbcReservationRepository(
    private val jdbcTemplate: JdbcTemplate,
) : ReservationRepository {
    override fun save(reservation: Reservation): Reservation {
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

    private fun generateInsertSql() = "INSERT INTO $TABLE ($COLUMN_NAME, $COLUMN_DATE, $COLUMN_TIME) VALUES (?, ?, ?)"

    companion object {
        private val TABLE = "reservation"
        private val COLUMN_ID = "id"
        private val COLUMN_NAME = "name"
        private val COLUMN_DATE = "date"
        private val COLUMN_TIME = "time"
    }
}
