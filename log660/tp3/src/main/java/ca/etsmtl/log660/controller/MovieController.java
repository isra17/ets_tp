package ca.etsmtl.log660.controller;

import ca.etsmtl.log660.model.Client;
import ca.etsmtl.log660.model.Film;
import ca.etsmtl.log660.service.WebflixException;
import ca.etsmtl.log660.service.WebflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    WebflixService webflixService;
    
    @RequestMapping(value = {"/", "/films"})
    public String list(ModelMap models) {

        List<Film> movies = webflixService.getFilms();
        List<String> langues = webflixService.getLangues();

        models.addAttribute("movies", movies);
        models.addAttribute("langues", langues);
        
        //Represents the .html path from resources/templates
        return "movies/list";
    }
    
    @RequestMapping(value = "/films/search", method = RequestMethod.POST)
    public String search(ModelMap models, HttpServletRequest request) {
        Long anneeMin = !request.getParameter("anneeMin").isEmpty() ? Long.parseLong(request.getParameter("anneeMin")) : -1;
        Long anneeMax = !request.getParameter("anneeMax").isEmpty() ? Long.parseLong(request.getParameter("anneeMax")) : -1;
        
        List<Film> movies = webflixService.searchFilms(
                request.getParameter("titre"), 
                anneeMin, 
                anneeMax, 
                request.getParameter("pays"), 
                request.getParameter("langue"), 
                request.getParameter("genre"), 
                request.getParameter("realisateur"), 
                request.getParameter("acteur")
        );
        List<String> langues = webflixService.getLangues();
        
        models.addAttribute("movies", movies);
        models.addAttribute("langues", langues);
        
        //Represents the .html path from resources/templates
        return "movies/list";
    }

    @RequestMapping(value = "/films/{id}/louer", method = RequestMethod.POST)
    public String rent(HttpServletRequest request, Principal principal, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Client client = (Client)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        try {
            webflixService.rentFilm(client, id);
        } catch(WebflixException exception) {
            redirectAttributes.addFlashAttribute("error", exception);
        }

        return "redirect:/films/" + id;
    }

    @RequestMapping(value = "/films/{id}", method = RequestMethod.GET)
    public String show(ModelMap models, HttpServletRequest request, @PathVariable Long id) {
        models.addAttribute("movie", webflixService.findFilm(id));
        models.addAttribute("recommendations", webflixService.findFilmRecommendations(id));
        return "movies/show";
    }
}
