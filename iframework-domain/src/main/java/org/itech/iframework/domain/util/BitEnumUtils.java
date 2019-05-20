package org.itech.iframework.domain.util;

import org.itech.iframework.domain.data.BitEnum;
import org.springframework.util.Assert;

import java.util.EnumSet;

/**
 * BitEnumUtils
 *
 * @author liuqiang
 */
public class BitEnumUtils {
    private static String ENUMS_NOT_NULL = "参数 enums 不能为空！";
    private static String BIT_ENUM_SIZE_SHOULD_LESS_THEN_64 = "BitEnum枚举的大小应小于等于64！";
    private static String VALUE_SHOULD_GREATE_THEN_ONE = "参数 value 的值应大于等于！";
    private static int DOUBLE = 2;

    /**
     * 按位与
     *
     * @param clazz transactionId
     * @param enums enums
     * @param <E>   E
     * @return 按位与
     */
    public static <E extends Enum<E> & BitEnum<E>> long or(Class<E> clazz, Iterable<E> enums) {
        Assert.notNull(enums, ENUMS_NOT_NULL);
        Assert.isTrue(isBitwise(clazz), BIT_ENUM_SIZE_SHOULD_LESS_THEN_64);

        long result = 0;

        for (E item : enums) {
            if (result == 0) {
                result = item.getValue();
            } else {
                result |= item.getValue();
            }
        }

        return result;
    }

    /**
     * parse enum
     *
     * @param clazz clazz
     * @param value value
     * @param <E>   e
     * @return enum
     */
    public static <E extends Enum<E> & BitEnum<E>> E getEnum(Class<E> clazz, long value) {
        Assert.isTrue(value >= 1, VALUE_SHOULD_GREATE_THEN_ONE);
        Assert.isTrue(isBitwise(clazz), BIT_ENUM_SIZE_SHOULD_LESS_THEN_64);

        for (E item : clazz.getEnumConstants()) {
            if (item.getValue() == value) {
                return item;
            }
        }

        return null;
    }

    /**
     * parse enum
     *
     * @param clazz clazz
     * @param value value
     * @param <E>   e
     * @return enum
     */
    public static <E extends Enum<E> & BitEnum<E>> EnumSet<E> getEnums(Class<E> clazz, long value) {
        Assert.isTrue(value >= 1, VALUE_SHOULD_GREATE_THEN_ONE);
        Assert.isTrue(isBitwise(clazz), BIT_ENUM_SIZE_SHOULD_LESS_THEN_64);

        EnumSet<E> result = EnumSet.noneOf(clazz);

        for (long i = 1; i <= value; i = DOUBLE * i) {
            if (i == (i & value)) {
                for (E obj : clazz.getEnumConstants()) {
                    if (i == obj.getValue()) {
                        result.add(obj);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 是否支持位运算
     *
     * @param clazz clazz
     * @param <E>   e
     * @return 是否支持位运算
     */
    public static <E extends Enum<E> & BitEnum<E>> boolean isBitwise(Class<E> clazz) {
        int enumSize = clazz.getEnumConstants().length;

        return enumSize <= Long.SIZE;
    }
}
