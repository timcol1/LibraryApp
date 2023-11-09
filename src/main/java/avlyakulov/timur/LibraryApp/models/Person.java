package avlyakulov.timur.LibraryApp.models;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Ім'я не може бути пустим")
    private String name;

    @Min(value = 1900, message = "Ви маєте бути старшим ніж 1900 рік")
    @Max(value = 2023, message = "Ваша дата народження має бути не більша ніж поточний рік, а саме 2023 рік")
    private int year;

    @Size(min = 2, max = 20, message = "Ваш нікнейм повинен бути від 2 до 20 символів")
    private String username;

    @Size(min = 6, message = "Ваш пароль повинен бути від 6 символів")
    private String password;

    private String role;

    @Pattern(regexp = "\\+380[0-9]{9}", message = "Ваш номер має бути українським та починатись на +380")
    private String phoneNumber;

    @Email(message = "Ведіть вірно ваш імейл")
    @NotEmpty(message = "Email не може бути пустим")
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> bookList;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
