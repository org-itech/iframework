package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * AggregatorImpl
 *
 * @author liuqiang
 */
public class AggregatorImpl implements Aggregator {
    private List<Aggregate> aggregates;
    private List<GroupBy> groupBys;
    private Having having;

    public AggregatorImpl() {
        this.aggregates = new ArrayList<>();
        this.groupBys = new ArrayList<>();
    }

    private AggregatorImpl(List<Aggregate> aggregates, List<GroupBy> groupBys, Having having) {
        this.aggregates = aggregates;
        this.groupBys = groupBys;
        this.having = having;

        Assert.isTrue(aggregates != null && aggregates.iterator().hasNext(), "聚合 aggregates 不能为空！");
    }

    public static Aggregator by(List<Aggregate> aggregates, List<GroupBy> groupBys, Having having) {
        return new AggregatorImpl(aggregates, groupBys, having);
    }

    @Override
    public List<Aggregate> getAggregates() {
        return this.aggregates;
    }

    @Override
    public List<GroupBy> getGroupBys() {
        return this.groupBys;
    }

    @Override
    public Having getHaving() {
        return this.having;
    }

    @Override
    public Iterable<Selection> getSelections() {
        List<Selection> selections = new ArrayList<>();

        selections.addAll(this.getGroupBys());

        selections.addAll(this.getAggregates());

        return selections;
    }
}
