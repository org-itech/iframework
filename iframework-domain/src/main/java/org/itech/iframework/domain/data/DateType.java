package org.itech.iframework.domain.data;

/**
 * DateType
 *
 * @author liuqiang
 */
public enum DateType implements IEnum<DateType> {
    /**
     * 日期
     */
    DATE("日期"),

    /**
     * 时间
     */
    TIME("时间"),

    /**
     * 日期时间
     */
    DATETIME("日期时间");

    /**
     * 构造方法
     */
    DateType(String name) {
        this.name = name;
    }

    /**
     * 名称
     */
    private final String name;

    /**
     * 获取枚举文本
     *
     * @return 枚举文本
     */
    @Override
    public String getName() {
        return name;
    }
}
