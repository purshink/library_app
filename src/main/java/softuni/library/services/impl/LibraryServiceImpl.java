package softuni.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.library.models.dtos.xmls.LibraryImportDto;
import softuni.library.models.dtos.xmls.LibraryImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.models.entities.Library;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.LibraryRepository;
import softuni.library.services.LibraryService;
import softuni.library.util.ValidationUtil;
import softuni.library.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {
    private final static String LIBRARIES_PATH = "src/main/resources/files/xml/libraries.xml";
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public LibraryServiceImpl(LibraryRepository libraryRepository, BookRepository bookRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.libraryRepository.count() > 0;
    }

    @Override
    public String readLibrariesFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(LIBRARIES_PATH)));
    }

    @Override
    public String importLibraries() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        LibraryImportRootDto rootDto = this.xmlParser.parseXml(LibraryImportRootDto.class, LIBRARIES_PATH);
        for (LibraryImportDto libraryImportDto : rootDto.getLibraryImportDtos()) {
            Optional<Library> libraryByName = this.libraryRepository.getLibraryByName(libraryImportDto.getName());

            if(this.validationUtil.isValid(libraryImportDto) && libraryByName.isEmpty()){
                Library library = this.modelMapper.map(libraryImportDto, Library.class);
                Book book = this.bookRepository.findById(libraryImportDto.getBook().getId()).get();
                Set<Book> books = new HashSet<>();
                books.add(book);
                library.setBooks(books);
                sb.append(String.format("Successfully added Library: %s - %s", libraryImportDto.getName(), libraryImportDto.getLocation())).append(System.lineSeparator());
                this.libraryRepository.saveAndFlush(library);
            }

            else{
                sb.append("Invalid Library").append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
