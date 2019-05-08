package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.data.Operator;
import org.itech.iframework.domain.query.filter.NumberFilter;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;

/**
 * Havings
 *
 * @author liuqiang
 */
public class Havings extends NumberFilter {
    private AggregateFN fn;

    private Havings(String property, Object value, Operator operator, AggregateFN fn) {
        super(property, value, operator);
        this.fn = fn;

        afterPropertySet();
    }

    public static Havings by(String property, Object value, Operator operator, AggregateFN fn) {
        return new Havings(property, value, operator, fn);
    }

    public static HavingBuidler buidler() {
        return new HavingBuidler();
    }

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

    public static class HavingBuidler {
        private Havings havings;

        public HavingBuidler and(String property, Object value, Operator operator, AggregateFN fn) {
            if (havings == null) {
                havings = Havings.by(property, value, operator, fn);
            } else {
                havings = (Havings) havings.and(Havings.by(property, value, operator, fn));
            }

            return this;
        }

        public HavingBuidler or(String property, Object value, Operator operator, AggregateFN fn) {
            if (havings == null) {
                havings = Havings.by(property, value, operator, fn);
            } else {
                havings = (Havings) havings.or(Havings.by(property, value, operator, fn));
            }

            return this;
        }
    }
}