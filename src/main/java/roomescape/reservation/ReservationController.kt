package roomescape.reservation

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservations")
class ReservationController(
    private val reservationService: ReservationService,
) {
    @GetMapping
    fun get(): List<Reservation> = reservationService.get()

    @PostMapping
    fun add(
        @RequestBody reservation: Reservation,
    ): Reservation = reservationService.add(reservation)

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ) = reservationService.delete(id)
}
