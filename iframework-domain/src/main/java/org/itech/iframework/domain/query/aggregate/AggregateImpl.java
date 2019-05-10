package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * AggregateImpl
 *
 * @author liuqiang
 */
public class AggregateImpl implements Aggregate {
    /**
     * 属性
     */
    private String property;

    /**
     * 聚合函数
     */
    private AggregateFN fn;

    /**
     * 别名
     */
    private String alias;

    private AggregateImpl(String property, AggregateFN fn, String alias) {
        this.property = property;
        this.fn = fn;
        this.alias = alias;
    }

    public static Aggregate by(String property, AggregateFN fn) {
        return new AggregateImpl(property, fn, property);
    }

    public static Aggregate by(String property, AggregateFN fn, String alias) {
        return new AggregateImpl(property, fn, alias);
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public AggregateFN getFn() {
        return fn;
    }

    @Override
    public javax.persistence.criteria.Expression<?> toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        Expression expression = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

        return fn.toExpression(expression, cb);
    }
}