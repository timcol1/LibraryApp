package avlyakulov.timur.LibraryApp.repository;

import avlyakulov.timur.LibraryApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByNameStartingWith(String name);

    Optional<Person> findByUsername(String username);

    @Query("select p.email from Person p where p.username =:username")
    String getEmailByUsername(@Param("username") String username);
}