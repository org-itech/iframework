package org.itech.iframework.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.itech.iframework.domain.data.OptionItem;
import org.itech.iframework.domain.data.OptionSet;
import org.itech.iframework.domain.util.OptionUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.io.Serializable;
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
public class OptionSetType<O extends OptionItem> implements DynamicParameterizedType, UserType {
    private Class<O> optionType;

    @Override
    public void setParameterValues(Properties properties) {
        ParameterType params = (ParameterType) properties.get(PARAMETER_TYPE);

        Class returnedClass = params.getReturnedClass();

        Assert.isAssignable(EnumSet.class, returnedClass);

        JavaXMember member = (JavaXMember) properties.get(XPROPERTY);

        //noinspection unchecked
        optionType = (Class<O>) ResolvableType.forType(member.getJavaType()).getGeneric(0).resolve();
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BIGINT};
    }

    @Override
    public Class returnedClass() {
        return OptionSet.class;
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

            return OptionUtils.getItems(optionType, value);
        } else {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BIGINT);
        } else {
            OptionSet data = (OptionSet) value;

            //noinspection unchecked
            preparedStatement.setLong(index, OptionUtils.getValue(optionType, data));
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
