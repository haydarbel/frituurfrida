package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.sessions.ZoekDeSaus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("galgje")
public class RaadController {
    private final ZoekDeSaus zoekDeSaus;

    public RaadController(ZoekDeSaus zoekDeSaus) {
        this.zoekDeSaus = zoekDeSaus;
    }


}
