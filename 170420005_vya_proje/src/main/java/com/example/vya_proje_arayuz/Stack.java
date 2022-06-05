package com.example.vya_proje_arayuz;

public class Stack<T> {
    //Stack'in en ustteki elemani.
    private Node top;

    public Stack(){
        this.top = null;
    }//Yeni stack olustururken en ustteki eleman null olarak tanimlaniyor.

    public void push(T x){
        //Yeni eleman eklenirken yeni bir node olusturuluyor.
        Node node = new Node(x);
        if(node == null) {
            System.out.println("heap overflow");
            return;
        }
        node.key = x;
        node.next = top;
        top = node;//Top eleman olarak ataniyor.
    }

    public T peek(){
        if(isEmpty()){
            System.out.println("stack is empty");
            System.exit(-1);
        }
        return (T) top.key;//En ustteki eleman donduruluyor.
    }

    public T pop(){
        if(isEmpty()){
            System.out.println("stack underflow");
            System.exit(-1);
        }
        T top = peek();//En ustteki elemana bakiliyor.
        this.top = (this.top).next;//Ve eleman stack'ten uzaklastiriliyor.
        return top;
    }

    public boolean isEmpty(){
        return top == null;
    }//Stack bos olup olamdigini donduruyor.

}
