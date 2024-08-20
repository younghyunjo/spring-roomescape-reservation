package roomescape.reservation

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReservationServiceTest(
    @MockkBean val reservationRepository: ReservationRepository,
    @Autowired val reservationService: ReservationService,
) : BehaviorSpec({
        Given("예약 ID") {
            val givenIdForAdd = 1L
            When("add 메서드 호출하면") {
                val reservation = Reservation(name = "브라운", date = "2023-01-01", time = "10:00")
                every { reservationRepository.insert(reservation) } returns reservation.copy(id = givenIdForAdd)

                val newReservation = reservationService.add(reservation)
                Then("예약 ID가 설정된다.") {
                    newReservation.id shouldBe givenIdForAdd
                }
            }
        }
        Given("예약 목록") {
            val givenReservations = listOf(Reservation(1L, "A", "2023-01-01", "10:00"), Reservation(2L, "B", "2023-01-11", "11:00"))
            When("get 메서드 호출") {
                every { reservationRepository.get() } returns givenReservations
                val reservations = reservationService.get()
                Then("예약 목록을 반환한다") {
                    reservations shouldBe givenReservations
                }
            }
            When("delete 호출") {
                every { reservationRepository.delete(any()) } just Runs
                reservationService.delete(1L)
                Then("에러가 없다") {
                }
            }
        }
    })
