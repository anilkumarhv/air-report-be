package com.anil.airreportbe.specification;

import com.anil.airreportbe.model.entity.Metar;
import com.anil.airreportbe.model.entity.SkyCondition;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MetarSpecification {

    public static Specification<Metar> getMetarQuery(List<String> stations, ZonedDateTime startDateTime, ZonedDateTime endDateTime, Boolean isPirepCondition, Boolean isPirepMissing, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.in(root.get("aircraft").get("code")).value(stations));
            predicates.add(criteriaBuilder.between(root.get("observationTime"), startDateTime, endDateTime));

//            if (!Objects.equals(type, "METAR")) {
            predicates.add(criteriaBuilder.equal(root.get("aircraft").get("type"), type));
//            }

            List<Predicate> orClauseFilters = new ArrayList<>();
            if (isPirepCondition) {
                orClauseFilters.add(criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi"), 5));
                orClauseFilters.add(criteriaBuilder.isNotNull(root.get("windGustKt")));
                orClauseFilters.add(criteriaBuilder.like(root.get("wxString"), "(?<!\\w)(?:TS|TSRA)(?!\\w)"));
//                orClauseFilters.add(criteriaBuilder.lessThanOrEqualTo(root.get("skyConditions").get("cloudBaseFtAgl"),5000));
            }
            if (orClauseFilters.size() > 0) {
                predicates.add(criteriaBuilder.or(orClauseFilters.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Metar> getMetarQueryWithJoin(List<String> stations, ZonedDateTime startDateTime, ZonedDateTime endDateTime, Boolean isPirepCondition, Boolean isPirepMissing, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            ListJoin<Metar, SkyCondition> skyConditionListJoin = root.joinList("skyConditions", JoinType.LEFT);

            predicates.add(criteriaBuilder.in(root.get("aircraft").get("code")).value(stations));
            predicates.add(criteriaBuilder.between(root.get("observationTime"), startDateTime, endDateTime));

            predicates.add(criteriaBuilder.equal(root.get("aircraft").get("type"), type));

            List<Predicate> orClauseFilters = new ArrayList<>();
            if (isPirepCondition) {
                orClauseFilters.add(criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi"), 5));
                orClauseFilters.add(criteriaBuilder.isNotNull(root.get("windGustKt")));
                orClauseFilters.add(criteriaBuilder.like(root.get("wxString"), "(?<!\\w)(?:TS|TSRA)(?!\\w)"));
                orClauseFilters.add(criteriaBuilder.lessThanOrEqualTo(skyConditionListJoin.get("cloudBaseFtAgl"), 5000));
            }

            if (orClauseFilters.size() > 0) {
                predicates.add(criteriaBuilder.or(orClauseFilters.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
