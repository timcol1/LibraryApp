package avlyakulov.timur.LibraryApp.util;


import avlyakulov.timur.LibraryApp.models.Book;
import avlyakulov.timur.LibraryApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        //проверяем сущетсвует ли книга уже с таким именем в бд
        if (bookService.getBookByName(book.getName()) != null) {
            errors.rejectValue("name", "", "Дана книга вже існує");
        }
    }
}