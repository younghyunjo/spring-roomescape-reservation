package roomescape.reservation

interface ReservationRepository {
    fun save(reservation: Reservation): Reservation
}
