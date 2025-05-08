package com.kuklin.webrisetest.repositories;

import com.kuklin.webrisetest.entities.ServicePlan;
import com.kuklin.webrisetest.models.Plan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePlanRepository extends JpaRepository<ServicePlan, Long> {
    Optional<ServicePlan> findServicePlanByName(Plan plan);

    @Query("SELECT s FROM ServicePlan s ORDER BY s.subscribeCount DESC")
    List<ServicePlan> findTopMostPopularSubscriptions(Pageable pageable);
}
