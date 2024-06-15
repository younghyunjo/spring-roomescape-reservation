package roomescape.theme;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    Theme add(@RequestBody  ThemeRequest request) {
        return themeService.add(request);
    }

    @GetMapping
    List<Theme> themes() {
        return themeService.themes();
    }
}
