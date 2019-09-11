package org.itech.iframework.domain.data;

/**
 * Scope
 *
 * @author liuqiang
 */
public enum Scope implements BitEnum<Scope> {
    /**
     * 不限
     */
    ALL("不限"),

    /**
     * 集团
     */
    GROUP("集团"),

    /**
     * 公司
     */
    COMPANY("公司"),

    /**
     * 部门
     */
    DEPARTMENT("部门"),

    /**
     * 个人
     */
    PERSONAL("个人"),

    /**
     * 无
     */
    NONE("无");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
