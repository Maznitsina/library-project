package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.AuthorCreateDto;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

//   @Override
//   public AuthorDto getAuthorByNameV1(String name) {
//       Author author = authorRepository.findAuthorByName(name).orElseThrow();
//       return convertEntityToDto(author);
//    }
    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Try to find Author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name: {} not found", name);
            throw new IllegalStateException("Автор не найден");
        }
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find Author by Id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id: {} not found", id);
            throw new IllegalStateException("Автор не найден");
        }
    }

//    @Override
//    public AuthorDto getAuthorByNameV2(String name) {
//        Author author = authorRepository.findAuthorByNameBySql(name).orElseThrow();
//        return convertEntityToDto(author);
//    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        log.info("Try to find Author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByNameBySql(name);
        if (author.isPresent()){
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error ("Author with name: {} not found", name);
            throw new IllegalStateException("Автор не найден");
        }
    }

    @Override
    public List<AuthorDto> getAuthorByNameV3(String name) {
        log.info("Try to find Author by name {}", name);
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        List<AuthorDto> authorDtoList = new ArrayList<>();
        List<Author> author = authorRepository.findAll(specification);
        for (Author author1 : author) {
            authorDtoList.add(convertEntityToDto(author1));
        }
        return authorDtoList;
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Try to create Author {}", authorCreateDto);
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Try to update Author {}", authorUpdateDto);
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Try to delete Author by id {}", id);
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }


    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }
}
