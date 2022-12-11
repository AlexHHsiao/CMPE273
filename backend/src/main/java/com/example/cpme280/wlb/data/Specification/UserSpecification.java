package com.example.cpme273.wlb.data.Specification;

import com.example.cpme273.wlb.dto.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
public class UserSpecification implements Specification {

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
          if ("role".equals(key)) {
            predicate
                .getExpressions()
                .add(criteriaBuilder.equal(root.get(key), Role.valueOf((String) value)));
          } else {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(key), value));
          }
        });
    return predicate;
  }
}
