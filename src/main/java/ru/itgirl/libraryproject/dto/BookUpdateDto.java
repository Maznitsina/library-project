package ru.itgirl.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {
    private Long id;

    @Size(min = 1, max = 40)
    @NotBlank(message = "Необходимо указать название")
    private String name;

    @NotBlank(message = "Необходимо указать id жанра")
    private Long genre;
}
