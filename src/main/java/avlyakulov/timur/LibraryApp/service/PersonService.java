package avlyakulov.timur.LibraryApp.service;

import avlyakulov.timur.LibraryApp.models.Book;
import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    public List<Person> getPeople() {
        return personRepository.findAll(Sort.by("name")).stream().filter(p -> p.getRole().equals("ROLE_USER")).toList();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void createPerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        if (foundPerson.isPresent()) {
            Hibernate.initialize((foundPerson.get().getBookList()));
            List<Book> books = foundPerson.get().getBookList();
            //books.forEach(b -> b.setOverdue(TimeUnit.MILLISECONDS.toDays(new Date().getTime() - b.getGivenAt().getTime()) >= 10));
            return books;
        } else {
            return Collections.emptyList();
        }
    }

    public List<Person> findPeopleByNameStartingWith(String name) {
        if (name.equals(""))
            return Collections.emptyList();
        else {
            return personRepository.findAllByNameStartingWith(name).stream().filter(p -> p.getRole().equals("ROLE_USER")).toList();
        }
    }

    public Optional<Person> findPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}