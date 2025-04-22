package com.example.datastructureproject3;

//Class Node for the AVL Tree
public class AVLTreeNode {
   private int element; //store data
   private AVLTreeNode left; // left child
   private AVLTreeNode right; //right child
   private int height; //Height

    private Object pointer;
    public AVLTreeNode(int element){
        this(element, null, null);
    }
    public AVLTreeNode(int element, AVLTreeNode left, AVLTreeNode right)
    {
        this.element=element;
        this.left=left;
        this.right=right;
        this.height=0;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Object getPointer() {
        return pointer;
    }

    public void setPointer(Object pointer) {
        this.pointer = pointer;
    }
}
