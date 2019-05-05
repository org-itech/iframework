package org.itech.iframework.domain.aggregate;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Aggregator
 *
 * @author liuqiang
 */
public class Aggregator {
    private List<Aggregate> aggregates;
    private List<String> groupBys;
    private List<Having> havings;

    private Aggregator(List<Aggregate> aggregates) {
        this.aggregates = aggregates;

        afterPropertySet();
    }

    private Aggregator(List<Aggregate> aggregates, List<String> groupBys) {
        this(aggregates);
        this.groupBys = groupBys;
    }

    private Aggregator(List<Aggregate> aggregates, List<String> groupBys, List<Having> havings) {
        this(aggregates, groupBys);
        this.havings = havings;
    }

    public static Aggregator by(List<Aggregate> aggregates, List<String> groupBys) {
        return new Aggregator(aggregates, groupBys);
    }

    public static Aggregator by(List<Aggregate> aggregates, List<String> groupBys, List<Having> havings) {
        return new Aggregator(aggregates, groupBys, havings);
    }

    public static Aggregator by(List<Aggregate> aggregates, String... groupBys) {
        return new Aggregator(aggregates, Arrays.asList(groupBys));
    }

    public static Aggregator by(Aggregate... aggregates) {
        return new Aggregator(Arrays.asList(aggregates), null);
    }

    private void afterPropertySet() {
        Assert.notEmpty(aggregates, "聚合 aggregates 不能为空！");
    }

    public List<Aggregate> getAggregates() {
        return aggregates;
    }

    public List<String> getGroupBys() {
        return groupBys;
    }

    public List<Having> getHavings() {
        return havings;
    }
}
