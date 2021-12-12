package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.library.models.dtos.jsons.AuthorImportDto;
import softuni.library.models.entities.Author;
import softuni.library.repositories.AuthorRepository;
import softuni.library.services.AuthorService;
import softuni.library.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHORS_PATH = "src/main/resources/files/json/authors.json";
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public String readAuthorsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(AUTHORS_PATH)));
    }

    @Override
    public String importAuthors() throws IOException {
        StringBuilder sb = new StringBuilder();
        AuthorImportDto[] authorImportDtos = this.gson.fromJson(this.readAuthorsFileContent(), AuthorImportDto[].class);

        for (AuthorImportDto authorImportDto : authorImportDtos) {
            Optional<Author> uniquenessAuthor = this.authorRepository.getAuthorByFirstNameAndLastName(authorImportDto.getFirstName(), authorImportDto.getLastName());
            if(this.validationUtil.isValid(authorImportDto) && uniquenessAuthor.isEmpty()){
                sb.append(String.format("Successfully imported Author: %s %s -  %s",authorImportDto.getFirstName(), authorImportDto.getLastName(),authorImportDto.getBirthTown())).append(System.lineSeparator());
                this.authorRepository.saveAndFlush(this.modelMapper.map(authorImportDto, Author.class));
            }
            else{
                sb.append("Invalid Author").append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
