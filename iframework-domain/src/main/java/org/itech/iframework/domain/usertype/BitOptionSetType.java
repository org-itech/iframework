package org.itech.iframework.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.itech.iframework.domain.data.OptionItem;
import org.itech.iframework.domain.data.OptionSet;
import org.itech.iframework.domain.util.OptionUtils;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * BitEnumSetType
 *
 * @author liuqiang
 */
public class BitOptionSetType<O extends OptionItem> extends OptionSetType<O> {
    @Override
    public void setParameterValues(Properties properties) {
        super.setParameterValues(properties);

        O instance = OptionUtils.instance(getOptionType());

        Assert.isTrue(instance.isBitwise(), MessageFormat.format("选项 {0} 不支持位运算！", instance.getOptionName()));
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BIGINT};
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        Object result = null;

        if (resultSet.getObject(names[0]) != null) {
            long value = resultSet.getLong(names[0]);

            result = OptionUtils.getSet(getOptionType(), value);
        }

        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BIGINT);
        } else {
            OptionSet data = (OptionSet) value;

            //noinspection unchecked
            preparedStatement.setLong(index, OptionUtils.getValue(getOptionType(), data));
        }
    }
}
