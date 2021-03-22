package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/")
 class IndexController {
    @GetMapping
    public ModelAndView index(@CookieValue Optional<Integer> aantalBezoeken, HttpServletResponse response) {
        var openOfGesloten = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "gesloten":"open";
        var modelAndView =  new ModelAndView("index", "gesloten", openOfGesloten);
        modelAndView.addObject("adres", new Adres("DekenhabrekenLaan", "50",
                new Gemeente("Beringen", 5050)));
        var nieuwAantalBezoeken = aantalBezoeken.orElse(0)+1;
        var cookie = new Cookie("aantalBezoeken",String.valueOf(nieuwAantalBezoeken));
        cookie.setMaxAge(31_536_000);
        cookie.setPath("/");
        response.addCookie(cookie);
        modelAndView.addObject("aantalBezoeken", nieuwAantalBezoeken);
        return modelAndView;
    }
}
