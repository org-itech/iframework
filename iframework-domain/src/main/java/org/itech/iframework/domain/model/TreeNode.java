package org.itech.iframework.domain.model;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.util.List;

/**
 * TreeNode
 *
 * @author liuqiang
 */
public interface TreeNode<Path extends TreePath<Node, Path>, Node extends TreeNode<Path, Node>> extends ResolvableTypeProvider {
    /**
     * 获取祖先路径
     *
     * @return 祖先路径
     */
    List<Path> getAncestorPaths();

    /**
     * 获取后代路径
     *
     * @return 后代路径
     */
    List<Path> getDescendantPaths();

    /**
     * 获取父级
     *
     * @return 父级
     */
    Node getParent();

    /**
     * 获取子级
     *
     * @return 子级
     */
    List<Node> getChildren();

    /**
     * 是否是叶子结点
     *
     * @return 是否是叶子结点
     */
    default boolean isLeaf() {
        return getChildren() != null && getChildren().size() > 0;
    }

    /**
     * 获取路径类型
     *
     * @return 路径类型
     */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    default Class<Path> getPathClass() {
        return (Class<Path>) getResolvableType().getGeneric(0).resolve();
    }

    /**
     * 获取  ResolvableType
     *
     * @return ResolvableType
     */
    @Override
    default ResolvableType getResolvableType() {
        return ResolvableType.forType(this.getClass().getGenericSuperclass());
    }
}
