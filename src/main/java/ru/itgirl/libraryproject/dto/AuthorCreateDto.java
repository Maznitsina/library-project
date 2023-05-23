package ru.itgirl.libraryproject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    private String name;
    private String surname;
}
