package roomescape.reservation

interface ReservationRepository {
    fun insert(reservation: ReservationCreateRequest): Reservation

    fun get(): List<Reservation>

    fun delete(id: Long)
}
