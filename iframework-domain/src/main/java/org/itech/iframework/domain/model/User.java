package org.itech.iframework.domain.model;

/**
 * User
 *
 * @author liuqiang
 */
public class User implements Persistable<String> {
    /**
     * 用户标识
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
