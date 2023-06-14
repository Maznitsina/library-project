package ru.itgirl.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    @Size(min = 2, max = 12)
    @NotBlank(message = "Необходимо указать Имя")
    private String name;

    @NotBlank(message = "Необходимо указать Фамилию")
    private String surname;
}
