package org.itech.iframework.domain.util;

import org.itech.iframework.domain.DomainException;
import org.itech.iframework.domain.data.Option;
import org.itech.iframework.domain.data.OptionItem;
import org.itech.iframework.domain.data.OptionSet;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.stream.Collectors;

/**
 * OptionUtils
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public class OptionUtils {
    private static String OPTION_IS_NOT_BITWISE = "选项 {0} 不支持位运算！";
    private static String OPTIONS_NOT_NULL = "参数 options 不能为空！";
    private static String DELIMITER = ",";

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
     * get string value
     *
     * @param clazz   clazz
     * @param options options
     * @param <O>     O
     * @return value
     */
    public static <O extends OptionItem> String join(Class<O> clazz, OptionSet<O> options) {
        Assert.notNull(options, OPTIONS_NOT_NULL);

        return options.stream()
                .map(item -> String.valueOf(item.getValue()))
                .collect(Collectors.joining(DELIMITER, DELIMITER, DELIMITER));
    }

    /**
     * get set
     *
     * @param clazz clazz
     * @param value value
     * @param <O>   O
     * @return set
     */
    public static <O extends OptionItem> OptionSet<O> split(Class<O> clazz, String value) {
        OptionSet result = new OptionSet();

        if (value != null) {
            for (String item : value.split(DELIMITER)) {
                if (StringUtils.hasText(item)) {
                    OptionItem optionItem = instance(clazz);

                    try {
                        Long val = new Long(item);

                        optionItem.setValue(val);
                        result.add(optionItem);
                    } catch (NumberFormatException ex) {
                        throw new DomainException(MessageFormat.format("选项 {0} 值应为数字！", optionItem.getName()));
                    }
                }
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

        int two = 2;
        for (long i = 1; i <= value; i = two * i) {
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
