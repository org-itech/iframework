package org.itech.iframework.domain.dialect;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StandardBasicTypes;

import static org.itech.iframework.domain.constant.DomainConstants.*;

/**
 * MySql8Dialect
 *
 * @author liuqiang
 */
public class MySql8Dialect extends org.hibernate.dialect.MySQL8Dialect {
    @SuppressWarnings("all")
    public MySql8Dialect() {
        super();
        registerFunction(BIT_AND_FUNCTION_NAME, new SQLFunctionTemplate(IntegerType.INSTANCE, "(?1 & ?2)"));

        registerFunction(BIT_OR_FUNCTION_NAME, new SQLFunctionTemplate(IntegerType.INSTANCE, "(?1 | ?2)"));

        registerFunction(MATCH_FUNCTION_NAME, new SQLFunctionTemplate(StandardBasicTypes.DOUBLE,
                "match(?1) against  (?2 in boolean mode)"));

        registerFunction(JSON_SEARCH_FUNCTION_NAME, new SQLFunctionTemplate(StandardBasicTypes.STRING,
                "JSON_SEARCH(?1,'all',?2)"));
    }
}
