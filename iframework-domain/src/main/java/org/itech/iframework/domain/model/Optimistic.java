package org.itech.iframework.domain.model;

/**
 * Optimistic
 *
 * @param <ID>
 * @param <Version>
 * @author liuqiang
 */
public interface Optimistic<ID, Version> extends Persistable<ID> {
    /**
     * 获取版本号
     *
     * @return 版本号
     */
    Version getVersion();
}
