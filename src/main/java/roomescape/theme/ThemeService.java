package roomescape.theme;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme add(ThemeRequest newThumbnail) {
        ThemeEntity entity=  themeRepository.add(newThumbnail.toEntity());
        return Theme.from(entity);
    }


    public List<Theme> themes() {
        return themeRepository
                .themes()
                .stream()
                .map(Theme::from)
                .toList();
    }

    public void delete(Long id) {
        themeRepository.delete(id);
    }
}
