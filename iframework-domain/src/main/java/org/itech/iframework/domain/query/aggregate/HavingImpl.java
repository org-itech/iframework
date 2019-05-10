package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.data.Operator;
import org.itech.iframework.domain.query.filter.NumberFilter;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;

/**
 * HavingImpl
 *
 * @author liuqiang
 */
public class HavingImpl extends NumberFilter implements Having {
    private AggregateFN fn;

    private HavingImpl(String property, Object value, Operator operator, AggregateFN fn) {
        super(property, value, operator);
        this.fn = fn;

        afterPropertySet();
    }

    public static HavingImpl by(String property, Object value, Operator operator, AggregateFN fn) {
        return new HavingImpl(property, value, operator, fn);
    }

    @Override
    public AggregateFN getFn() {
        return fn;
    }

    private void afterPropertySet() {
        Assert.notNull(fn, "聚合函数 fn 不能为空！");
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(getProperty(), root.getJavaType()));

        Expression ex = getFn().toExpression(path, cb);

        return getOperator().toPredicate(root, query, cb, ex, getProperty(), getValue());
    }
}