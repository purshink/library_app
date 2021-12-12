package softuni.library.models.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "characters")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterImportRootDto {

    @XmlElement(name = "character")
    List<CharacterImportDto> characterImportDtos;

    public CharacterImportRootDto() {
    }

    public List<CharacterImportDto> getCharacterImportDtos() {
        return characterImportDtos;
    }

    public void setCharacterImportDtos(List<CharacterImportDto> characterImportDtos) {
        this.characterImportDtos = characterImportDtos;
    }
}
