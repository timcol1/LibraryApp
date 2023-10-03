package avlyakulov.timur.LibraryApp.controllers;


import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
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
    public String createPerson(@ModelAttribute Person person) {
        personService.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getViewEditing(@PathVariable int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable int id, @ModelAttribute Person person) {
        personService.updatePerson(id, person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }
}