package com.example.vya_proje_arayuz;

public class BSTNode {
    //binary search tree de kullanilacak olan node'lar olusturulur.
    float key;
    String name;
    BSTNode left, right;

    public BSTNode(String name, float key){
        this.key = key;
        this.name = name;
        left = right = null;
    }
}
