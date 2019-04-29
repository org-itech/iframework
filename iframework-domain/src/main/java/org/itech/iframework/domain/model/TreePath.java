package org.itech.iframework.domain.model;

/**
 * TreePath
 *
 * @author liuqiang
 */
public interface TreePath<Node extends TreeNode<Path, Node>, Path extends TreePath<Node, Path>> {
    /**
     * 获取祖先
     *
     * @return 祖先
     */
    Node getAncestor();

    /**
     * 设置祖先
     *
     * @param ancestor 祖先
     */
    void setAncestor(Node ancestor);

    /**
     * 获取后代
     *
     * @return 后代
     */
    Node getDescendant();

    /**
     * 设置后代
     *
     * @param descendant 后代
     */
    void setDescendant(Node descendant);

    /**
     * 获取深度
     *
     * @return 深度
     */
    int getDepth();

    /**
     * 设置深度
     *
     * @param depth 深度
     */
    void setDepth(int depth);
}
