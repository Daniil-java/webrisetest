package com.kuklin.webrisetest.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class SubscriptionDto {
    private Long id;
    private UserDto user;
    private ServicePlanDto servicePlan;
    private SubscriptionStatus subscriptionStatus;
    private LocalDate startDate;
    private LocalDate endDate;
}
