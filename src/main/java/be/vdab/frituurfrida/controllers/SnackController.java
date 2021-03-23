package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.BeginLetters;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("snacks")
class SnackController {
    private final SnackService snackService;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView snacks() {
        return new ModelAndView("snacks", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView snackByletter(@PathVariable char letter) {
        return new ModelAndView("snacks", "alfabet", alfabet)
                .addObject("snacksByLetter", snackService.findByBeginNaam(String.valueOf(letter)));

    }

    @GetMapping("beginnaam/form")
    public ModelAndView beginNaamForm() {
        return new ModelAndView("beginnaam")
                .addObject(new BeginLetters("letter hier"));
    }

    @GetMapping("beginnaam")
    public ModelAndView beginNaam(@Valid BeginLetters form, Errors error) {
        var modelAndView = new ModelAndView("beginnaam");
        if (error.hasErrors()) {
            return modelAndView;
        }
        return new ModelAndView("beginnaam", "snacks",
                snackService.findByBeginNaam(form.getLetters()));
    }
}
