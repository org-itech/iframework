package org.itech.iframework.domain.data;

import org.itech.iframework.domain.model.AbstractAggregateRoot;
import org.itech.iframework.domain.model.AbstractEntity;

import java.math.BigDecimal;
import java.util.EnumSet;

/**
 * DataSubType
 *
 * @author liuqiang
 */
public enum DataSubType implements BitEnum<DataSubType> {
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
            , DataType.STRING
            , DataType.STRING.getJavaType()),

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
            , DataType.STRING
            , DataType.STRING.getJavaType()),

    /**
     * 引用
     */
    REFERENCE("引用", EnumSet.of(Operator.EQ
            , Operator.NEQ
            , Operator.IN
            , Operator.NOT_IN)
            , DataType.STRING
            , DataType.STRING.getJavaType()),

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
            , DataType.STRING
            , DataType.STRING.getJavaType()),

    /**
     * 双态
     */
    DUAL("双态"
            , DataType.BOOLEAN.getSupportedOperators()
            , DataType.BOOLEAN
            , DataType.BOOLEAN.getJavaType()),

    /**
     * 三态
     */
    TRIPLE("三态", EnumSet.of(Operator.EQ
            , Operator.NEQ)
            , DataType.BOOLEAN
            , DataType.BOOLEAN.getJavaType()),

    /**
     * SHORT
     * -32768 ~ 32767
     */
    SHORT("SHORT"
            , DataType.INTEGER.getSupportedOperators()
            , DataType.INTEGER
            , Short.class),

    /**
     * INT
     * -2147483648 ~ 2147483647
     */
    INT("INT"
            , DataType.INTEGER.getSupportedOperators()
            , DataType.INTEGER
            , Integer.class),

    /**
     * BIGINT
     * -263 ~ 263-1
     */
    BIGINT("BIGINT"
            , DataType.INTEGER.getSupportedOperators()
            , DataType.INTEGER
            , Long.class),

    /**
     * FLOAT
     */
    FLOAT("FLOAT"
            , DataType.NUMBER.getSupportedOperators()
            , DataType.NUMBER
            , Float.class),

    /**
     * DOUBLE
     */
    DOUBLE("DOUBLE"
            , DataType.NUMBER.getSupportedOperators()
            , DataType.NUMBER
            , Double.class),

    /**
     * BIG_DECIMAL
     */
    BIG_DECIMAL("BIG_DECIMAL", DataType.NUMBER.getSupportedOperators()
            , DataType.NUMBER
            , BigDecimal.class),

    /**
     * 日期
     */
    DATE("日期"
            , DataType.DATE.getSupportedOperators()
            , DataType.DATE
            , DataType.DATE.getJavaType()),

    /**
     * 时间
     */
    TIME("时间", DataType.DATE.getSupportedOperators()
            , DataType.DATE
            , DataType.DATE.getJavaType()),

    /**
     * 日期时间
     */
    DATETIME("日期时间", DataType.DATE.getSupportedOperators()
            , DataType.DATE
            , DataType.DATE.getJavaType()),

    /**
     * 聚合根
     */
    AGGREGATE_ROOT("聚合根", DataType.OBJECT.getSupportedOperators()
            , DataType.OBJECT
            , AbstractAggregateRoot.class),

    /**
     * 实体
     */
    ENTITY("实体", DataType.OBJECT.getSupportedOperators()
            , DataType.OBJECT
            , AbstractEntity.class),

    /**
     * 值对象
     */
    VALUE_OBJECT("值对象", DataType.OBJECT.getSupportedOperators()
            , DataType.OBJECT
            , Object.class);

    private final String name;

    private final DataType dataType;

    private final EnumSet<Operator> supportedOperators;

    private final Class javaType;

    DataSubType(String name, EnumSet<Operator> supportedOperators, DataType dataType, Class javaType) {
        this.name = name;
        this.dataType = dataType;
        this.supportedOperators = supportedOperators;
        this.javaType = javaType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public EnumSet<Operator> getSupportedOperators() {
        return supportedOperators;
    }

    public Class getJavaType() {
        return javaType;
    }
}
