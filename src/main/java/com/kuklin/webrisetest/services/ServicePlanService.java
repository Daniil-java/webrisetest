package com.kuklin.webrisetest.services;

import com.kuklin.webrisetest.components.exceptions.ErrorResponseException;
import com.kuklin.webrisetest.components.exceptions.ErrorStatus;
import com.kuklin.webrisetest.entities.ServicePlan;
import com.kuklin.webrisetest.models.Plan;
import com.kuklin.webrisetest.models.ServicePlanDto;
import com.kuklin.webrisetest.models.mappers.ServicePlanMapper;
import com.kuklin.webrisetest.repositories.ServicePlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicePlanService {
    private final ServicePlanRepository servicePlanRepository;
    private final ServicePlanMapper servicePlanMapper;

    //Получение сервиса-подписки
    public ServicePlan getServicePlanByServiceAndIncreaseCounter(Plan plan) {
        //Проверка существования сервиса-подписки
        ServicePlan servicePlan = servicePlanRepository.findServicePlanByName(plan)
                .orElseThrow(() -> new ErrorResponseException(ErrorStatus.SERVICE_PLAN_NOT_FOUND));

        servicePlanRepository.save(
                servicePlan.setSubscribeCount(servicePlan.getSubscribeCount() + 1));
        return servicePlan;
    }

    //Получение топ самых популярных подписок
    public List<ServicePlanDto> getTop3MostPopularSubscriptions() {
        Integer topSize = 3;
        List<ServicePlan> servicePlanDtos = servicePlanRepository
                .findTopMostPopularSubscriptions(PageRequest.of(0, topSize));
        return servicePlanMapper.toDtoList(servicePlanDtos);
    }
}
