package org.itech.iframework.domain.aggregate;

/**
 * Having
 *
 * @author liuqiang
 */
public class Having {
    private String property;
    private AggregateFN fn;
    private Object value;

    private Having(String property, AggregateFN fn, Object value) {
        this.property = property;
        this.fn = fn;
        this.value = value;
    }
}
