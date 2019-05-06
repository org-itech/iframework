package org.itech.iframework.domain.data;

/**
 * DataType
 *
 * @author liuqiang
 */
public enum DataSubType implements IEnum<DataSubType> {
    /**
     * 字符串
     * 主要用于短文本
     */
    STRING("字符串"),

    /**
     * 长文本
     * 主要用长文本
     */
    TEXT("长文本"),

    /**
     * 引用
     */
    REFERENCE("引用"),

    /**
     * 层级
     */
    HIERARCHY("层级"),

    /**
     * 双态
     */
    DUAL("双态"),

    /**
     * 三态
     */
    TRIPLE("三态"),

    /**
     * SHORT
     * -32768 ~ 32767
     */
    SHORT("SHORT"),

    /**
     * INT
     * -2147483648 ~ 2147483647
     */
    INT("INT"),

    /**
     * BIGINT
     * -263 ~ 263-1
     */
    BIGINT("BIGINT"),

    /**
     * FLOAT
     */
    FLOAT("FLOAT"),

    /**
     * DOUBLE
     */
    DOUBLE("DOUBLE"),

    /**
     * BIG_DECIMAL
     */
    BIG_DECIMAL("BIG_DECIMAL"),

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

    private final String name;

    DataSubType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
