package roomescape.reservation

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JdbcReservationRepositoryTest(
    @Autowired jdbcReservationRepository: JdbcReservationRepository,
) : BehaviorSpec({
        Given("save data") {
            val given =
                listOf(
                    Reservation(1L, "A", "2023-01-01", "2023-01-02"),
                    Reservation(2L, "A", "2023-01-01", "10:00"),
                )
            jdbcReservationRepository.insert(given[0])
            jdbcReservationRepository.insert(given[1])
            When("get") {
                val reservations = jdbcReservationRepository.get()
                Then("returns saved data") {
                    reservations shouldContain given[0]
                    reservations shouldContain given[1]
                }
            }
        }
    })
