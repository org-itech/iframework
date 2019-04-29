package org.itech.iframework.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * AbstractTreeAggregateRoot
 *
 * @param <Node> node
 * @param <Path> TreePath
 * @author liuqiang
 */
@MappedSuperclass
public abstract class AbstractTreeAggregateRoot<Path extends AbstractTreePath<Path, Node>, Node extends AbstractTreeAggregateRoot<Path, Node>>
        extends AbstractAggregateRoot
        implements TreeNode<Path, Node> {
    public AbstractTreeAggregateRoot() {
        this.ancestors = new ArrayList<>();
        this.descendants = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    @Transient
    private boolean parentChanged;

    /**
     * 父代路径
     */
    @OneToMany(mappedBy = "descendant", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "depth")
    private final List<Path> ancestors;

    /**
     * 后代路径
     */
    @OneToMany(mappedBy = "ancestor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "depth")
    private final List<Path> descendants;

    /**
     * 子代
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Node> children;

    /**
     * 父节点
     */
    @OneToOne
    private Node parent;

    /**
     * 获取父节点
     *
     * @return 父节点
     */
    @Override
    public Node getParent() {
        return this.parent;
    }

    /**
     * 获取祖先路径
     *
     * @return 祖先路径
     */
    @Override
    public List<Path> getAncestorPaths() {
        return ancestors;
    }

    /**
     * 获取后代路径
     *
     * @return 后代路径
     */
    @Override
    public List<Path> getDescendantPaths() {
        return descendants;
    }

    /**
     * 获取子级
     *
     * @return 子级
     */
    @Override
    public List<Node> getChildren() {
        return this.children;
    }

    @Transient
    public boolean isRoot() {
        return this.parent == null;
    }

    public String getParentId() {
        return this.getParent() == null ? null : this.getParent().getId();
    }

    public void setParent(Node parent) {
        if (this.parent != parent) {
            parentChanged = true;
        }

        this.parent = parent;

        if (this.isNew()) {
            initTreePath();
        } else {
            if (parentChanged) {
                changTreePath();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void initTreePath() {
        this.getAncestorPaths().add(createPath((Node) this, (Node) this, 0));

        if (this.getParent() != null) {
            for (Path path : this.getParent().getAncestorPaths()) {
                this.getAncestorPaths().add(createPath(path.getAncestor(), (Node) this, path.getDepth() + 1));
            }
        }
    }

    public void changTreePath() {
        Set<Node> ancestors = this.getAncestorPaths().stream()
                .filter(item -> !item.getAncestor().equals(this))
                .map(AbstractTreePath::getAncestor)
                .collect(toSet());

        for (Path path : this.getDescendantPaths()) {
            path.getDescendant().getAncestorPaths()
                    .removeIf(item -> ancestors.contains(item.getAncestor()));
        }

        if (getParent() != null) {
            // 重建path
            for (Path path : getParent().getAncestorPaths()) {
                Node ancestor = path.getAncestor();

                // 重建子集path
                for (Path subPath : this.getDescendantPaths()) {
                    Node descendant = subPath.getDescendant();
                    descendant.getAncestorPaths().add(createPath(ancestor, descendant, subPath.getDepth() + path.getDepth() + 1));
                }
            }
        }
    }

    protected Path createPath(Node ancestor, Node descendant, Integer depth) {
        try {
            Path path = getPathClass().newInstance();

            path.setAncestor(ancestor);
            path.setDepth(depth);
            path.setDescendant(descendant);

            return path;
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
