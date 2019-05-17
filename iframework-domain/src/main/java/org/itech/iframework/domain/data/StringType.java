package org.itech.iframework.domain.data;

/**
 * StringType
 *
 * @author liuqiang
 */
public enum StringType implements BitEnum<StringType> {
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
    HIERARCHY("层级");

    /**
     * 构造方法
     */
    StringType(String name) {
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
