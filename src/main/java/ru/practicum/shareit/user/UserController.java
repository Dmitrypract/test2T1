package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.validation.marker.Create;
import ru.practicum.shareit.validation.marker.Update;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto saveUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        User user = userService.save(UserMapper.mapToUser(userDto));
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя");
        return UserMapper.mapToUserDto(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Validated(Update.class) @RequestBody UserDto userDto, @PathVariable long userId) {
        User user = userService.update(UserMapper.mapToUser(userDto), userId);
        log.info("Получен PATCH-запрос к эндпоинту: '/userId' на обновление пользователя");
        return UserMapper.mapToUserDto(user);
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable long id) {
        User user = userService.findById(id);
        log.info("Получен GET-запрос к эндпоинту: '/userId' на получения пользователя по id");
        return UserMapper.mapToUserDto(user);
    }

    @GetMapping
    public Collection<UserDto> findAllUsers() {
        log.info("Получен GET-запрос к эндпоинту: '/users' на получения всех пользователей");
        return userService
                .findAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        log.info("Получен DELETE-запрос к эндпоинту: '/users' на удаление пользователя с ID={}", id);
        userService.deleteById(id);
    }
}
