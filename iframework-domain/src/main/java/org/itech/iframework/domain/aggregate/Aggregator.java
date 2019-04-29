package org.itech.iframework.domain.aggregate;

import org.springframework.util.Assert;

import java.util.ArrayList;
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
    }

    private Aggregator(List<Aggregate> aggregates, List<String> groupBys) {
        this(aggregates);
        this.groupBys = groupBys;
        this.havings = havings;
    }

    private Aggregator(List<Aggregate> aggregates, List<String> groupBys, List<Having> havings) {
        this(aggregates, groupBys);
        this.havings = havings;
    }

    public static Aggregator by(List<Aggregate> aggregates, List<String> groupBys) {
        Assert.notEmpty(aggregates, "聚合 aggregates 不能为空！");

        return new Aggregator(aggregates, groupBys);
    }

    public static Aggregator by(List<Aggregate> aggregates, String... groupBys) {
        Assert.notEmpty(aggregates, "聚合 aggregates 不能为空！");

        return new Aggregator(aggregates, Arrays.asList(groupBys));
    }

    public static Aggregator by(Aggregate... aggregates) {
        Assert.notEmpty(aggregates, "聚合 aggregates 不能为空！");

        return new Aggregator(Arrays.asList(aggregates), null);
    }

    public static Aggregator by(AggregateFN fn, String... properties) {
        Assert.notEmpty(properties, "属性 properties 不能为空！");

        List<Aggregate> aggregates = new ArrayList<>();

        for (String p : properties) {
            aggregates.add(Aggregate.by(p, fn));
        }

        return new Aggregator(aggregates, null);
    }
}
