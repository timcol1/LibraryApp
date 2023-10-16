package avlyakulov.timur.LibraryApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Назва книги не може бути пустою")
    private String name;

    @Column(name = "author_name")
    @NotEmpty(message = "Автор не може бути пустим")
    private String authorName;


    @Min(value = 100, message = "Ви вели неправильну дату написання книги")
    @Max(value = 2023, message = "Ви вели неправильну дату написання книги")
    private int year;

    @Column(name = "given_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date givenAt;

    @Transient
    private String simpleDateFormat;

    @Transient
    private boolean overdue;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person owner;


    public Book() {

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getGivenAt() {
        return givenAt;
    }

    public void setGivenAt(Date givenAt) {
        this.givenAt = givenAt;
    }

    public String getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(String simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}