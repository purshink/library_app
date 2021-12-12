package softuni.library.models.dtos.xmls;

import org.hibernate.validator.constraints.Length;
import softuni.library.config.LocalDateAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "character")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterImportDto {
    @XmlElement
    private int age;

    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthday;

    @XmlElement(name = "first-name")

    private String firstName;

    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement(name = "middle-name")
    private String middleName;

    @XmlElement
    private String role;

    @XmlElement(name = "book")
    private BookOfCharacterImportDto book;

    public CharacterImportDto() {
    }
    @Min(value = 10)
    @Max(value = 66)
    @NotNull
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @NotNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    @Length(min = 3)
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Length(min = 3)
    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Length(max = 1)
    @NotNull
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @Length(min = 5)
    @NotNull
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BookOfCharacterImportDto getBook() {
        return book;
    }

    public void setBook(BookOfCharacterImportDto book) {
        this.book = book;
    }
}
