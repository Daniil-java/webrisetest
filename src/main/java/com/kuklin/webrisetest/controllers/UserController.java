package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.models.UserDto;
import com.kuklin.webrisetest.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    //- Создание пользователя.
    //- Получение информации о пользователе.
    //- Обновление данных пользователя.
    //- Удаление пользователя.

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUserAndGetDto(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserDto(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody UserDto userDto) {
        return userService.updateUserByIdAndGetDto(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
