package com.kuklin.webrisetest.services;

import com.kuklin.webrisetest.services.processors.SubscriptionExpiredScheduleProcessor;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final SubscriptionExpiredScheduleProcessor subscriptionExpiredScheduleProcessor;

    @Scheduled(cron = "0 0 0,12 * * *")
    private void scheduleProcess() {
        subscriptionExpiredScheduleProcessor.process();
    }
}
