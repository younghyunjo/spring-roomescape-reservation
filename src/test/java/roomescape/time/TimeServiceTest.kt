package roomescape.time

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TimeServiceTest(
    @MockkBean val timeRepository: TimeRepository,
    @Autowired val timeService: TimeService,
) : BehaviorSpec({
        Given("DB에 시간 데이터 추가") {
            val givenTime = listOf(Time(1, "10:00"), Time(2, "11:00"))
            every { timeRepository.get() } returns givenTime
            When("시간 조회하면") {
                val actual = timeService.get()
                Then("DB에 저장된 시간이 조회 된다") {
                    actual shouldBe givenTime
                }
            }
        }
        Given("새 시간") {
            val givenTime = Time(1L, "10:00")
            val t = mutableListOf<Time>()

            every { timeRepository.insert(any()) } answers {
                val time = firstArg<Time>()
                t.add(time)
                time
            }

            every { timeRepository.get() } returns t

            When("시간 추가하면") {
                timeService.add(givenTime)
                Then("시간 조회하면 추가된 시간이 나온다") {
                    val actual = timeService.get()
                    actual shouldContain givenTime
                }
            }
        }
    })
