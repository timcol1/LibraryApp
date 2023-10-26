package avlyakulov.timur.LibraryApp.security_service;

import avlyakulov.timur.LibraryApp.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonRestorePasswordService {
    private final PersonRepository personRepository;

    private final EmailService emailService;

    public PersonRestorePasswordService(PersonRepository personRepository, EmailService emailService) {
        this.personRepository = personRepository;
        this.emailService = emailService;
    }

    public String getEmailUserByHisUsername(String username) {
        return personRepository.getEmailByUsername(username);
    }

    public int sendSecretCodeToUserAndGetSecretCode(String username) {
        return emailService.sendEmail(getEmailUserByHisUsername(username));
    }

    public String codeEmail(String email) {
        String[] split = email.split("@[a-z]*\\.[a-z]*");
        String emailPart = split[0];
        int length = emailPart.length();
        char[] codedEmail = email.toCharArray();
        for (int i = 1; i < length - 1; ++i) {
            codedEmail[i] = '*';
        }
        return String.valueOf(codedEmail);
    }
}