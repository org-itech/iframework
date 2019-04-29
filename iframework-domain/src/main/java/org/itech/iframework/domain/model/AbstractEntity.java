package org.itech.iframework.domain.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractEntity
 *
 * @author liuqiang
 */
@MappedSuperclass
public abstract class AbstractEntity implements Persistable<String>, Optimistic<String, Integer> {
    /**
     * 标识
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    /**
     * 获取标识
     *
     * @return 标识
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * 设置标识
     *
     * @param id 标识
     */
    protected void setId(String id) {
        this.id = id;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    @Override
    public Integer getVersion() {
        return null;
    }
}
