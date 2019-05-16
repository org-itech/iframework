package org.itech.iframework.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.itech.iframework.domain.DomainException;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

/**
 * OptionType
 *
 * @author liuqiang
 */
public class OptionType implements DynamicParameterizedType, UserType {
    private Class<?> returnClass;

    @Override
    public void setParameterValues(Properties properties) {
        ParameterType params = (ParameterType) properties.get(PARAMETER_TYPE);

        returnClass = params.getReturnedClass();
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BIGINT};
    }

    @Override
    public Class returnedClass() {
        return returnClass;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException, SQLException {
        Object result = null;

        if (resultSet.getObject(names[0]) != null) {
            Long value = resultSet.getLong(names[0]);

            try {
                result = ReflectionUtils.accessibleConstructor(returnClass, Long.class).newInstance(value);
            } catch (Exception ex) {
                throw new DomainException(ex);
            }
        }

        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BIGINT);
        } else {
            OptionItem optionItem = ((OptionItem) value);

            preparedStatement.setLong(index, optionItem.getValue());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
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
