package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.exceptions.*;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;


import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {
    private final FilmService filmService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
    private final UserService userService = new UserService(new InMemoryUserStorage());

 @Test
 void getTopFilms() {

     Film film =  Film.builder()
             .name("Name")
             .description("Description")
             .duration(100L)
             .releaseDate(LocalDate.parse("1896-10-10")).build();
     film.setLikes(new HashSet<>());
     film.getLikes().add(1L);

     filmService.createFilm(film);

     Collection<Film> topFilms = filmService.getTopFilms(2);

     assertEquals(1, topFilms.size());
     assertTrue(topFilms.contains(film));
 }

    @Test
    void testRemoveFriend() {

        User user1 = User.builder()
                .email("@.ru")
                .name("Name")
                .login("gg")
                .birthday(LocalDate.parse("1900-01-01")).build();
        userService.createUser(user1);

        User user2 = User.builder()
                .email("@.rгu")
                .name("Nae")
                .login("g")
                .birthday(LocalDate.parse("1901-01-01")).build();
        userService.createUser(user2);


        userService.addFriend(1, 2);


        assertThrows(NotFoundException.class, () -> userService.removeFriend(1, 999));
    }
}
