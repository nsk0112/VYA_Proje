package com.example.vya_proje_arayuz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OgretimUyesi {
    @FXML
    private Label kull;
    @FXML
    private Label sifre;
    @FXML
    private TextField kullGir;
    @FXML
    private TextField sifreGir;
    @FXML
    private Button girisYap;
    @FXML
    private Label girisYapildi;
    @FXML
    private Button bilgileriGoruntule;
    @FXML
    private Button notGirisi;
    @FXML
    private TextArea genelLabel;
    @FXML
    private ProgressBar sifreProgress;
    @FXML
    private Label dersAdi;
    @FXML
    private Label ogrNo;
    @FXML
    private Label not;
    @FXML
    private TextField dersAdiGir;
    @FXML
    private TextField ogrNoGir;
    @FXML
    private TextField notGir;
    @FXML
    private Button notEkle;
    @FXML
    private Button dersAdiButon;
    @FXML
    private Button notGoruntule;
    @FXML
    private TextField getDers;
    @FXML
    private Button cikisButon;
    @FXML
    private Button notbuton;


    public void ogrGiris() throws IOException {
        //Ogretim uyelerinin giris yapmasi icin kullanilan bu sinifita oncelikle arayuzdeki TextField'lardan ad-soyad ve
        //sifre girisi alinir. Bu bilgiler ogretim gorevlilerinin biilgilerinin bulundugu dosyadan kontrol edilir.
        String adsoyad = kullGir.getText();
        String sifre = sifreGir.getText();
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\OgretimUyesi.txt");
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            Stack<String> stack = new Stack<>();//Bilgilerin saklanacagi stack olusturuluyor.
            String satir = scanner.nextLine();
            String[] ayir = satir.split(" ");
            String[] advesoyad = adsoyad.split(" ");
            advesoyad[0] = advesoyad[0].substring(0,1).toUpperCase()+advesoyad[0].substring(1);//Ad-soyadin ilk harfleri eslesme saglanmasi icin buyutuluyor.
            advesoyad[1] = advesoyad[1].substring(0,1).toUpperCase()+advesoyad[1].substring(1);
            stack.push(ayir[5]);//Bilgiler stack'e aktariliyor.
            stack.push(ayir[2]);
            stack.push(ayir[1]);
            if(stack.pop().equals(advesoyad[0])){
                if(stack.pop().equals(advesoyad[1])){
                    if(stack.pop().equals(sifre)){
                        //Bilgiler sirayla stack'ten cikarilip dosyadaki her bir satirla karsilastiriliyor.
                        //Eslesme oldugunda basarili giris olmus oluyor ve bilgiler tekrar kullanim icin bir dosyaya yaziliyor.
                        girisYapildi.setText("Giris basarili...");
                        File file2 = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\OgretimUyesi.txt");
                        Scanner s1 = new Scanner(file2);
                        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt"));
                        bw.write(satir);
                        bw.close();
                        secenekSayfasi();
                    }
                    girisYapildi.setText("Sifre yanlis.");
                }
            }
            else girisYapildi.setText("Bilgiler yanis.");
        }
    }

    public void bilgilerimiGoruntule() throws IOException {
        //Bilgiler goruntulenmek istendiginde dosyadan tekrar okuma yapiliyor.
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
        Scanner scanner = new Scanner(file);
        String yaz = scanner.nextLine();
        String[] kelimeler = yaz.split(" ");
        String bolum2 = "";
        for(int i=7; i<kelimeler.length; i++) bolum2 += (kelimeler[i]+" ");//Satir bolunerek her bir bilginin ayri olarak alinmasi saglaniyor.
        String bolum = bolum2.split("\\+")[0];
        String yazilacak = "Unvan:                   "+kelimeler[0]+
                "\nAd:                         "+kelimeler[1]+
                "\nSoyad:                    "+kelimeler[2]+
                "\nT.C. Kimlik No:       "+kelimeler[3]+
                "\nDogum Tarihi:       "+kelimeler[4]+
                "\nFakulte:                  "+kelimeler[6]+" Fakultesi"+
                "\nBolum:                   "+bolum;//Bilgiler uygun formatta bir String icinde birlestiriliyor.
        String[] dersler = yaz.split("\\+");
        if(dersler.length > 0){
            yazilacak = yazilacak + "\nVerilen Dersler:      ";
            for(int i=1; i<dersler.length; i++) yazilacak = yazilacak + dersler[i] + "\n                               ";
        }
        genelLabel.setText(yazilacak);//Bilgiler ekrana yazdiriliyor.
    }

    public void sifreGir(){
        //Ekranda gorulen ProgressBar, girilen sifre 6 haneli olunca doluyor.
        sifreProgress.setProgress(sifreGir.getText().length() * 0.1667);
    }

    public void notGoruntule() throws IOException {
        //Notlari gorntulenmek istenen dersin adi butona basilinca TextField'dan alinir.
        String ders = getDers.getText();
        String[] derskelimeler = ders.split(" ");
        //String[] u = null;
        //String[] a = kullGir.getText().split(" ");
        File fileuse = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
        Scanner scanneruse = new Scanner(fileuse);
        String[] satiroku = scanneruse.nextLine().split(" ");
        String adsoyad = satiroku[1]+" "+satiroku[2];//Ogretim uyesinin adi ve soyadi birlestirilerek tek String'e atanir.
        StringBuilder dersbirlestir = new StringBuilder();
        if(derskelimeler.length == 1)derskelimeler[0] = derskelimeler[0].substring(0,1).toUpperCase() + derskelimeler[0].substring(1);
        for (int j = 0; j < derskelimeler.length; j++){
            derskelimeler[j] = derskelimeler[j].substring(0,1).toUpperCase() + derskelimeler[j].substring(1);
            dersbirlestir.append(derskelimeler[j]);
        }//Arayuzden alinan ders adi kelimelere bolunur ve her kelimenin ilk harfi buyutulur. Bunun sebebi dosya isimlerinin
        //ilk harfi buyuk olarak belirlenmesidir.
        try{
            String dersadi = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\Dersler\\"+dersbirlestir+".txt";
            File dosya = new File(dersadi);//Olusturulan yeni ders adi dosya yolu olarak birlestirilir ve dosya acilir.
            Scanner s1 = new Scanner(dosya);
            String[] ilksatir = s1.nextLine().split("\\+");//Ilk satirda ders adi ve ogretim uyesi adi bulunur.

            if(ilksatir[1]!=null && ilksatir[1].equals(adsoyad)) {//Ilk satirdaki ogretim uysei adi sisteme giris yapmis olan ogretim
                //gorevlisi adi ile ayni ise notlar okunmaya baslanir. Degilse islemin yapilmasina izin verilmez.
                BinarySearchTree bstVize = new BinarySearchTree();
                BinarySearchTree bstFinal = new BinarySearchTree(); //Vize ve final notlari icin ayri binary search tree'ler olusturulur.
                float vizetoplam = 0;
                float finaltoplam = 0;//Vize ve final ortalamalarinin hesaplanmasi icin oncelikle toplamlarinin bulunmasi gerekir.
                //Bunun icin iki farkli toplam degiskeni olusturulur.
                int z = 0;//z degiskeni if bloguna girip girilmeyecegini tespit icin kullanilir.
                while (s1.hasNextLine()) {
                    if (z == 0) {
                        if (ilksatir.length == 2) {//Ilk satirin ogretim gorevlisi kismi dolu ise ekrana yazdirilir.
                            System.out.println("Öğretim Görevlisi: " + ilksatir[1]);
                        } else {
                    }
                        z++;
                    } else {
                        String satir = s1.nextLine();//Ilk satir haric diger satirlar icin bu blok kullanilir.
                        String[] x = satir.split(" ");//Satirdaki degerler ayrilir.
                        float vizepuan = Float.parseFloat(x[1]);
                        vizetoplam += vizepuan;//Vize degeri float turune cast edilerek toplama aktarilir.
                        bstVize.insert(x[0], vizepuan);//Vize bst'sine ogrenci no ve vize notu eklenir.
                        if (x.length == 3) {//Satir uzunlugu 3 ise yani final notu varsa:
                            float finalpuan = Float.parseFloat(x[2]);
                            finaltoplam += finalpuan;//Final degeri float turune cast edilerek toplama aktarilir.
                            bstFinal.insert(x[0], finalpuan);//Final bst'sine ogrenci no ve final notu eklenir.
                        }
                    }
                }
                StringBuilder yaz = new StringBuilder();
                yaz.append("Vize Notlari:  \n");
                bstVize.inorder();//Bu metodda inorder traversal ile tum degerler bir dosyaya yazilir.
                Scanner scanner1 = new Scanner(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use2.txt"));
                while (scanner1.hasNextLine()) {
                    String oku = scanner1.nextLine();
                    yaz.append(oku + "\n");//Degerler dosyadan okunarak bir StringBuilder'da toplanir.
                }
                yaz.append("\n\n");
                scanner1.close();
                float vizeortalama = vizetoplam / bstVize.countNode(bstVize.root);
                yaz.append("Vize ortalamasi: " + vizeortalama + "\n");//Vize ortalamasi hesaplanip yaziya eklenir.
                if(bstFinal.root != null){//Final notlari girilmis ise:
                    yaz.append("Final Notlari:  \n");
                    bstFinal.inorder();//Bu metodda inorder traversal ile tum degerler bir dosyaya yazilir.
                    Scanner scanner2 = new Scanner(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use2.txt"));
                    while (scanner2.hasNextLine()) {
                        String oku = scanner2.nextLine();
                        yaz.append(oku + "\n");//Degerler dosyadan okunarak bir StringBuilder'da toplanir.
                    }
                    float finalortalama = finaltoplam / bstFinal.countNode(bstFinal.root);
                    yaz.append("Final ortalamasi:  " + finalortalama);//Final ortalamasi hesaplanip yaziya eklenir.
                }
                genelLabel.setText(String.valueOf(yaz));//Butun degerler ekrana yazdirilir.
            }
            else{
            genelLabel.setText("\nBu işlem için yetkiniz bulunmamaktadır.");//Dersin ogretim gorevlisi olmaya kisiler notlari goruntuleyemez.
            }}
        catch (Exception e){genelLabel.setText("Dosya bulunamadi. Lutfen tekrar deneyiniz...");}
    }

    @FXML
    public void girisSayfa() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ogrUye.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 420);
        Stage stage = new Stage();
        stage.setTitle("Giris Sayfasi");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void secenekSayfasi() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ogrUyeSecenek.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 420);
        Stage stage = new Stage();
        stage.setTitle("Secenek Sayfasi");
        stage.setScene(scene);
        stage.show();
    }

    public void cikis(){
        System.exit(0);
    }
}
