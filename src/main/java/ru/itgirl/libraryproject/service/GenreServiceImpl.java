package ru.itgirl.libraryproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.dto.GenreDto;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

//    @Override
//    public GenreDto getGenreById(Long id) {
//        Genre genre = genreRepository.findById(id).orElseThrow();
//        return convertToDto(genre);
//    }
    @Override
    public GenreDto getGenreById(Long id) {
        log.info("Try to find genre with id {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            GenreDto genreDto = convertToDto(genre.get());
            log.info("Genre: {}", genreDto.toString());
            return genreDto;
        } else {
            log.error("Genre with id {} not found", id);
            throw new IllegalStateException("Жанр не найден");
        }
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> {
                    List<AuthorDto> authorDtoList = book.getAuthors()
                            .stream()
                            .map(author -> AuthorDto.builder()
                                    .id(author.getId())
                                    .name(author.getName())
                                    .surname(author.getSurname())
                                    .build()
                            ).toList();
                    return BookDto.builder()
                            .authors(authorDtoList)
                            .id(book.getId())
                            .name(book.getName())
                            .build();
                }) .toList();
                    return GenreDto.builder()
                            .books(bookDtoList)
                            .id(genre.getId())
                            .name(genre.getName())
                            .build();
                }

    }

