package org.itech.iframework.domain.aggregate;

/**
 * Aggregate
 *
 * @author liuqiang
 */
public class Aggregate {
    /**
     * 属性
     */
    private String property;

    /**
     * 聚合函数
     */
    private AggregateFN fn;

    private Aggregate(String property, AggregateFN fn) {
        this.property = property;
        this.fn = fn;
    }

    public static Aggregate by(String property, AggregateFN fn) {
        return new Aggregate(property, fn);
    }
}
