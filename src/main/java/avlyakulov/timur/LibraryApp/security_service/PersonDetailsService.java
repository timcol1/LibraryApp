package avlyakulov.timur.LibraryApp.security_service;

import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.repository.PersonRepository;
import avlyakulov.timur.LibraryApp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isPresent())
            return new PersonDetails(person.get());
        else {
            throw new UsernameNotFoundException("Користувач з таким ім'ям для логіну не існує");
        }
    }
}