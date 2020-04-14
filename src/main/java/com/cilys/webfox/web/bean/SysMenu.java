package com.cilys.webfox.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysMenu implements Serializable {

    private int id;
    private int pId = 0;    //父节点为0，则表明该节点是根节点

    private String name;    //名称
    private String href;    //链接的网页地址

    private List<SysMenu> children; //子节点

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public void addChild(SysMenu sysMenu){
        if (sysMenu == null){
            return;
        }
        if (children == null){
            children = new ArrayList<>();
        }
        children.add(sysMenu);
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", children=" + children +
                '}';
    }
}
