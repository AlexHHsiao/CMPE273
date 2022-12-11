package com.example.cpme273.wlb.data.Specification;

import com.example.cpme273.wlb.dto.HomeType;
import com.example.cpme273.wlb.dto.OfferType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class HomeSpecification implements Specification {

  private Map<String, Object> whereClauseFields;

  @Override
  public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {

    final Collection<Predicate> predicates = new ArrayList<>();

    if (!CollectionUtils.isEmpty(whereClauseFields)) {
      predicates.add(buildConjunctionPredicate(whereClauseFields, root, criteriaBuilder));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
  }
  /**
   * Build AND Conjunction predicate
   *
   * @param whereClauseFields
   * @param root
   * @param criteriaBuilder
   * @return
   */
  public Predicate buildConjunctionPredicate(
      Map<String, Object> whereClauseFields, Root<?> root, CriteriaBuilder criteriaBuilder) {

    Predicate predicate = criteriaBuilder.conjunction();

    whereClauseFields.forEach(
        (key, value) -> {
          if ("minPrice".equals(key)) {
            predicate
                .getExpressions()
                .add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), (Double) value));
          } else if ("maxPrice".equals(key)) {
            predicate
                .getExpressions()
                .add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), (Double) value));
          } else if ("homeType".equals(key)) {
            predicate
                .getExpressions()
                .add(criteriaBuilder.equal(root.get(key), HomeType.valueOf((String) value)));
          } else if ("offerType".equals(key)) {
              predicate
                 .getExpressions()
                 .add(criteriaBuilder.equal(root.get(key), OfferType.valueOf((String) value)));
          } else if ("ownerId".equals(key)) {
              String ownerIdValue = (String) value;
              if(ownerIdValue.startsWith("-")) {
                  predicate
                      .getExpressions()
                      .add(criteriaBuilder.notEqual(root.get(key), UUID.fromString(ownerIdValue.substring(1))));
              } else{
                  predicate
                      .getExpressions()
                      .add(criteriaBuilder.equal(root.get(key), UUID.fromString(ownerIdValue)));
              }
          } else {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(key), value));
          }
        });
    return predicate;
  }
}

