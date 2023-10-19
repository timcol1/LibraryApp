package avlyakulov.timur.LibraryApp.repository;

import avlyakulov.timur.LibraryApp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByName(String name);

    List<Book> findAllByNameStartingWith(String name);

    @Query("SELECT b FROM Book b WHERE b.owner is not null and b.givenAt <= :date")
    List<Book> getOverdueBooksBy30Days(@Param("date") Date date);
}