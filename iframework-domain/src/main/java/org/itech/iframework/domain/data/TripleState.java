package org.itech.iframework.domain.data;

/**
 * TripleState
 *
 * @author liuqiang
 */
public interface TripleState extends Stateful<TripleStatus> {
    /**
     * 是否是新建
     *
     * @return 是否新建
     */
    default boolean isNew() {
        return getStatus() == TripleStatus.NEW;
    }

    /**
     * 是否有效
     *
     * @return 是否有效
     */
    default boolean isActive() {
        return getStatus() == TripleStatus.ACTIVE;
    }

    /**
     * 有效
     */
    default void active() {
        setStatus(TripleStatus.ACTIVE);
    }

    /**
     * 无效
     */
    default void inactive() {
        setStatus(TripleStatus.INACTIVE);
    }

    /**
     * 是否无效
     *
     * @return 是否无效
     */
    default boolean isInactive() {
        return getStatus() == TripleStatus.INACTIVE;
    }
}
