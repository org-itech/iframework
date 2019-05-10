package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.filter.Filter;

/**
 * Having
 *
 * @author liuqiang
 */
public interface Having extends Filter {
    /**
     * 获取聚合函数
     *
     * @return 聚合函数
     */
    AggregateFN getFn();
}
