package softuni.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.library.models.dtos.xmls.CharacterImportDto;
import softuni.library.models.dtos.xmls.CharacterImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.CharacterRepository;
import softuni.library.services.CharacterService;
import softuni.library.util.ValidationUtil;
import softuni.library.models.entities.Character;
import softuni.library.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {
    private final static String CHARACTERS_PATH = "src/main/resources/files/xml/characters.xml";
    private final CharacterRepository characterRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, BookRepository bookRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.characterRepository = characterRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.characterRepository.count() > 0;
    }

    @Override
    public String readCharactersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CHARACTERS_PATH)));
    }

    @Override
    public String importCharacters() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        CharacterImportRootDto characterImportRootDto = this.xmlParser.parseXml(CharacterImportRootDto.class, CHARACTERS_PATH);
        for (CharacterImportDto characterImportDto : characterImportRootDto.getCharacterImportDtos()) {
            Optional<Character> nameAndLastName = this.characterRepository.findAllByFirstNameAndMiddleNameAndLastName(characterImportDto.getFirstName(), characterImportDto.getMiddleName(), characterImportDto.getLastName());
            if(validationUtil.isValid(characterImportDto)&&nameAndLastName.isEmpty()) {
                Character character = this.modelMapper.map(characterImportDto, Character.class);
                Book book = this.bookRepository.findById(characterImportDto.getBook().getId()).get();
                character.setBook(book);
                sb.append(String.format("Successfully imported Character: %s %s %s - age %d", characterImportDto.getFirstName(), characterImportDto.getMiddleName(), characterImportDto.getLastName(), characterImportDto.getAge())).append(System.lineSeparator());
                this.characterRepository.saveAndFlush(character);
            }
            else{
                sb.append("Invalid Character").append(System.lineSeparator());
            }


        }

        return sb.toString();
    }

    @Override
    public String findCharactersInBookOrderedByLastNameDescendingThenByAge() {
        StringBuilder sb = new StringBuilder();
        Set<Character> characters = this.characterRepository.exportCharactersByAge();
            System.out.println();
        for (Character character : characters) {
            sb.append(String.format("Character name %s %s %s, age %d, in book %s", character.getFirstName(), character.getMiddleName(), character.getLastName(), character.getAge(), character.getBook().getName())).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
