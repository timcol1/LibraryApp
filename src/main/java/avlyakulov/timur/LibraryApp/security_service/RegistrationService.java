package avlyakulov.timur.LibraryApp.security_service;

import avlyakulov.timur.LibraryApp.models.Person;
import avlyakulov.timur.LibraryApp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}