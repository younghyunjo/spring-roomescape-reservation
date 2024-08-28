package roomescape.time

interface TimeRepository {
    fun get(): List<Time>

    fun insert(time: Time): Time

    fun delete(id: Long)
}
