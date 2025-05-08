package com.kuklin.webrisetest.controllers;

import com.kuklin.webrisetest.models.Plan;
import com.kuklin.webrisetest.models.SubscriptionDto;
import com.kuklin.webrisetest.services.SubscriptionService;
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

    //POST /users/{id}/subscriptions - добавить подписку
    //GET /users/{id}/subscriptions - получить подписки пользователя
    //DELETE /users/{id}/subscriptions/{sub_id} - удалить подписку

    @PostMapping("/users/{userId}/subscriptions")
    public SubscriptionDto subscribe(@PathVariable Long userId,
                                     @RequestBody Plan plan) {
        return subscriptionService.subscribeAndGetDto(plan, userId);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public List<SubscriptionDto> getUserSubscriptionsById(@PathVariable Long userId) {
        return subscriptionService.getUserSubscriptionDtoList(userId);
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    public SubscriptionDto stopSubscribeById(@PathVariable Long subId) {
        return subscriptionService.stopSubscriptionByIdAndGetDto(subId);
    }

}
