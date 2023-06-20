package ru.itgirl.libraryproject.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetByNameV1() {
        Long id = 1L;
        String name = "Harry Potter";
        Set<Book> books = new HashSet<>();
        Genre genre = new Genre(5L, "Novel", books);
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);

        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV1(name);

        verify(bookRepository).findBookByName(name);
        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetBookByNameV1NotFound() {
        String name = "Book";
        when(bookRepository.findBookByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> bookService.getByNameV1(name));

        verify(bookRepository).findBookByName(name);
    }

    @Test
    public void testGetByNameV2() {
        Long id = 1L;
        String name = "It";
        Set<Book> books = new HashSet<>();
        Genre genre = new Genre(5L, "Horror", books);
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);

        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV2(name);

        verify(bookRepository).findBookByNameBySql(name);
        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetBookByNameV2NotFound() {
        String name = "Книга";
        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> bookService.getByNameV2(name));

        verify(bookRepository).findBookByNameBySql(name);
    }



}
