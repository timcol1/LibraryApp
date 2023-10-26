package avlyakulov.timur.LibraryApp.controllers;

import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.security_service.EmailServiceRestoringPassword;
import avlyakulov.timur.LibraryApp.security_service.RegistrationService;
import avlyakulov.timur.LibraryApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final EmailServiceRestoringPassword emailServiceRestoringPassword;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, EmailServiceRestoringPassword emailServiceRestoringPassword) {

        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.emailServiceRestoringPassword = emailServiceRestoringPassword;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.register(person);
        return "redirect:auth/login";
    }

    @GetMapping("/restore-pass")
    public String getRestorePassView() {
        return "auth/restore-pass";
    }

    @PostMapping("/restore-pass")
    public String sendSecretCodeFromClient(@ModelAttribute("email") String email) {
        System.out.println(email);
        return "auth/restore-pass";
    }
}