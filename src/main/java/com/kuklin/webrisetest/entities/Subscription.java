package com.kuklin.webrisetest.entities;

import com.kuklin.webrisetest.models.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false)
    private ServicePlan servicePlan;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    @CreationTimestamp
    private LocalDate created;
}
