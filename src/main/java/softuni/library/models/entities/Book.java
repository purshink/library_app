package softuni.library.models.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book  extends BaseEntity{

    private String name;
    private int edition;
    private LocalDate written;
    private String description;
    private Author author;
    Set<Character> characters;
    Set<Library> libraries;

    public Book() {
    }

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    public Set<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    public Set<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }
    @Column(nullable = false)
    public LocalDate getWritten() {
        return written;
    }

    public void setWritten(LocalDate written) {
        this.written = written;
    }
    @Column(columnDefinition="text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
