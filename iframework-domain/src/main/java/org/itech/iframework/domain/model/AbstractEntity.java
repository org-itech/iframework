package org.itech.iframework.domain.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.itech.iframework.domain.usertype.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * AbstractEntity
 *
 * @author liuqiang
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
        @TypeDef(name = "BitEnum", typeClass = BitEnumType.class),
        @TypeDef(name = "BitEnumSet", typeClass = BitEnumSetType.class),
        @TypeDef(name = "Option", typeClass = OptionType.class),
        @TypeDef(name = "OptionSet", typeClass = OptionSetType.class),
        @TypeDef(name = "BitOptionSet", typeClass = BitOptionSetType.class)
})
public abstract class AbstractEntity implements Optimistic<String, Integer> {
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
