package org.itech.iframework.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.itech.iframework.domain.data.BitEnum;
import org.itech.iframework.domain.util.BitEnumUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Properties;

/**
 * BitEnumSetType
 *
 * @author liuqiang
 */
public class BitEnumSetType<E extends Enum<E> & BitEnum<E>> implements DynamicParameterizedType, UserType {
    private Class<E> enumType;

    @Override
    public void setParameterValues(Properties properties) {
        ParameterType params = (ParameterType) properties.get(PARAMETER_TYPE);

        Class returnedClass = params.getReturnedClass();

        Assert.isAssignable(EnumSet.class, returnedClass);

        JavaXMember member = (JavaXMember) properties.get(XPROPERTY);

        //noinspection unchecked
        enumType = (Class<E>) ((ParameterizedType) member.getJavaType()).getActualTypeArguments()[0];

        Assert.isAssignable(BitEnum.class, enumType);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BIGINT};
    }

    @Override
    public Class returnedClass() {
        return enumType;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        if (resultSet.getObject(names[0]) != null) {
            long value = resultSet.getLong(names[0]);

            return BitEnumUtils.getEnumSet(enumType, value);
        } else {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BIGINT);
        } else {
            preparedStatement.setLong(index, ((BitEnum) value).getValue());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
