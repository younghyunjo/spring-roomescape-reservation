package roomescape.reservation

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import kotlin.collections.HashMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerE2ETest {
    @Test
    fun redirection() {
        Given {
            log().all()
        } When {
            get("/")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun addTimeAndCreateReservationAndGetReservationAndDeleteReservation() {
        val time: MutableMap<String, String> = HashMap()
        time["startAt"] = "10:00"

        Given {
            log().all().contentType(ContentType.JSON).body(time)
        } When {
            post("/times")
        } Then {
            statusCode(200).body("id", `is`(1)).body("startAt", `is`("10:00"))
        }

        val params: MutableMap<String, String> = HashMap()
        params["name"] = "브라운"
        params["date"] = "2023-08-05"
        params["timeId"] = "1"

        Given {
            log().all().contentType(ContentType.JSON).body(params)
        } When {
            post("/reservations")
        } Then {
            statusCode(200).body("id", `is`(1)).body("time.id", `is`(1)).body("time.startAt", `is`("10:00"))
        }

        Given {
            log().all()
        } When {
            get("/reservations")
        } Then {
            statusCode(200).body("size()", `is`(1))
        }

        Given {
            log().all()
        } When {
            delete("/reservations/1")
        } Then {
            statusCode(200)
        }

        Given {
            log().all()
        } When {
            get("/reservations")
        } Then {
            statusCode(200).body("size()", `is`(0))
        }
    }
}
