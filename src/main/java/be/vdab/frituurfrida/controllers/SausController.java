package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.domain.SausRaden;
import be.vdab.frituurfrida.forms.SausRadenForm;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final SausService sausService;
    private final SausRaden sausRaden;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[]{"mayonaise", "ketchup", "cognac"}),
            new Saus(2, "mayonaise", new String[]{"ei", "mosterd"}),
            new Saus(3, "mosterd", new String[]{"mosterd", "azijn", "witte wijn"}),
            new Saus(4, "tartare", new String[]{"mayonaise", "augurk", "tabasco"}),
            new Saus(5, "vinaigrette", new String[]{"olijfolie", "mosterd", "azijn"})};

    SausController(SausService sausService, SausRaden sausRaden) {
        this.sausService = sausService;
        this.sausRaden = sausRaden;
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
        return new ModelAndView("alfabet", "alfabet", alfabet)
                .addObject("sauzenLetter", sauzenMetEerstLetter(letter));

    }

    private List<Saus> sauzenMetEerstLetter(char letter) {
        return sausService.findAll()
                .stream()
                .filter(saus -> saus.getNaam().startsWith(String.valueOf(letter)))
                .collect(Collectors.toList());
    }

    /* <!-------------------------------------->*/

    private String randomSaus() {
        var sauzen = sausService.findAll();
        return sauzen.get(ThreadLocalRandom.current().nextInt(sauzen.size())).getNaam();
    }

    @GetMapping("raden")
    public ModelAndView radenForm() {
        sausRaden.reset(randomSaus());
        return new ModelAndView("sausRaden").addObject(sausRaden)
            .addObject(new SausRadenForm(null));
    }

    @PostMapping("raden/nieuwspel")
    public String radenNieuwSpel() {
        return "redirect:/sauzen/raden";
    }

    @PostMapping("raden")
    public ModelAndView raden(@Valid SausRadenForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("sausRaden").addObject(sausRaden);
        }
        sausRaden.gok(form.getLetter());
        return new ModelAndView("redirect:/sauzen/raden/volgendegok");
    }

    @GetMapping("raden/volgendegok")
    public ModelAndView volgendeGok() {
        return new ModelAndView("sausRaden").addObject(sausRaden)
                .addObject(new SausRadenForm(null));
    }
}





































