package com.example.vya_proje_arayuz;

public class Node<T> {
    //Stack ve queue yapilarinda kullanilacak Node class'i.
    T key;
    Node next;

    public Node(T key){
        this.key = key;
        this.next = null;
    }
}
