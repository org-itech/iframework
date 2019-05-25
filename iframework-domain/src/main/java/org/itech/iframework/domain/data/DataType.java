package org.itech.iframework.domain.data;

import java.util.Date;
import java.util.EnumSet;

/**
 * DataType
 *
 * @author liuqiang
 */
public enum DataType implements BitEnum<DataType> {
    /**
     * 字符串
     */
    STRING("字符串", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.CT
            , Operator.SW
            , Operator.EW
            , Operator.IN
            , Operator.NOT_IN
            , Operator.DES
            , Operator.ANC
            , Operator.DES_AS
            , Operator.ANC_AS)
            , String.class),

    /**
     * 布尔
     */
    BOOLEAN("布尔", EnumSet.of(Operator.EQ
            , Operator.NEQ)
            , Boolean.class),

    /**
     * 整数
     */
    INTEGER("整数", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.GT
            , Operator.GTE
            , Operator.LT
            , Operator.LTE
            , Operator.BTW
            , Operator.IN
            , Operator.NOT_IN
            , Operator.BIT_IN
            , Operator.BIT_CT)
            , Integer.class),

    /**
     * 数字
     */
    NUMBER("数字", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.GT
            , Operator.GTE
            , Operator.LT
            , Operator.LTE
            , Operator.BTW
            , Operator.IN
            , Operator.NOT_IN)
            , Number.class),

    /**
     * 日期和时间
     * ISO8601
     */
    DATE("日期和时间", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.GT
            , Operator.GTE
            , Operator.LT
            , Operator.LTE
            , Operator.BTW
            , Operator.IN)
            , Date.class),

    /**
     * JSON
     */
    JSON("JSON", EnumSet.noneOf(Operator.class), Object.class),

    /**
     * 对象
     */
    OBJECT("对象", EnumSet.noneOf(Operator.class), Object.class);

    private final String name;

    private final EnumSet<Operator> supportedOperators;

    private final Class javaType;

    DataType(String name, EnumSet<Operator> supportedOperators, Class javaType) {
        this.name = name;
        this.supportedOperators = supportedOperators;
        this.javaType = javaType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 获取支持的操作符
     *
     * @return 操作符集合
     */
    public EnumSet<Operator> getSupportedOperators() {
        return this.supportedOperators;
    }

    public Class getJavaType() {
        return this.javaType;
    }

    /**
     * 是否是支持的操作符
     *
     * @param operator 操作符
     * @return 是否支持
     */
    public boolean isSupportedOperator(Operator operator) {
        return this.supportedOperators.contains(operator);
    }
}
