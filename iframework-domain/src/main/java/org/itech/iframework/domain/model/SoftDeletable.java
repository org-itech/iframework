package org.itech.iframework.domain.model;

/**
 * SoftDeletable
 *
 * @author liuqiang
 */
public interface SoftDeletable<ID> extends Persistable<ID> {
    /**
     * 是否删除
     *
     * @return 是否
     */
    boolean isDeleted();

    /**
     * 设置删除
     *
     * @param deleted 是否
     */
    void setDeleted(boolean deleted);
}