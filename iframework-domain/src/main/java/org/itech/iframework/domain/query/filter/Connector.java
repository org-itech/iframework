package org.itech.iframework.domain.query.filter;


import org.itech.iframework.domain.data.IEnum;

/**
 * 连接器
 *
 * @author liuqiang
 */
public enum Connector implements IEnum<Connector> {
    /**
     * 与
     */
    AND("与"),

    /**
     * 或
     */
    OR("或");

    /**
     * 构造方法
     */
    Connector(String name) {
        this.name = name;
    }

    /**
     * 名称
     */
    private final String name;

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     */
    @Override
    public String getName() {
        return name;
    }
}
