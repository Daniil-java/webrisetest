package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.components.exceptions.ErrorResponse;
import com.kuklin.webrisetest.models.Plan;
import com.kuklin.webrisetest.models.SubscriptionDto;
import com.kuklin.webrisetest.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(
            summary = "Добавление подписки пользователю",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SubscriptionDto.class))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping("/users/{userId}/subscriptions")
    public SubscriptionDto subscribe(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long userId,
            @RequestBody Plan plan) {
        return subscriptionService.subscribeAndGetDto(plan, userId);
    }

    @Operation(
            summary = "Получение списка подписок пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema =
                            @Schema(implementation = SubscriptionDto.class)))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/users/{userId}/subscriptions")
    public List<SubscriptionDto> getUserSubscriptionsById(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long userId) {
        return subscriptionService.getUserSubscriptionDtoList(userId);
    }

    @Operation(
            summary = "Остановка подписки пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SubscriptionDto.class))
                    ),
                    @ApiResponse(
                            description = "Провальный ответ", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @DeleteMapping("/subscriptions/{subId}")
    public SubscriptionDto stopSubscribeById(
            @Parameter(description = "ID подписки", required = true) @PathVariable Long subId) {
        return subscriptionService.stopSubscriptionByIdAndGetDto(subId);
    }

}
