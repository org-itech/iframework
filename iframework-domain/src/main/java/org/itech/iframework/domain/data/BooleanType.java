package org.itech.iframework.domain.data;

/**
 * BooleanType
 *
 * @author liuqiang
 */
public enum BooleanType implements BitEnum<BooleanType> {
    /**
     * 双态
     */
    DUAL("双态"),

    /**
     * 三态
     */
    TRIPLE("三态");

    private final String name;

    BooleanType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
