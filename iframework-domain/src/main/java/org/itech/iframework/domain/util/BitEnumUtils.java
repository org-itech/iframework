package org.itech.iframework.domain.util;

import org.itech.iframework.domain.data.BitEnum;
import org.springframework.util.Assert;

/**
 * BitEnumUtils
 *
 * @author liuqiang
 */
public class BitEnumUtils {
    /**
     * 按位或
     *
     * @param enums
     * @param <E>   enum type
     * @return 结果
     */
    public static <E extends Enum<E> & BitEnum<E>> long or(E... enums) {
        Assert.notEmpty(enums, "参数 enums 不能为空！");

        Long result = 0L;


        return result;
    }
}
