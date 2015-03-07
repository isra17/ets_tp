package ca.etsmtl.log660.controller;

import ca.etsmtl.log660.service.WebflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ParticipantController {

    @Autowired
    WebflixService webflixService;

    @RequestMapping(value = "/participants/{id}", method = RequestMethod.GET)
    public String show(ModelMap models, HttpServletRequest request, @PathVariable Long id) {
        models.addAttribute("participant", webflixService.findParticipant(id));
        return "participants/show";
    }
}