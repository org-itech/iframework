package org.itech.iframework.domain.query.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Filter
 *
 * @author liuqiang
 */
public interface Filter {
    /**
     * and 连接筛选器
     *
     * @param filter 筛选器
     * @return 筛选器
     */
    Filter and(Filter filter);

    /**
     * or 连接筛选器
     *
     * @param filter 筛选器
     * @return 筛选器
     */
    Filter or(Filter filter);

    /**
     * 转换成谓词
     *
     * @param root  root
     * @param query criteriaQuery
     * @param cb    criteriaBuilder
     * @return 谓词
     */
    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
