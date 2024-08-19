package roomescape.reservation

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JdbcReservationRepositoryTest(
    @Autowired jdbcReservationRepository: JdbcReservationRepository,
) : BehaviorSpec({
        Given("데이터 저장") {
            val given =
                listOf(
                    Reservation(1L, "A", "2023-01-01", "2023-01-02"),
                    Reservation(2L, "A", "2023-01-01", "10:00"),
                )
            jdbcReservationRepository.insert(given[0])
            jdbcReservationRepository.insert(given[1])
            When("데이터 불러 옴") {
                val reservations = jdbcReservationRepository.get()
                Then("저장된 데이터 불러옴 ") {
                    reservations shouldContain given[0]
                    reservations shouldContain given[1]
                }
            }
            When("삭제 후 불러오기") {
                jdbcReservationRepository.delete(1L)
                val reservations = jdbcReservationRepository.get()
                Then("데이터가 1개 삭제 됨") {
                    reservations.size shouldBe 1
                    reservations[0].id shouldBe 2L
                }
            }
        }
    })
