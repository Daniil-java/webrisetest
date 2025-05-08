package com.kuklin.webrisetest.services;

import com.kuklin.webrisetest.components.exceptions.ErrorResponseException;
import com.kuklin.webrisetest.components.exceptions.ErrorStatus;
import com.kuklin.webrisetest.entities.ServicePlan;
import com.kuklin.webrisetest.entities.Subscription;
import com.kuklin.webrisetest.entities.User;
import com.kuklin.webrisetest.models.*;
import com.kuklin.webrisetest.models.mappers.SubscriptionMapper;
import com.kuklin.webrisetest.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ServicePlanService servicePlanService;
    private final UserService userService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDto subscribeAndGetDto(Plan plan, Long userId) {
        return subscriptionMapper.toDto(subscribe(plan, userId));
    }

    /**
     * Подписание пользователя на сервис-подписку
     */
    public Subscription subscribe(Plan plan, Long userId) {
        User user = userService.getUserById(userId);
        ServicePlan servicePlan = servicePlanService
                .getServicePlanByService(plan);

        LocalDate now = LocalDate.now();
        Subscription subscription = new Subscription()
                .setUser(user)
                .setServicePlan(servicePlan)
                .setStartDate(now)
                .setEndDate(now.plusDays(servicePlan.getDurationDays()))
                .setSubscriptionStatus(SubscriptionStatus.LASTS);

        return subscriptionRepository.save(subscription);
    }

    public List<SubscriptionDto> getUserSubscriptionDtoList(Long userId) {
        return subscriptionMapper.toDtoList(getUserSubscriptionList(userId));
    }

    public List<Subscription> getUserSubscriptionList(Long userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    public SubscriptionDto stopSubscriptionByIdAndGetDto(Long id) {
        return subscriptionMapper.toDto(stopSubscriptionById(id));
    }

    /**
     * Прекращение подписки пользователя
     */
    public Subscription stopSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ErrorResponseException(ErrorStatus.SUBSCRIPTION_NOT_FOUND));
        return subscriptionRepository.save(subscription.setSubscriptionStatus(SubscriptionStatus.ENDED));
    }

    public void deleteSubscriptionById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    /**
     * Получение всех незакончившихся подписок
     */
    public List<Subscription> getAllLastsSubscription() {
        return subscriptionRepository.findAllBySubscriptionStatus(SubscriptionStatus.LASTS);
    }
}
