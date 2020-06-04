package com.wzg.shutest;

public class NodeTest {
    int data;//节点数据
    NodeTest leftChild;//左子树的引用
    NodeTest rightChild;//右子树的引用
    boolean isDelete;//是否删除

    public NodeTest(int data){
        this.data = data;
    }

    //打印节点内容
    public void display(){
        System.out.println(data);
    }

}
