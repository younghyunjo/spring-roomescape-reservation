package roomescape.reservation

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import roomescape.time.JdbcTimeRepository
import roomescape.time.Time

@SpringBootTest
class JdbcReservationRepositoryTest(
    @Autowired jdbcReservationRepository: JdbcReservationRepository,
    @Autowired jdbcTimeRepository: JdbcTimeRepository,
) : BehaviorSpec({
        beforeTest {
            val time0 = Time(startAt = "10:00")
            val time1 = Time(startAt = "11:00")
            jdbcTimeRepository.insert(time0)
            jdbcTimeRepository.insert(time1)
        }
        Given("데이터 저장") {
            val given =
                listOf(
                    ReservationCreateRequest("A", "2023-01-01", 1L),
                    ReservationCreateRequest("B", "2023-01-01", 2L),
                )
            val insertedReservation0 = jdbcReservationRepository.insert(given[0])
            val insertedReservation1 = jdbcReservationRepository.insert(given[1])
            When("데이터 불러 옴") {
                val reservations = jdbcReservationRepository.get()
                Then("저장된 데이터 불러옴 ") {
                    reservations shouldContain insertedReservation0
                    reservations shouldContain insertedReservation1
                    reservations[0].time shouldBe Time(1L, "10:00")
                    reservations[1].time shouldBe Time(2L, "11:00")
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
