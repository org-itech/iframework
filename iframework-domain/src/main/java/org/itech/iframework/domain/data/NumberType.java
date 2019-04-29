package org.itech.iframework.domain.data;

/**
 * NumberType
 *
 * @author liuqiang
 */
public enum NumberType implements IEnum<NumberType> {
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

    /**     * BIG_DECIMAL
     */
    BIG_DECIMAL("BIG_DECIMAL");

    private final String name;

    NumberType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
