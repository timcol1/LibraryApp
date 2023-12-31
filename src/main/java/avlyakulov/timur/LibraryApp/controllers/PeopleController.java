package avlyakulov.timur.LibraryApp.controllers;


import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.service.PersonService;
import avlyakulov.timur.LibraryApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public PeopleController(PersonValidator personValidator, PersonService personService, PasswordEncoder passwordEncoder) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public String getViewPeople(Model model,
                                @RequestParam(value = "search", required = false) Optional<String> searchQuery) {
        if (searchQuery.isPresent()) {
            model.addAttribute("people", personService.findPeopleByNameStartingWith(searchQuery.get()));
            model.addAttribute("search", true);
        } else {
            model.addAttribute("people", personService.getPeople());
        }
        int a = 123;
        return "people/people_list";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable int id, Model model) {
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "people/person";
    }


    @GetMapping("/new")
    public String getViewCreatePerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/add_person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/add_person";
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personService.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getViewEditing(@PathVariable int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit_person";
        }
        personService.updatePerson(id, person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }
}