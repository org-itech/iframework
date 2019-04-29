package org.itech.iframework.domain.model;

import java.util.Optional;

/**
 * Auditable
 *
 * @param <UID>
 * @param <U>
 * @param <ID>
 * @param <T>
 * @author liuqiang
 */
public interface Auditable<UID, U extends Persistable<UID>, ID, T> extends Persistable<ID> {
    /**
     * 获取 创建人标识
     *
     * @return 创建人标识
     */
    Optional<UID> getCreatedById();

    /**
     * 设置 创建人标识
     *
     * @param createdById 创建人标识
     */
    void setCreatedById(UID createdById);

    /**
     * 获取创建人
     *
     * @return 创建人
     */
    Optional<U> getCreatedBy();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Optional<T> getCreatedOn();

    /**
     * 设置创建时间
     *
     * @param createdOn 创建时间
     */
    void setCreatedOn(T createdOn);

    /**
     * 获取修改人标识
     *
     * @return 修改人标识
     */
    Optional<UID> getLastModifiedById();

    /**
     * 设置修改人标识
     *
     * @param lastModifiedById 修改人标识
     */
    void setLastModifiedById(UID lastModifiedById);

    /**
     * 获取修改人
     *
     * @return 修改人
     */
    Optional<U> getLastModifiedBy();

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    Optional<T> getLastModifiedOn();

    /**
     * 设置修改时间
     *
     * @param lastModifiedOn 修改时间
     */
    void setLastModifiedOn(T lastModifiedOn);
}
