package roomescape.reservation

import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
) {
    fun add(reservation: Reservation): Reservation {
        val newReservation = reservationRepository.insert(reservation)
        return newReservation
    }

    fun get(): List<Reservation> = reservationRepository.get()

    fun delete(id: Long) {
        reservationRepository.delete(id)
    }
}
