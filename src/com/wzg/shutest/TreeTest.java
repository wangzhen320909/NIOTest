package com.wzg.shutest;

public interface TreeTest {
    //查找节点
    public NodeTest find(int key);
    //插入新节点
    public boolean insert(int data);

    //中序遍历
    public void infixOrder(NodeTest current);
    //前序遍历
    public void preOrder(NodeTest current);
    //后序遍历
    public void postOrder(NodeTest current);

    //查找最大值
    public NodeTest findMax();
    //查找最小值
    public NodeTest findMin();

    //删除节点
    public boolean delete(int key);

    //Other Method......
}
