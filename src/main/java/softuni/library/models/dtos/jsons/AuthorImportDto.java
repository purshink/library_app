package softuni.library.models.dtos.jsons;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthorImportDto {

    @Expose
    @Length(min = 2)
    @NotNull
    private String firstName;
    @Expose
    @Length(min = 2)
    @NotNull
    private String lastName;
    @Expose
    @Length(min = 3, max = 12)
    @NotNull
    private String birthTown;

    public AuthorImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthTown() {
        return birthTown;
    }

    public void setBirthTown(String birthTown) {
        this.birthTown = birthTown;
    }
}
