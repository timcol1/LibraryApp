package avlyakulov.timur.LibraryApp.controllers;


import avlyakulov.timur.LibraryApp.models.Book;
import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.service.BookService;
import avlyakulov.timur.LibraryApp.service.PersonService;
import avlyakulov.timur.LibraryApp.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/librarian")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    @GetMapping("")
    public String getViewBooks(Model model,
                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "items_per_page", required = false) Optional<Integer> itemsPerPage,
                               @RequestParam(value = "sort", required = false) Optional<String> sortBy,
                               @RequestParam(value = "order", required = false) Optional<String> orderSort,
                               @RequestParam(value = "search", required = false) Optional<String> searchQuery) {
        if (page.isPresent() && itemsPerPage.isPresent()) {
            model.addAttribute("books", bookService.getPageableBooks(page.get(), itemsPerPage.get()));
        } else if (sortBy.isPresent() && orderSort.isPresent()) {
            model.addAttribute("books", bookService.getBooksSortByParameter(sortBy.get(), orderSort.get()));
        } else if (searchQuery.isPresent()) {
            model.addAttribute("books", bookService.findBookByNameStartingWith(searchQuery.get()));
            model.addAttribute("search", true);
        } else {
            model.addAttribute("books", bookService.getListBooks());
        }
        return "books/book_list";
    }

    @GetMapping("/new")
    public String getViewCreateBook(@ModelAttribute("book") Book book) {
        return "books/add_book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/add_book";
        }
        bookService.createBook(book);
        return "redirect:/librarian";
    }

    @GetMapping("/{id}")
    public String getViewBook(@PathVariable int id, Model model) {
        Book book = bookService.getBook(id);
        if (!bookService.checkOwner(id)) {
            model.addAttribute("people", personService.getPeople());
            model.addAttribute("person", new Person());
        }
        model.addAttribute("book", book);
        return "books/book";
    }

    @GetMapping("/{id}/edit")
    public String getViewEditBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable int id, @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/edit_book";
        }
        bookService.editBook(id, book);
        return "redirect:/librarian/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/librarian";
    }

    @PatchMapping("/{id}/appoint")
    public String appointOwnerForBook(@PathVariable int id, @ModelAttribute Person person) {
        bookService.appointOwnerForBook(person, id);
        return "redirect:/librarian";
    }

    @PatchMapping("/{id}/release")
    public String appointOwnerForBook(@PathVariable int id) {
        bookService.releaseBookFromPerson(id);
        return "redirect:/librarian";
    }
}