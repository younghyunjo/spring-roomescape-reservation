package roomescape.reservation

data class ReservationCreateRequest(
    val name: String,
    val date: String,
    val timeId: Long,
)
