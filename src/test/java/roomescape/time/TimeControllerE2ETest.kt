package roomescape.time

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeControllerE2ETest {
    @Test
    fun redirect() {
        Given {
            log().all()
        } When {
            get("/admin/time")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun addAndGetDelete() {
        val params: MutableMap<String, String> = HashMap()
        params["startAt"] = "10:00"

        Given {
            log().all().contentType(ContentType.JSON).body(params)
        } When {
            post("/times")
        } Then {
            statusCode(200).body("id", `is`(1))
        }

        Given {
            log().all()
        } When {
            get("/times")
        } Then {
            statusCode(200).body("size()", `is`(1))
        }

        Given {
            log().all()
        } When {
            delete("/times/1")
        } Then {
            statusCode(200)
        }

        Given {
            log().all()
        } When {
            get("/times")
        } Then {
            statusCode(200).body("size()", `is`(0))
        }
    }
}
