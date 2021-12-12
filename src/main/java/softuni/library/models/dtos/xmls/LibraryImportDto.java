package softuni.library.models.dtos.xmls;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "library")
@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryImportDto {
    @XmlElement
    private String name;
    @XmlElement
    private String location;
    @XmlElement
    private int rating;
    @XmlElement(name = "book")
    private BookOfLibraryImportDto book;

    public LibraryImportDto() {
    }

    @Length(min = 3)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Length(min = 5)
    @NotNull
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Min(value = 1)
    @Max(value = 10)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public BookOfLibraryImportDto getBook() {
        return book;
    }

    public void setBook(BookOfLibraryImportDto book) {
        this.book = book;
    }
}
