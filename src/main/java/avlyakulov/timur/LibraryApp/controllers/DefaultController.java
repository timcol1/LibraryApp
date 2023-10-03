package avlyakulov.timur.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        else if (request.isUserInRole("ROLE_LIBRARIAN")) {
            return "redirect:/librarian";
        }
        return "redirect:/user/books";
    }
}