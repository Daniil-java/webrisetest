package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.components.exceptions.ErrorResponse;
import com.kuklin.webrisetest.models.ServicePlanDto;
import com.kuklin.webrisetest.models.SubscriptionDto;
import com.kuklin.webrisetest.services.ServicePlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions/")
@RequiredArgsConstructor
@Slf4j
public class ServicePlanController {
    private final ServicePlanService servicePlanService;

    //Получение топ-3 подписок за все время
    @Operation(
            summary = "Получение топ-3 самых популярных сервисов",
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
    @GetMapping("/subscriptions/top")
    public List<ServicePlanDto> getTop3MostPopularSubscriptions() {
        return servicePlanService.getTop3MostPopularSubscriptions();
    }

}
