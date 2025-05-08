package com.kuklin.webrisetest.services.processors;

import com.kuklin.webrisetest.entities.Subscription;
import com.kuklin.webrisetest.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Процесс проверки срока подписки
 */
@Component
@RequiredArgsConstructor
public class SubscriptionExpiredScheduleProcessor implements ScheduleProcessor {
    private final SubscriptionService subscriptionService;

    @Transactional
    @Override
    public void process() {
        List<Subscription> subscriptions = subscriptionService.getAllLastsSubscription();
        LocalDate now = LocalDate.now();

        for (Subscription sub : subscriptions) {
            if (sub.getEndDate().isBefore(now)) {
                subscriptionService.stopSubscriptionById(sub.getId());
            }
        }
    }
}
