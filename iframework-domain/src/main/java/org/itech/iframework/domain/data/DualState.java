package org.itech.iframework.domain.data;

/**
 * DualState
 *
 * @author liuqiang
 */
public interface DualState extends Stateful<DualStatus> {
    /**
     * 是否有效
     *
     * @return 是否有效
     */
    default boolean isActive() {
        return getStatus() == DualStatus.ACTIVE;
    }

    /**
     * 有效
     */
    default void active() {
        setStatus(DualStatus.ACTIVE);
    }

    /**
     * 无效
     */
    default void inactive() {
        setStatus(DualStatus.INACTIVE);
    }

    /**
     * 是否无效
     *
     * @return 是否无效
     */
    default boolean isInactive() {
        return getStatus() == DualStatus.INACTIVE;
    }
}
