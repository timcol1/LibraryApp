package avlyakulov.timur.LibraryApp;

import avlyakulov.timur.LibraryApp.controllers.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryAppApplicationTests {

    @Autowired
    private BookController bookController;


    @Test
    void contextLoads() {
        assertThat(bookController).isNotNull();//проверяем подтягивается ли наш контроллер с context
    }

}