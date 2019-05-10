package org.itech.iframework.domain.query.aggregate;

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
}
