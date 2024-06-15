package roomescape.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("reservation")
    public String reservation() {
        return "admin/reservation-new";
    }

    @GetMapping("time")
    public String time() {
        return "admin/time";
    }
}
