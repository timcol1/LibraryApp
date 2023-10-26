package avlyakulov.timur.LibraryApp.controllers;

import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.security_service.EmailService;
import avlyakulov.timur.LibraryApp.security_service.PersonRestorePasswordService;
import avlyakulov.timur.LibraryApp.security_service.RegistrationService;
import avlyakulov.timur.LibraryApp.service.PersonService;
import avlyakulov.timur.LibraryApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private final PersonService personService;
    private final RegistrationService registrationService;
    private final EmailService emailService;

    private final PersonRestorePasswordService personRestorePasswordService;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, RegistrationService registrationService, EmailService emailService, PersonRestorePasswordService personRestorePasswordService) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.registrationService = registrationService;
        this.emailService = emailService;
        this.personRestorePasswordService = personRestorePasswordService;
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
    public String sendSecretCodeFromClient(@ModelAttribute("username") String username, Model model) {
        model.addAttribute("email", personRestorePasswordService.codeEmail(personRestorePasswordService.getEmailUserByHisUsername(username)));
        model.addAttribute("secret_code", "1504");
        return "auth/restore-pass";
    }
}