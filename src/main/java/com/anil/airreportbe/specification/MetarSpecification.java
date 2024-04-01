package com.anil.airreportbe.specification;

import com.anil.airreportbe.model.entity.Metar;
import com.anil.airreportbe.model.entity.SkyCondition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
//                orClauseFilters.add(criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi").as(Double.class), 5d));

                orClauseFilters.add(criteriaBuilder.or(
                        criteriaBuilder.and(
                                isNumeric(root, criteriaBuilder),
                                criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi").as(Double.class), 5d)
                        ),
                        criteriaBuilder.and(
                                isString(root, criteriaBuilder),
                                criteriaBuilder.lessThanOrEqualTo(root.get("visibilityStatuteMi").as(Double.class), 5d)
                        )
                ));

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

    private static Predicate isNumeric(Root<Metar> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("visibilityStatuteMi"), "\\d+"),
                criteriaBuilder.like(root.get("visibilityStatuteMi"), "\\d*\\.\\d+")
        );
    }

    private static Predicate isString(Root<Metar> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get("visibilityStatuteMi"), "\\d+");
    }

    public static Specification<Metar> getMetarQueryWithJoinNew(List<String> stations, ZonedDateTime startDateTime, ZonedDateTime endDateTime, Boolean isPirepCondition, Boolean isPirepMissing, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

//            ListJoin<Metar, SkyCondition> skyConditionListJoin = root.joinList("skyConditions", JoinType.LEFT);

            Expression<String> aircraftCode = root.get("aircraft").get("code");
            Expression<ZonedDateTime> observationTime = root.get("observationTime");
            Expression<String> aircraftType = root.get("aircraft").get("type");
            Expression<String> visibilityStatuteMi = root.get("visibilityStatuteMi");
//            Expression<Integer> cloudBaseFtAgl = skyConditionListJoin.get("cloudBaseFtAgl");

            predicates.add(aircraftCode.in(stations));
            predicates.add(criteriaBuilder.between(observationTime, startDateTime, endDateTime));
            predicates.add(criteriaBuilder.equal(aircraftType, type));

            if (isPirepCondition) {
                ListJoin<Metar, SkyCondition> skyConditionListJoin = root.joinList("skyConditions", JoinType.LEFT);
                Expression<Integer> cloudBaseFtAgl = skyConditionListJoin.get("cloudBaseFtAgl");

                Predicate isNumeric = criteriaBuilder.like(visibilityStatuteMi, "\\d*\\.\\d+");
                Predicate isString = criteriaBuilder.like(visibilityStatuteMi, "\\d+");

                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.and(isNumeric, criteriaBuilder.lessThanOrEqualTo(visibilityStatuteMi.as(Double.class), 5d)),
                        criteriaBuilder.and(isString, criteriaBuilder.lessThanOrEqualTo(visibilityStatuteMi.as(Double.class), 5d))
                ));

                predicates.add(criteriaBuilder.isNotNull(root.get("windGustKt")));
                predicates.add(criteriaBuilder.like(root.get("wxString"), "(?<!\\w)(?:TS|TSRA)(?!\\w)"));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(cloudBaseFtAgl, 5000));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

//    private static Expression<Number> castToNumeric(Root<Metar> root, CriteriaBuilder criteriaBuilder) {
//        return criteriaBuilder.cast(root.get("visibilityStatuteMi"), Number.class);
//    }
//
//    private static Expression<Integer> castToInteger(Root<Metar> root, CriteriaBuilder criteriaBuilder) {
//        return criteriaBuilder.cast(root.get("visibilityStatuteMi"), Integer.class);
//    }
}
