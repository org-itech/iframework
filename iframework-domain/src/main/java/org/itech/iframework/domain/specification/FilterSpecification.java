package org.itech.iframework.domain.specification;

import org.itech.iframework.domain.query.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * FilterSpecification
 *
 * @param <T>
 * @author liuqiang
 */
public class FilterSpecification<T> implements Specification<T> {
    private Filter filter;

    public FilterSpecification(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return filter.toPredicate(root, query, criteriaBuilder);
    }
}
