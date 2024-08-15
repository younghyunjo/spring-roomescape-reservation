package roomescape.reservation

import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {
    fun getReservations(): List<Reservation> = emptyList()

    fun add(reservation: Reservation): Reservation {
        val newReservation = reservationRepository.save(reservation)
        return newReservation
    }

    fun deleteReservation(id: Long) {
    }
}
