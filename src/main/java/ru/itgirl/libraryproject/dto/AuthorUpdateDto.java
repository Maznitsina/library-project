package ru.itgirl.libraryproject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class AuthorUpdateDto {
    private Long id;
    private String name;
    private String surname;
}
