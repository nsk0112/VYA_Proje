package com.example.vya_proje_arayuz;

import java.util.Objects;

public class LinkedList {
    //Ilk degeri belirtmesi icin head degiskeni.
    LLNode head;

    public void initialize(String data){
        //Liste ilk baslatilirken kullanilan bu metodda head icin yeni bir node olusturulur.
        head = new LLNode();
        head.data = data;
        head.next = null;
    }

    public void add(String data){
        //Eklenecek deger icin yeni node olusturulur.
        LLNode newNode = new LLNode();
        newNode.data = data;
        newNode.next = null;
        LLNode temp = head;//Head gecici degiskene kopyalanir.
        while(temp.next != null) temp = temp.next; //Son node'a kadar gelinir.
        temp.next = newNode;//Son node'un next'i olarak yeni node atanir.
    }

    public boolean delete(String data){
        boolean situation = false;
        LLNode current = head;
        LLNode temp = null;
        if(Objects.equals(current.data, data)){
            //Silinecek eleman head ise direkt bir sonraki elemana gecilir.
            temp = current.next;
            head = temp;
        }
        else{
            while(current.next != null){//Tum liste gezilir.
                if(Objects.equals(current.next.data, data)){
                    //Eslesme bulundugunda silinecek elemandan onceki elemanin yeni sonraki elemani, silinecek elemanin
                    //onceki sonraki elemani olarak atanir.
                    temp = current.next;
                    current.next = temp.next;
                    situation = true;
                    break;
                }
                current = current.next;
            }
        }
        return situation;
    }

    public void print(){
        if(head == null) {
            System.out.println("listede eleman bulunmamaktadir...");
            return;
        }

        LLNode temp = head;
        while(temp != null){
            //En bastaki elemandan baslanarak tum elemanlar sirayla yazdirilir.
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}
