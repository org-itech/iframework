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
    private Having havings;

    private Aggregator(Aggregates aggregates) {
        this.aggregates = aggregates;

        afterPropertySet();
    }

    private Aggregator(Aggregates aggregates, GroupBys groupBys) {
        this(aggregates);
        this.groupBys = groupBys;
    }

    private Aggregator(Aggregates aggregates, GroupBys groupBys, Having havings) {
        this(aggregates, groupBys);
        this.havings = havings;
    }

    public static Aggregator by(Aggregates aggregates, GroupBys groupBys) {
        return new Aggregator(aggregates, groupBys);
    }

    public static Aggregator by(Aggregates aggregates, GroupBys groupBys, Having havings) {
        return new Aggregator(aggregates, groupBys, havings);
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

    public Having getHavings() {
        return havings;
    }
}
