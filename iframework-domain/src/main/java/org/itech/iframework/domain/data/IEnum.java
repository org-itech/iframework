package org.itech.iframework.domain.data;

/**
 * IEnum
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public interface IEnum<T extends Enum<T>> {
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
        return (long) (((T) this).ordinal() << 1);
    }
}
