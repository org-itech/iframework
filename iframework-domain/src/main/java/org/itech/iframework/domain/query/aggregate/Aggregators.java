package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.data.Operator;

/**
 * Aggregators
 *
 * @author liuqiang
 */
public class Aggregators {
    public static AggregatorBuilder builder() {
        return new AggregatorBuilder();
    }

    public static class AggregatorBuilder {
        private AggregatorImpl aggregator;

        private AggregatorBuilder() {
            this.aggregator = new AggregatorImpl();
        }

        public AggregatorBuilder aggregate(Aggregate aggregate) {
            this.aggregator.getAggregates().add(aggregate);

            return this;
        }

        public AggregatorBuilder aggregate(String property, AggregateFN fn, String alias) {
            this.aggregator.getAggregates().add(AggregateImpl.by(property, fn, alias));

            return this;
        }

        public AggregatorBuilder aggregate(String property, AggregateFN fn) {
            this.aggregator.getAggregates().add(AggregateImpl.by(property, fn));

            return this;
        }

        public AggregatorBuilder groupBy(GroupBy groupBy) {
            this.aggregator.getGroupBys().add(groupBy);

            return this;
        }

        public AggregatorBuilder groupBy(String property) {
            this.aggregator.getGroupBys().add(GroupByImpl.by(property));

            return this;
        }

        public AggregatorBuilder groupBy(String property, String alias) {
            this.aggregator.getGroupBys().add(GroupByImpl.by(property, alias));

            return this;
        }

        public AggregatorBuilder having(Having having) {
            this.aggregator.getHaving().and(having);

            return this;
        }

        public AggregatorBuilder having(String property, Object value, Operator operator, AggregateFN fn) {
            this.aggregator.getHaving().and(HavingImpl.by(property, value, operator, fn));

            return this;
        }
    }
}
