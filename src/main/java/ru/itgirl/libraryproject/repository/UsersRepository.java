package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.dto.UsersDto;
import ru.itgirl.libraryproject.model.Users;

public interface UsersRepository extends JpaRepository <Users, Long> {
    UsersDto findByUsername(String login);
}
