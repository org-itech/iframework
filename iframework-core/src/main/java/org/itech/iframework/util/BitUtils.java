package org.itech.iframework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * BitUtils
 *
 * @author liuqiang
 */
public final class BitUtils {
    private final static int DOUBLE = 2;

    /**
     * 按位或求值
     *
     * @param set 集合
     * @return 结果
     */
    public static Long valueOf(Set<Long> set) {
        Long result = 0L;

        for (Long item : set) {
            result = result | item;
        }

        return result;
    }

    /**
     * 按位与求值
     *
     * @param value 值
     * @return 结果
     */
    public static List<Long> setOf(Long value) {
        List<Long> set = new ArrayList<>();

        for (long i = 1; i <= value; i = DOUBLE * i) {
            if (i == (i & value)) {
                set.add(i);
            }
        }

        return set;
    }

    /**
     * 是否是2的n次幂
     *
     * @param value 是否
     * @return 是否
     */
    public static boolean isPowerOfTwo(Long value) {
        return (value > 0) && (value & (value - 1)) == 0;
    }
}
