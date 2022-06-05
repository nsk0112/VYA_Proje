package com.example.vya_proje_arayuz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class BinarySearchTree {
    //BSTNode turunden root tanimlanir.
    BSTNode root;

    BinarySearchTree() {root = null;}//Constructor metod.

    void insert(String name, float key){root = insertRec(root, name, key);}//Insert islemi icin recursive yapi kulllanilir.

    BSTNode insertRec(BSTNode root, String name, float key){
        if (root == null) {//Root null ise yeni root olusturulur.
            root = new BSTNode(name, key);
            return root;
        }
        if (key < root.key)//Eklenen deger root'tan buyuk ise sagina dogru recursive ekleme yapilir.
            root.left = insertRec(root.left, name, key);
        else if (key > root.key)//Eklenen deger root'tan kucuk ise soluna dogru recursive ekleme yapilir.
            root.right = insertRec(root.right, name, key);
        return root;
    }

    void inorder() throws IOException {
        FileWriter fw = new FileWriter(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use2.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        //Inorder traversal icin oncelikle dosyaya yazmada kullanilacak olan BufferedWriter ve FileWriter nesneleri olusturulur.
        if(root == null) return;
        Stack<BSTNode> s = new Stack<>();//Kullanilacak stack tanimlanir.
        BSTNode curr = root;//Root gecici bir degiskene kopyalanir.

        while(curr != null || s.size() > 0){//Node bulundugu surece...
            while(curr != null){
                s.push(curr);
                curr = curr.left;//Node'lar stack'e eklenir ve null olana kadar soluna dogru devam edilir.
            }
            curr = s.pop();//Stack'in en ustundki eleman alinir.
            bw.write(curr.name +"    "+curr.key);
            bw.write("\n");//Adi ve notu yazilir ve bir satir atlanir.
            curr = curr.right;//Ayni islem saga dogru devam eder.
        }
        bw.close();
    }

    public int countNode(BSTNode root){
        if(root==null)
            return 0;
        return 1 + countNode(root.left) + countNode(root.right);//Ortalama hesaplanirken kullanilacak toplam node sayisini hesaplar.
        //sola ve saga dogru recursive yapiyla tum node'lari dolanir.
    }

}
