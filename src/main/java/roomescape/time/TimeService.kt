package roomescape.time

import org.springframework.stereotype.Service

@Service
class TimeService(
    private val timeRepository: TimeRepository,
) {
    fun get(): List<Time> = timeRepository.get()

    fun add(time: Time): Time = timeRepository.insert(time)

    fun delete(id: Long) {
        timeRepository.delete(id)
    }
}
