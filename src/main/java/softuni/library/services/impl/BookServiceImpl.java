package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.library.models.dtos.jsons.BookImportDto;
import softuni.library.models.entities.Author;
import softuni.library.models.entities.Book;
import softuni.library.repositories.AuthorRepository;
import softuni.library.repositories.BookRepository;
import softuni.library.services.BookService;
import softuni.library.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final static String BOOKS_PATH = "src/main/resources/files/json/books.json";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(BOOKS_PATH)));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder sb = new StringBuilder();
        BookImportDto[] bookImportDtos = this.gson.fromJson(this.readBooksFileContent(), BookImportDto[].class);
        System.out.println();
        for (BookImportDto bookImportDto : bookImportDtos) {
            if(this.validationUtil.isValid(bookImportDto)){
                Book book = this.modelMapper.map(bookImportDto, Book.class);
                Author byId = this.authorRepository.findById((long) bookImportDto.getAuthor()).get();
                book.setAuthor(byId);
                sb.append(String.format("Successfully imported Book: %s written in %s",bookImportDto.getName(), bookImportDto.getWritten())).append(System.lineSeparator());
                this.bookRepository.saveAndFlush(book);
            }
            else{
                sb.append("Invalid Book").append(System.lineSeparator());
            }

        }


        return sb.toString();
    }
}
