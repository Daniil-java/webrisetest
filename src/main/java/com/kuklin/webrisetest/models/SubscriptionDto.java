package com.kuklin.webrisetest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Schema(description = "Модель пользователя")
public class SubscriptionDto {
    @Schema(description = "Идентификатор подписки")
    private Long id;
    @Schema(description = "Модель пользователя")
    private UserDto user;
    @Schema(description = "Модель сервиса")
    private ServicePlanDto servicePlan;
    @Schema(description = "Статус подписки")
    private SubscriptionStatus subscriptionStatus;
    @Schema(description = "Дата начала подписки")
    private LocalDate startDate;
    @Schema(description = "Дата окончания подписки")
    private LocalDate endDate;
}
