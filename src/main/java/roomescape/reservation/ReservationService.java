package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.theme.ThemeEntity;
import roomescape.theme.ThemeRepository;
import roomescape.time.TimeEntity;
import roomescape.time.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.themeRepository = themeRepository;
    }

    public List<Reservation> reservations() {
        return reservationRepository.reservations();
    }

    public Reservation add(NewReservation newReservation) {
        ReservationEntity entity =  reservationRepository.addReservation(newReservation.toEntity());
        TimeEntity time = timeRepository.time(entity.getTimeId());
        ThemeEntity theme = themeRepository.theme(entity.getThemeId());
        return Reservation.createdByEntity(entity, time, theme);
    }

    public void delete(long id) {
        reservationRepository.deleteReservation(id);
    }
}
