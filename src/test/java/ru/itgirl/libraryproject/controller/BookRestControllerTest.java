package ru.itgirl.libraryproject.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject.dto.BookDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBookByName() throws Exception {
        String bookName = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book").param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV2() throws Exception {
        String bookName = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v2").param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV3() throws Exception {
        String bookName = "Война и мир";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v3").param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }
}
