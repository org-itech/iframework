package org.itech.iframework.domain.util;

import org.itech.iframework.domain.data.Option;
import org.itech.iframework.domain.data.OptionItem;
import org.itech.iframework.domain.data.OptionSet;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.text.MessageFormat;

/**
 * OptionUtils
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public class OptionUtils {
    private static String OPTION_IS_NOT_BITWISE = "选项 {0} 不支持位运算！";
    private static String OPTIONS_NOT_NULL = "参数 options 不能为空！";
    private static int DOUBLE = 2;

    /**
     * get value
     *
     * @param clazz   clazz
     * @param options options
     * @param <O>     O
     * @return value
     */
    public static <O extends OptionItem> long getValue(Class<O> clazz, OptionSet<O> options) {
        Assert.notNull(options, OPTIONS_NOT_NULL);

        long result = 0;

        Option option = getOption(clazz);
        Assert.isTrue(option.isBitwise(), MessageFormat.format(OPTION_IS_NOT_BITWISE, option.getName()));

        for (O item : options) {
            if (result == 0) {
                result = item.getValue();
            } else {
                result |= item.getValue();
            }
        }

        return result;
    }

    /**
     * get set
     *
     * @param clazz clazz
     * @param value value
     * @param <O>   O
     * @return set
     */
    public static <O extends OptionItem> OptionSet<O> getSet(Class<O> clazz, long value) {
        Option option = getOption(clazz);
        Assert.isTrue(option.isBitwise(), MessageFormat.format(OPTION_IS_NOT_BITWISE, option.getName()));

        OptionSet<O> result = new OptionSet();

        for (long i = 1; i <= value; i = DOUBLE * i) {
            if (i == (i & value)) {
                O item = instance(clazz);
                item.setValue(i);

                result.add(item);
            }
        }

        return result;
    }

    /**
     * is bitwise
     *
     * @param clazz clazz
     * @param <O>   O
     * @return is bitwise
     */
    public static <O extends OptionItem> boolean isBitwise(Class<O> clazz) {
        return getOption(clazz).isBitwise();
    }

    /**
     * instance
     *
     * @param clazz clazz
     * @param <O>   O
     * @return instance
     */
    public static <O extends OptionItem> O instance(Class<O> clazz) {
        return BeanUtils.instantiateClass(clazz);
    }

    private static <O extends Option, I extends OptionItem<O>> O getOption(Class<I> clazz) {
        return instance(clazz).getOption();
    }
}
