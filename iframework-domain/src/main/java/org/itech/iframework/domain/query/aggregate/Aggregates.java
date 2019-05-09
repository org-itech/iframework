package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aggregates
 *
 * @author liuqiang
 */
public class Aggregates implements Iterable<Aggregates.Aggregate> {
    private List<Aggregate> aggregates;

    private Aggregates(List<Aggregate> aggregates) {
        this.aggregates = aggregates;
    }

    @Override
    public Iterator<Aggregate> iterator() {
        return aggregates.iterator();
    }

    public List<javax.persistence.criteria.Selection<?>> toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        return aggregates.stream()
                .map(item -> item.toJpaSelection(root, query, cb))
                .collect(Collectors.toList());
    }

    public static AggregatesBuilder builder() {
        return new AggregatesBuilder();
    }

    public static class AggregatesBuilder {
        private List<Aggregate> aggregates;

        AggregatesBuilder() {
            this.aggregates = new ArrayList<>();
        }

        public AggregatesBuilder add(String property, AggregateFN fn, String alias) {
            this.aggregates.add(Aggregate.by(property, fn, alias));

            return this;
        }

        public AggregatesBuilder add(String property, AggregateFN fn) {
            this.aggregates.add(Aggregate.by(property, fn, property));

            return this;
        }

        public Aggregates build() {
            return new Aggregates(this.aggregates);
        }
    }

    /**
     * Aggregate
     *
     * @author liuqiang
     */
    public static class Aggregate implements Selection {
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

        private Aggregate(String property, AggregateFN fn, String alias) {
            this.property = property;
            this.fn = fn;
            this.alias = alias;
        }

        public static Aggregate by(String property, AggregateFN fn) {
            return new Aggregate(property, fn, property);
        }

        public static Aggregate by(String property, AggregateFN fn, String alias) {
            return new Aggregate(property, fn, alias);
        }

        @Override
        public String getProperty() {
            return property;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        public AggregateFN getFn() {
            return fn;
        }

        @Override
        public javax.persistence.criteria.Selection<?> toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
            Expression expression = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return fn.toExpression(expression, cb);
        }
    }
}
