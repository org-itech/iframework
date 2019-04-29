package org.itech.iframework.domain.data;

import java.util.EnumSet;

/**
 * DataType
 *
 * @author liuqiang
 */
public enum DataSubType implements IEnum<DataSubType> {
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
            , Operator.ANC_AS)),

    /**
     * 布尔
     */
    BOOLEAN("布尔", EnumSet.of(Operator.EQ
            , Operator.NEQ)),

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
            , Operator.CT
            , Operator.IN
            , Operator.NOT_IN
            , Operator.BIT_IN
            , Operator.BIT_CT)),

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
            , Operator.NOT_IN)),

    /**
     * 日期
     * ISO8601
     */
    DATE("日期", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.GT
            , Operator.GTE
            , Operator.LT
            , Operator.LTE
            , Operator.BTW
            , Operator.IN)),

    /**
     * 对象
     */
    Object("对象", EnumSet.noneOf(Operator.class));

    private final String name;

    private final EnumSet<Operator> supportedOperators;

    DataSubType(String name, EnumSet<Operator> supportedOperators) {
        this.name = name;
        this.supportedOperators = supportedOperators;
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
