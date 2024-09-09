package roomescape.reservation

data class Reservation(
    val id: Long? = null,
    val name: String,
    val date: String,
    val time: String? = null,
    val timeId: Long? = null,
)
