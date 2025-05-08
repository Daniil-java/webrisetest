package com.kuklin.webrisetest.repositories;

import com.kuklin.webrisetest.entities.Subscription;
import com.kuklin.webrisetest.models.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByUserId(Long userId);
    List<Subscription> findAllBySubscriptionStatus(SubscriptionStatus subscriptionStatus);
}
