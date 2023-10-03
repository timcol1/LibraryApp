package avlyakulov.timur.LibraryApp.repository;

import avlyakulov.timur.LibraryApp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByName(String name);

    List<Book> findAllByNameStartingWith(String name);
}