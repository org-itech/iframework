package org.itech.iframework.domain.model;

/**
 * User
 *
 * @author liuqiang
 */
public interface User {
    /**
     * 获取用户标识
     *
     * @return 用户标识
     */
    String getId();

    /**
     * 获取用户名称
     *
     * @return 用户名称
     */
    String getName();

    /**
     * 获取用户代码
     *
     * @return 用户代码
     */
    String getCode();
}
