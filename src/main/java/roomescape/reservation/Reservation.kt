package roomescape.reservation

import roomescape.time.Time

data class Reservation(
    val id: Long? = null,
    val name: String,
    val date: String,
    val time: Time? = null,
)
