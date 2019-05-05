package org.itech.iframework.domain.data;

/**
 * IntegerType
 *
 * @author liuqiang
 */
public enum IntegerType implements IEnum<IntegerType> {
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
    BIGINT("BIGINT");

    private final String name;

    IntegerType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
