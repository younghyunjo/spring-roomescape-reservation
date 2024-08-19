package roomescape.reservation

interface ReservationRepository {
    fun insert(reservation: Reservation): Reservation

    fun get(): List<Reservation>

    fun delete(id: Long)
}
