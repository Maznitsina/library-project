package ru.itgirl.libraryproject.service;

import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.model.Book;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);
}