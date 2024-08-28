package roomescape.time

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JdbcTimeRepositoryTest(
    private val jdbcTimeRepository: JdbcTimeRepository,
) : BehaviorSpec({
        Given("데이터 저장") {
            val given = listOf(Time(1L, "10:00"), Time(2L, "11:00"))
            jdbcTimeRepository.insert(given[0])
            jdbcTimeRepository.insert(given[1])

            When("데이터 조회") {
                val actual = jdbcTimeRepository.get()
                Then("저장된 데이터 조회") {
                    actual shouldContain given[0]
                    actual shouldContain given[1]
                }
            }
            When("데이터 삭제") {
                jdbcTimeRepository.delete(1L)
                Then("저장된 데이터 없음") {
                    val actual = jdbcTimeRepository.get()
                    actual.size shouldBe 0
                }
            }
        }
    })
