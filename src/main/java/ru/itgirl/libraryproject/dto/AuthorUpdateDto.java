package ru.itgirl.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class AuthorUpdateDto {
    private Long id;

    @Size(min = 2, max = 15)
    @NotBlank(message = "Необходимо указать имя")
    private String name;

    @Size(min = 2, max = 25)
    @NotBlank(message = "Необходимо указать фамилию")
    private String surname;
}
