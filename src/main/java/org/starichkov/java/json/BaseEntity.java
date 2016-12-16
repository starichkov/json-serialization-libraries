package org.starichkov.java.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vadim Starichkov
 * @since 16.12.2016 17:45
 */
class BaseEntity {
    private int id;
    private String name;
    private List<BaseEntity> children = new ArrayList<>();

    public BaseEntity() {
    }

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseEntity> getChildren() {
        return children;
    }

    public void setChildren(List<BaseEntity> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return name + '#' + id + ", children=" + children;
    }

    public void addChild(BaseEntity child) {
        children.add(child);
    }
}
