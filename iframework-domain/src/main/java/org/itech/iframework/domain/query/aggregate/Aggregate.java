package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;

/**
 * Aggregate
 *
 * @param <P>
 * @author liuqiang
 */
public interface Aggregate<P> extends Selection<P> {
    /**
     * 获取聚合函数
     *
     * @return 聚合函数
     */
    AggregateFN getFn();
}
