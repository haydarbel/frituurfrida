package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final SausService sausService;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[]{"mayonaise", "ketchup", "cognac"}),
            new Saus(2, "mayonaise", new String[]{"ei", "mosterd"}),
            new Saus(3, "mosterd", new String[]{"mosterd", "azijn", "witte wijn"}),
            new Saus(4, "tartare", new String[]{"mayonaise", "augurk", "tabasco"}),
            new Saus(5, "vinaigrette", new String[]{"olijfolie", "mosterd", "azijn"})};

    SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView sauzen() {
        return new ModelAndView("sauzen", "sauzen", sausService.findAll());
    }

    @GetMapping("{nummer}")
    public ModelAndView sausVind(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        sausService.findAll().stream().filter(saus -> saus.getNummer() == nummer)
                .findFirst()
                .ifPresent(saus -> modelAndView.addObject("saus", saus));
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("alfabet", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView sausVindVanAlfabet(@PathVariable char letter) {
        return new ModelAndView("alfabet","alfabet", alfabet)
                .addObject( "sauzenLetter", sauzenMetEerstLetter(letter));

    }

    private List<Saus> sauzenMetEerstLetter(char letter) {
        return sausService.findAll()
                .stream()
                .filter(saus -> saus.getNaam().startsWith(String.valueOf(letter)))
                .collect(Collectors.toList());
    }
}