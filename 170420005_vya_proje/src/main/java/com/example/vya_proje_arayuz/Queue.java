package com.example.vya_proje_arayuz;

public class Queue {
    //Queue'nun on ve arka ucu icin degiskenler.
    Node front, rear;

    public Queue(){
        this.front = this.rear = null;
    }//Constructor metod.

    public void enqueue(String key){
        Node temp = new Node(key);
        if(this.rear == null){
            this.front = this.rear = temp;
            return;
        }

        this.rear.next = temp;
        this.rear = temp;
    }

    public void dequeue(){
        if(this.front == null) return;
        Node temp = this.front;
        this.front = this.front.next;

        if(this.front == null) this.rear = null;
    }

    public boolean isEmpty(){
        return rear == null && front == null;
    }

    public void print(){
        Node temp = front;
        while(temp!=null){
            System.out.print(temp.key + " <- ");
            temp = temp.next;
        }
        System.out.println("null");
    }
}
