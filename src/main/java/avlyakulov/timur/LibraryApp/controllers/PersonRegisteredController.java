package avlyakulov.timur.LibraryApp.controllers;

import avlyakulov.timur.LibraryApp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PersonRegisteredController {
    private final BookService bookService;

    public PersonRegisteredController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getFreeBooks(Model model,
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
        return "user/list_books";
    }
}