package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("taal")
class TaalController {

    @GetMapping
    public ModelAndView taal(@RequestHeader("Accept-Language") String acceptedL) {
       return new ModelAndView("taal","taal",acceptedL.startsWith("nl"));
    }
}
