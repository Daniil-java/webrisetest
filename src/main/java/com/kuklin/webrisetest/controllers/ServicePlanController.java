package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.models.ServicePlanDto;
import com.kuklin.webrisetest.services.ServicePlanService;
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
    @GetMapping("/subscriptions/top")
    public List<ServicePlanDto> getTop3MostPopularSubscriptions() {
        return servicePlanService.getTop3MostPopularSubscriptions();
    }

}
