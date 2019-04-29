package org.itech.iframework.domain.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * AbstractTreePath
 *
 * @param <Node>
 * @author liuqiang
 */
@MappedSuperclass
public abstract class AbstractTreePath<Path extends AbstractTreePath<Path, Node>, Node extends AbstractTreeAggregateRoot<Path, Node>> extends AbstractEntity implements TreePath<Node, Path> {
    /**
     * 祖先
     */
    @OneToOne
    private Node ancestor;

    /**
     * 子代
     */
    @OneToOne
    private Node descendant;

    /**
     * 深度
     */
    private int depth;

    @Override
    public Node getAncestor() {
        return this.ancestor;
    }

    @Override
    public void setAncestor(Node ancestor) {
        this.ancestor = ancestor;
    }

    @Override
    public Node getDescendant() {
        return this.descendant;
    }

    @Override
    public void setDescendant(Node descendant) {
        this.descendant = descendant;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
