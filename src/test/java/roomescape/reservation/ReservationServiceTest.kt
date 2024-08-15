package roomescape.reservation

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReservationServiceTest(
    @MockkBean val reservationRepository: ReservationRepository,
    @Autowired val reservationService: ReservationService,
) : BehaviorSpec({
        Given("reservation") {
            val r = Reservation(name = "name", date = "date", time = "time")
            When("add") {
                every { reservationRepository.save(r) } returns r.copy(id = 1)

                val newReservation = reservationService.add(r)
                Then("id is setted") {
                    newReservation.id shouldBe 1
                }
            }
        }
    })
