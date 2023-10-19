package avlyakulov.timur.LibraryApp.service;

import avlyakulov.timur.LibraryApp.models.Book;
import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getListBooks() {
        return bookRepository.findAll(Sort.by("id"));
    }

    @Transactional
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public Book getBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getGivenAt() != null) {
                setSimpleFormatForBookDate(book);
            }
            return book;
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void editBook(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void appointOwnerForBook(Person newOwner, int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(newOwner);
        book.setGivenAt(new Date());
    }

    @Transactional
    public void releaseBookFromPerson(int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
        book.setGivenAt(null);
    }

    public boolean checkOwner(int id) {
        Book book = bookRepository.findById(id).get();
        return book.getOwner() != null;
    }

    public Book getBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    public List<Book> getPageableBooks(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("id"))).getContent();
    }

    public List<Book> getBooksSortByParameter(String sortBy, String order) {
        switch (sortBy) {
            case "name" -> {
                if (order.equals("asc"))
                    return bookRepository.findAll(Sort.by("name"));
                else
                    return bookRepository.findAll(Sort.by("name").descending());
            }
            case "year" -> {
                if (order.equals("asc"))
                    return bookRepository.findAll(Sort.by("year"));
                else
                    return bookRepository.findAll(Sort.by("year").descending());
            }
            default -> {
                return bookRepository.findAll(Sort.by("name").descending());
            }
        }
    }

    public List<Book> findBookByNameStartingWith(String name) {
        if (name.isEmpty())
            return Collections.emptyList();
        else {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            name = new String(chars);
            return bookRepository.findAllByNameStartingWith(name);
        }
    }

    public List<Book> getOverdueBooks() {
        List<Book> books = getListBooks();
        books = books.stream().filter(b -> b.getOwner() != null).collect(Collectors.toList());
        setOverdueForBooks(books);
        books = filterOverdueBooks(books);
        books.forEach(this::setSimpleFormatForBookDate);
        return books;
    }

    public void setOverdueForBooks(List<Book> books) {
        books.forEach(b -> b.setOverdue(TimeUnit.MILLISECONDS.toDays(new Date().getTime() - b.getGivenAt().getTime()) >= 30));
    }

    public List<Book> filterOverdueBooks(List<Book> books) {
        return books.stream().filter(Book::isOverdue).collect(Collectors.toList());
    }

    public void setSimpleFormatForBookDate(Book book) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatDate = simpleDateFormat.format(book.getGivenAt());
        book.setSimpleDateFormat(formatDate);
    }
}