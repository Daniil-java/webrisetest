package com.kuklin.webrisetest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "Модель пользователя")
public class UserDto {
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "Электронная почта")
    @NotNull
    private String email;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
}
