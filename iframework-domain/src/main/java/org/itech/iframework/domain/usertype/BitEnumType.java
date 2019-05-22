package org.itech.iframework.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.itech.iframework.domain.data.BitEnum;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

/**
 * BitEnumType
 *
 * @author liuqiang
 */
public class BitEnumType implements DynamicParameterizedType, UserType {
    private Class<?> enumType;

    @Override
    public void setParameterValues(Properties properties) {
        ParameterType params = (ParameterType) properties.get(PARAMETER_TYPE);
        enumType = params.getReturnedClass();

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
        Object result = null;
        if (resultSet.getObject(names[0]) != null) {
            Long value;

            value = resultSet.getLong(names[0]);

            for (Object obj : returnedClass().getEnumConstants()) {
                if (obj instanceof BitEnum) {
                    if (((BitEnum) obj).getValue().equals(value)) {
                        result = obj;
                        break;
                    }
                }
            }
        }

        return result;
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
