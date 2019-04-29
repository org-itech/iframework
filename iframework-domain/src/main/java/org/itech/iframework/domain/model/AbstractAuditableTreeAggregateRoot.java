package org.itech.iframework.domain.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Optional;

/**
 * AbstractAuditableTreeAggregateRoot
 *
 * @param <Node> node
 * @param <Path> TreePath
 * @author liuqiang
 */
@MappedSuperclass
public abstract class AbstractAuditableTreeAggregateRoot<Path extends AbstractTreePath<Path, Node>
        , Node extends AbstractAuditableTreeAggregateRoot<Path, Node>>
        extends AbstractTreeAggregateRoot<Path, Node>
        implements Auditable<String, User, String, Date> {
    /**
     * 创建人标识
     */
    @CreatedBy
    @Column(length = 36)
    private String createdById;

    /**
     * 修改人标识
     */
    @LastModifiedBy
    @Column(length = 36)
    private String lastModifiedById;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createdOn;

    /**
     * 修改时间
     */
    @LastModifiedDate
    private Date lastModifiedOn;

    /**
     * 创建人
     */
    @Transient
    private User createdBy;

    /**
     * 修改人
     */
    @Transient
    private User lastModifiedBy;

    @Override
    public Optional<String> getCreatedById() {
        return Optional.ofNullable(createdById);
    }

    @Override
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    @Override
    public Optional<User> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public Optional<Date> getCreatedOn() {
        return Optional.ofNullable(createdOn);
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public Optional<String> getLastModifiedById() {
        return Optional.ofNullable(lastModifiedById);
    }

    @Override
    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    @Override
    public Optional<User> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @Override
    public Optional<Date> getLastModifiedOn() {
        return Optional.ofNullable(lastModifiedOn);
    }

    @Override
    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
