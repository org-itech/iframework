package org.itech.iframework.domain.data;

import org.springframework.util.Assert;

/**
 * BitEnum
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public interface BitEnum<T extends Enum<T> & BitEnum<T>> {
    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取代码
     *
     * @return 代码
     */
    default String getCode() {
        return ((T) this).name();
    }

    /**
     * 获取值
     *
     * @return 值
     */
    default Long getValue() {
        return (long) (1 << ((T) this).ordinal());
    }


    /**
     * 按位或
     *
     * @param another another
     * @return result
     */
    default Long or(T another) {
        Assert.notNull(another, "参数 another 不能为空！");

        return this.getValue() | another.getValue();
    }
}
