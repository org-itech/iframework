package org.itech.iframework.domain.query.aggregate;

/**
 * GroupByImpl
 *
 * @author liuqiang
 */
public class GroupByImpl implements GroupBy {
    /**
     * 属性
     */
    private String property;

    /**
     * 别名
     */
    private String alias;

    private GroupByImpl(String property, String alias) {
        this.property = property;
        this.alias = alias;
    }

    public static GroupBy by(String property) {
        return new GroupByImpl(property, property);
    }

    public static GroupBy by(String property, String alias) {
        return new GroupByImpl(property, alias);
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
