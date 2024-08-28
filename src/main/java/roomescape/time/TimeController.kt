package roomescape.time

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/times")
class TimeController(
    private val timeService: TimeService,
) {
    @PostMapping
    fun add(
        @RequestBody time: Time,
    ): Time = timeService.add(time)

    @GetMapping
    fun get(): List<Time> = timeService.get()

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ) = timeService.delete(id)
}
