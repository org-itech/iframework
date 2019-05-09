package org.itech.iframework.domain.query;

import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Selection
 *
 * @param <P>
 * @author liuqiang
 */
public interface Selection<P> {
    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    String getProperty();

    /**
     * 获取别名
     *
     * @return 别名
     */
    String getAlias();

    /**
     * to jpa selection
     *
     * @param root  root
     * @param query criteriaQuery
     * @param cb    criteriaBuilder
     * @return jpa selection
     */
    default javax.persistence.criteria.Selection<?> toJpaSelection(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return QueryUtils.toExpressionRecursively(root, PropertyPath.from(getProperty(), root.getJavaType()));
    }
}
