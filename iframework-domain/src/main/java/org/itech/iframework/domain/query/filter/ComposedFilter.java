package org.itech.iframework.domain.query.filter;

import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * ComposedFilter
 *
 * @author liuqiang
 */
public class ComposedFilter extends AbstractFilter {
    /**
     * 连接器
     */
    private final Connector connector;

    /**
     * 左侧筛选器
     */
    private final Filter left;

    /**
     * 右侧筛选器
     */
    private final Filter right;

    public ComposedFilter(Filter left, Filter right, Connector connector) {
        Assert.notNull(left, "左侧筛选器 left 不能为空！");
        Assert.notNull(right, "右侧筛选器 right 不能为空！");
        Assert.notNull(connector, "连接器 connector 不能为空！");

        this.connector = connector;
        this.left = left;
        this.right = right;
    }

    public Connector getConnector() {
        return connector;
    }

    public Filter getLeft() {
        return this.left;
    }

    public Filter getRight() {
        return this.right;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate leftPredicate = getLeft().toPredicate(root, query, cb);
        Predicate rightPredicate = getRight().toPredicate(root, query, cb);

        if (getConnector() == Connector.AND) {
            return cb.and(leftPredicate, rightPredicate);
        } else {
            return cb.or(leftPredicate, rightPredicate);
        }
    }
}