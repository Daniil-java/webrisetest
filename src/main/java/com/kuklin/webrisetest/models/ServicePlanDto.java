package com.kuklin.webrisetest.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ServicePlanDto {
    private Long id;
    private Plan name;
    private String description;
    private BigDecimal baseCost;
}
