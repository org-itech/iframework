package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;

/**
 * Aggregate
 *
 * @author liuqiang
 */
public interface Aggregate extends Selection {
    /**
     * 获取聚合函数
     *
     * @return 聚合函数
     */
    AggregateFN getFn();
}
