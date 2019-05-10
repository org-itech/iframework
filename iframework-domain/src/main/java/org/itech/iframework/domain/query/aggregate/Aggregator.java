package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;

/**
 * Aggregator
 *
 * @author liuqiang
 */
public interface Aggregator {
    /**
     * get aggregates
     *
     * @return aggregates
     */
    Iterable<Aggregate> getAggregates();

    /**
     * get group bys
     *
     * @return group bys
     */
    Iterable<GroupBy> getGroupBys();

    /**
     * get having
     *
     * @return having
     */
    Having getHaving();

    /**
     * get selections
     *
     * @return selections
     */
    Iterable<Selection> getSelections();
}
