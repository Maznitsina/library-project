package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

//    @Override
//    public BookDto getByNameV1(String name) {
//        Book book = bookRepository.findBookByName(name).orElseThrow();
    //       return convertEntityToDto(book);
    //   }

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Книга не найдена");
        }
    }

//    @Override
//    public BookDto getByNameV2(String name) {
//        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
//        return convertEntityToDto(book);
//    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Книга не найдена");
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        log.info("Try to find book by name {}", name);
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new IllegalStateException("Книга не найдена");
        }

//        Book book = bookRepository.findOne(specification).orElseThrow();
//        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Try to create book {}", bookCreateDto);
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Try to update book by {}", bookUpdateDto);
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Try to delete book by id {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private BookDto convertEntityToDto(Book book) {
        List<AuthorDto> authorDtoList = null;
        if (book.getAuthors() != null) {
            authorDtoList = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDto.builder()
                            .id(author.getId())
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .toList();
        }

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
        return bookDto;
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findById(bookCreateDto.getGenre()).orElseThrow();
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();
    }
}