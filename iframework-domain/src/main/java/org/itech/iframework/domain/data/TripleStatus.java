package org.itech.iframework.domain.data;

/**
 * TripleStatus
 *
 * @author liuqiang
 */
public enum TripleStatus implements BitEnum<DualStatus> {
    /**
     * 新建
     */
    NEW("新建"),

    /**
     * 有效
     */
    ACTIVE("有效"),

    /**
     * 无效
     */
    INACTIVE("无效");

    private final String name;

    TripleStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
