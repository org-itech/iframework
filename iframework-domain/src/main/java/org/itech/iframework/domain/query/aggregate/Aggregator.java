package org.itech.iframework.domain.query.aggregate;

import org.springframework.util.Assert;

/**
 * Aggregator
 *
 * @author liuqiang
 */
public class Aggregator {
    private Aggregates aggregates;
    private GroupBys groupBys;
    private Havings havings;

    private Aggregator(Aggregates aggregates) {
        this.aggregates = aggregates;

        afterPropertySet();
    }

    private Aggregator(Aggregates aggregates, GroupBys groupBys) {
        this(aggregates);
        this.groupBys = groupBys;
    }

    private Aggregator(Aggregates aggregates, GroupBys groupBys, Havings havings) {
        this(aggregates, groupBys);
        this.havings = havings;
    }

    public static Aggregator by(Aggregates aggregates, GroupBys groupBys) {
        return new Aggregator(aggregates, groupBys);
    }

    public static Aggregator by(Aggregates aggregates, GroupBys groupBys, Havings havings) {
        return new Aggregator(aggregates, groupBys, havings);
    }

    public static AggregatorBuilder builder() {
        return new AggregatorBuilder();
    }

    private void afterPropertySet() {
        Assert.isTrue(aggregates.iterator().hasNext(), "聚合 aggregates 不能为空！");
    }

    public Aggregates getAggregates() {
        return aggregates;
    }

    public GroupBys getGroupBys() {
        return groupBys;
    }

    public Havings getHavings() {
        return havings;
    }

    public static class AggregatorBuilder {
        private Aggregates aggregates;
        private GroupBys groupBys;
        private Havings havings;

        public AggregatorBuilder aggregates(Aggregates aggregates) {
            this.aggregates = aggregates;
            return this;
        }

        public AggregatorBuilder groupBys(GroupBys groupBys) {
            this.groupBys = groupBys;
            return this;
        }

        public AggregatorBuilder havings(Havings havings) {
            this.havings = havings;
            return this;
        }

        public Aggregator build() {
            return Aggregator.by(this.aggregates, this.groupBys, this.havings);
        }
    }
}
