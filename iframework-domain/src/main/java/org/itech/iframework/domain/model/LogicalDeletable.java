package org.itech.iframework.domain.model;

/**
 * LogicalDeletable
 *
 * @author liuqiang
 */
public interface LogicalDeletable<ID> extends Persistable<ID> {
    /**
     * 是否逻辑删除
     *
     * @return 是否
     */
    boolean isDeleted();

    /**
     * 设置逻辑删除
     *
     * @param deleted 是否
     */
    void setDeleted(boolean deleted);
}