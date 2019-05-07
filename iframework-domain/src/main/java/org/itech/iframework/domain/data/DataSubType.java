package org.itech.iframework.domain.data;

import java.math.BigDecimal;
import java.util.EnumSet;

/**
 * DataType
 *
 * @author liuqiang
 */
public enum DataSubType implements IEnum<DataSubType> {
    /**
     * 字符串
     * 主要用于短文本
     */
    SHORT_STRING("字符串", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.CT
            , Operator.SW
            , Operator.EW
            , Operator.IN
            , Operator.NOT_IN)
            , String.class),

    /**
     * 长文本
     * 主要用长文本
     */
    LONG_TEXT("长文本", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.CT
            , Operator.SW
            , Operator.EW
            , Operator.IN
            , Operator.NOT_IN)
            , String.class),

    /**
     * 引用
     */
    REFERENCE("引用", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.IN
            , Operator.NOT_IN)
            , String.class),

    /**
     * 层级
     */
    HIERARCHY("层级", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.IN
            , Operator.NOT_IN
            , Operator.DES
            , Operator.ANC
            , Operator.DES_AS
            , Operator.ANC_AS)
            , String.class),

    /**
     * 双态
     */
    DUAL("双态"
            , DataType.BOOLEAN.getSupportedOperators()
            , DataType.BOOLEAN.getJavaType()),

    /**
     * 三态
     */
    TRIPLE("三态", EnumSet.of(Operator.EQ
            , Operator.NEQ)
            , DataType.BOOLEAN.getJavaType()),

    /**
     * SHORT
     * -32768 ~ 32767
     */
    SHORT("SHORT"
            , DataType.INTEGER.getSupportedOperators()
            , Short.class),

    /**
     * INT
     * -2147483648 ~ 2147483647
     */
    INT("INT"
            , DataType.INTEGER.getSupportedOperators()
            , Integer.class),

    /**
     * BIGINT
     * -263 ~ 263-1
     */
    BIGINT("BIGINT", DataType.INTEGER.getSupportedOperators()
            , Long.class),

    /**
     * FLOAT
     */
    FLOAT("FLOAT"
            , DataType.NUMBER.getSupportedOperators()
            , Float.class),

    /**
     * DOUBLE
     */
    DOUBLE("DOUBLE"
            , DataType.NUMBER.getSupportedOperators()
            , Double.class),

    /**
     * BIG_DECIMAL
     */
    BIG_DECIMAL("BIG_DECIMAL", DataType.NUMBER.getSupportedOperators()
            , BigDecimal.class),

    /**
     * 日期
     */
    DATE("日期"
            , DataType.DATE.getSupportedOperators()
            , DataType.DATE.getJavaType()),

    /**
     * 时间
     */
    TIME("时间", DataType.DATE.getSupportedOperators()
            , DataType.DATE.getJavaType()),

    /**
     * 日期时间
     */
    DATETIME("日期时间", DataType.DATE.getSupportedOperators()
            , DataType.DATE.getJavaType());

    private final String name;

    private final EnumSet<Operator> supportedOperators;

    private final Class javaType;

    DataSubType(String name, EnumSet<Operator> supportedOperators, Class javaType) {
        this.name = name;
        this.supportedOperators = supportedOperators;
        this.javaType = javaType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public EnumSet<Operator> getSupportedOperators() {
        return supportedOperators;
    }

    public Class getJavaType() {
        return javaType;
    }
}
