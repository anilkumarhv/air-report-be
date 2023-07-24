package com.anil.airreportbe.specification;

import com.anil.airreportbe.model.entity.Pirep;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.*;

public class PirepSpecification {

    public static Specification<Pirep> getPirepQuery(List<String> stations, ZonedDateTime startDateTime, ZonedDateTime endDateTime, Boolean icingCondition, Boolean skyCondition, Boolean qualityControlCondition, Boolean turbulenceCondition, String type, String visibility, String ceilingBelow) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.in(root.get("aircraft").get("code")).value(stations));
            predicates.add(criteriaBuilder.between(root.get("observationTime"), startDateTime, endDateTime));

//            if (icingCondition) {
//                predicates.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isIcingType"), icingCondition));
//            }
//            if (skyCondition) {
//                predicates.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isSkyType"), skyCondition));
//            }
//            if (qualityControlCondition) {
//                predicates.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isQualityControlFlagType"), qualityControlCondition));
//            }
//            if (turbulenceCondition) {
//                predicates.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isTurbulenceType"), turbulenceCondition));
//            }
            if (!Objects.equals(type, "PIREP")) {
                predicates.add(criteriaBuilder.equal(root.get("aircraft").get("type"), type));
            }

            List<Predicate> orClauseFilters = new ArrayList<>();
            if (icingCondition) {
                orClauseFilters.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isIcingType"), true));
            }
            if (skyCondition) {
                orClauseFilters.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isSkyType"), true));
            }
            if (qualityControlCondition) {
                orClauseFilters.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isQualityControlFlagType"), true));
            }
            if (turbulenceCondition) {
                orClauseFilters.add(criteriaBuilder.equal(root.get("aircraftCondition").get("isTurbulenceType"), true));
            }
            if (orClauseFilters.size() > 0) {
                predicates.add(criteriaBuilder.or(orClauseFilters.toArray(new Predicate[0])));
            }

            if (null != visibility && !visibility.isBlank()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi"), Float.valueOf(visibility)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Pirep> getIcing(Boolean icingCondition) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aircraftCondition").get("isIcingType"), icingCondition);
    }

    public static Specification<Pirep> getSky(Boolean skyCondition) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aircraftCondition").get("isSkyType"), skyCondition);
    }

    public static Specification<Pirep> getQualityControl(Boolean qualityControlCondition) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aircraftCondition").get("isQualityControlFlagType"), qualityControlCondition);
    }

    public static Specification<Pirep> getTB(Boolean turbulenceCondition) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aircraftCondition").get("isTurbulenceType"), turbulenceCondition);
    }
}
