package org.itech.iframework.domain.data;

/**
 * DualStatus
 *
 * @author liuqiang
 */
public enum DualStatus implements IEnum<DualStatus> {
    /**
     * 有效
     */
    ACTIVE("有效"),

    /**
     * 无效
     */
    INACTIVE("无效");

    private final String name;

    DualStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
