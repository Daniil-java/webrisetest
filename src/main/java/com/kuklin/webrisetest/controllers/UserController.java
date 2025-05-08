package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.components.exceptions.ErrorResponse;
import com.kuklin.webrisetest.models.UserDto;
import com.kuklin.webrisetest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Управление пользователями", description = "Методы работы с пользователями")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Создание нового пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping
    public UserDto createUser(
            @Parameter(description = "Модель пользователя", required = true) @RequestBody @Validated UserDto userDto) {
        return userService.createUserAndGetDto(userDto);
    }

    @Operation(
            summary = "Получение пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public UserDto getUserById(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id) {
        return userService.getUserDto(id);
    }

    @Operation(
            summary = "Изменение пользовательских данных",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public UserDto updateUser(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id,
            @Parameter(description = "Модель пользователя", required = true) @RequestBody @Validated UserDto userDto) {
        return userService.updateUserByIdAndGetDto(id, userDto);
    }

    @Operation(
            summary = "Удаление пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
