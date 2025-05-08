package com.kuklin.webrisetest.entities;

import com.kuklin.webrisetest.models.Plan;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ServicePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Plan name;
    private String description;
    private Long durationDays;
    private BigDecimal baseCost;
    @UpdateTimestamp
    private LocalDate updated;
    @CreationTimestamp
    private LocalDate created;
}
