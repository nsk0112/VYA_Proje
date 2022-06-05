package com.example.vya_proje_arayuz;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Ogrenci {
    //Arayuzlerde kullanilacak JavaFX node'ları tanimlandi.
    @FXML
    private Label ogrno;
    @FXML
    private Label sifre;
    @FXML
    private TextField nogir;
    @FXML
    private TextField sifregir;
    @FXML
    private Button gir;
    @FXML
    private Label girisBasarili;
    @FXML
    private Label hosgeldiniz;
    @FXML
    private Label anaSecenek;
    @FXML
    private Button bilgilerimiGoster;
    @FXML
    private Button notGoster;
    @FXML
    private TextArea bilgiler;
    @FXML
    private Button oBelOlustur;
    @FXML
    private Button cikis;
    @FXML
    private Button transkriptOlustur;
    @FXML
    private ProgressBar sifreProgress;


    //Tum class'ta kullanilabilmesi icin HashTable class genelinde tanimlandi. Yine HashTable'da kullanilacak olan key
    //degiskeni de int turunde burada tanimlandi.
    Hashtable<Object, Object> ogrenciler = new Hashtable<>();
    int key;

    //Constructor metodda oListele metodu cagrilarak ogrenci objesi olusturuldugu anda text dosyasından okunan ogrenci bilgilerinin
    //HashTable'a aktarilmasi saglandi.
    public Ogrenci(){
        oListele();
    }

    public void oListele(){
        //Dosyadan okuma sirasinda olusabilecek hatalara karsi try blogu icine alinan bu bolumde, tum ogrencilerin bilgilerinin
        //oldugu dosyadan ogrenci numaralari alinip 1000 ile modu alindi ve HashTable'a buna gore yerlestirildi.
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\OgrenciListe.txt"));
            String satir = br.readLine();
            while (satir!=null){
                String[] ayir = satir.split(" ");
                int key2 = Integer.parseInt(ayir[0]);
                key2 = key2 % 1000;
                ogrenciler.put(key2, ayir[5]);//key'e karsilik gelen deger ogrencinin sifresi.
                satir = br.readLine();
            }
            br.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public void oGiris() throws IOException {
        //Arayuzdeki TextArea'lardan alinan degerler HashTable'daki degerlere gore sinaniyor.
        String n = nogir.getText();
        String s = sifregir.getText();
        int kull = Integer.parseInt(n);
        key = kull % 1000;
        if (ogrenciler.containsKey(key)){
            if (s.equals(ogrenciler.get(key))){
                //Ogrenci numarasi bulundugu ve HashTable'daki degerle eslestigi durumda giris basarili oluyor.
                girisBasarili.setText("Giris Basarili...");
                File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\OgrenciListe.txt");
                Scanner s1 = new Scanner(file);
                BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt"));
                //Basarili giris oldugu takdirde ogrencinin bilgileri daha sonra okunmak uzere bir dosyay yazdiriliyor.
                String satir = null;
                while(s1.hasNextLine()){
                    satir = s1.nextLine();
                    String[] satirAyir = satir.split(" ");
                    if(Integer.parseInt(satirAyir[0]) == Integer.parseInt(n)) break;
                }
                bw.write(satir);
                bw.close();
                secenekSayfasi();
            }
            else girisBasarili.setText("Sifre Yanlis...");
        }
        else girisBasarili.setText("Bilgiler Yanlis...");
        //Yanlis sifre veya ogrenci numarasi durumunda ekrana bilgi mesaji yazdiriliyor.
    }

    public void sifreGir(){
        //Ekranda gorulen ProgressBar, girilen sifre 6 haneli olunca doluyor.
        sifreProgress.setProgress(sifregir.getText().length() * 0.1667);
    }

    public void notlariGoster() throws FileNotFoundException {
        File fileuse = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
        Scanner scanner = new Scanner(fileuse);
        String[] kelimeler = scanner.nextLine().split(" ");//Giris yapmis olan ogrencinin bilgilerinin bulundugu dosya
        //acilarak icindeki bilgilere bolunuyor.
        Queue queue = new Queue();
        //for(int i=0; i<kelimeler.length; i++)
        String path = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\Dersler";
        File dosya = new File(path);
        //Notlarin oldugu text dosyalarinin tutuldugu klasor yolu belirtiliyor.
        StringBuilder yaz = new StringBuilder();

        if(dosya.isDirectory()){
            //Yukarida belirtilen klasor bir dosya ise bu bloga giriliyor.
            File[] tum = dosya.listFiles(); //Klasordeki tum dosyalar bir diziye atiliyor.
            String no = kelimeler[0];
            for(int i = 0; i < tum.length; i++){//Tum dosyalar sirayla okunuyor.
                Scanner s = new Scanner(tum[i]);
                int a = 0;
                String dersadi = "";
                String egitmen = "";
                while(s.hasNextLine()){
                    String str = s.nextLine();
                    if(a==0){//Dosyanin ilk satirindaki ders adi ve ogretim gorevlisi adi ayriliyor.
                        dersadi = str;
                        String[] ayir = dersadi.split("\\+");
                        dersadi = ayir[0];
                        if(ayir.length>1){
                            egitmen = ayir[1];
                            a++;
                        }
                        else a++;

                    }
                    else{//Diger satirlarda aranan ogrenci numarasina rastlanilirsa String'e ekleniyor.
                        if(str.contains(no)){
                            queue.enqueue(dersadi);
                            if(egitmen!=null) queue.enqueue("\nÖğretim Görevlisi:  "+egitmen);
                            String[] ayir = str.split(" ");
                            queue.enqueue("\nVize: "+ayir[1]);
                            if(ayir.length>2){//Final notu eklenmisse yaziliyor.
                                queue.enqueue("    Final: "+ayir[2]+"\n\n");
                            }
                        }
                    }
                }
            }
            String bitir = "";
            while(!queue.isEmpty()){
                bitir += (queue.front.key);
                queue.dequeue();
            }
            bilgiler.setText(bitir);
            //Son olarak tum not bilgisi ekrana yazdiriliyor.
        }
    }

    @FXML
    public void girisSayfa() throws IOException {
        //Ogrenci girisi icin giris sayfasi olusturuluyor.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ogrenci.fxml"));
        //FXML dosyası FXMLLoader ile yukleniyor.
        Scene scene = new Scene(fxmlLoader.load(), 640, 420);
        Stage stage = new Stage();
        //Scene ve Stage nesneleri ile sahne hazirlaniyor.
        stage.setTitle("Giris Sayfasi");
        stage.setScene(scene);
        stage.show();//Sayfa gosteriliyor.
    }

    @FXML
    public void secenekSayfasi() throws IOException {
        //Ogrenci giris yaptiktan sonra secim yapmasi icin secim sayfasi gosteriliyor.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ogrenciSecenek.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 420);
        Stage stage = new Stage();
        stage.setTitle("Secenek Sayfasi");
        stage.setScene(scene);
        stage.show();
    }

    public void bilgileriGoster(){
        //Dosyanin bulunamamasi ihtimaline karsi try blogu icinde islemler yapiliyor.
        try {
            File fileuse = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
            Scanner s2 = new Scanner(fileuse);//Giris yapmis olan ogrencinin bilgilerinin bulundugu dosyada ogrenci bilgileri okunuyor.
            LinkedList list = new LinkedList();//Bilgilerin eklenmesi icin linked list nesnesi olusturuluyor.
            String[] satir = s2.nextLine().split(" ");//Ogrenci bilgileri kelimelere ayriliyor.
            list.initialize(satir[0]);//Linked list ilk ogrenci bilgisiyle baslatiliyor.
            for(int i=1; i<5; i++) list.add(satir[i]);
            list.add(satir[6]);
            StringBuilder bolum = new StringBuilder();
            for(int i=7; i<satir.length; i++) bolum.append(satir[i]+" ");//Bolum bilgisi olusturuluyor.
            list.add(bolum.toString());//Butun bilgiler linked list'e ekleniyor.

            String yaz = "Ogrenci No:          "+list.head.data+
                    "\nAd:                         "+list.head.next.data+
                    "\nSoyad:                    "+list.head.next.next.data+
                    "\nDogum Tarihi:       "+list.head.next.next.next.data+
                    "\nT.C. Kimlik No:       "+list.head.next.next.next.next.data+
                    "\nFakulte:                  "+list.head.next.next.next.next.next.data+" Fakultesi"+
                    "\nBolum:                   "+list.head.next.next.next.next.next.next.data;
            //Linked list uzerinde gezinilerek butun bilgiler okunuyor ve bir String'e ekleniyor.
            bilgiler.setText(yaz);//Bilgiler ekrana bastiriliyor.
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @FXML
    public void oBelOlustur() throws IOException {
        bilgiler.setText("Belge Hazirlaniyor...");
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
        Scanner s = new Scanner(file);//Giris yapmis olan ogrencinin bilgileri dosyadan aliniyor.
        String[] ogr = s.nextLine().split(" ");
        String dosyaadi = ogr[0]+ogr[1]+ogr[2]+".docx";//Olusturulacak dosyanin adi ogrenci bilgileri ile olusturuluyor.
        File dosya = new File(dosyaadi);
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(dosya);
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date tarih = new Date();

        //Bundan sonraki satirlarda yeni paragraflar olusturulup bilgiler sirayla yazdiriliyor.
        //Bunun yapilabilmesi icin Apache Poi modulu kullaniliyor.
        XWPFParagraph p0 = document.createParagraph();
        p0.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun r0 = p0.createRun();
        r0.setFontSize(11);
        r0.setText(sdf.format(tarih));
        r0.addBreak();

        XWPFParagraph p1 = document.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setFontSize(20);
        r1.setText("MARMARA ÜNİVERSİTESİ REKTÖRLÜĞÜ");

        XWPFParagraph p2 = document.createParagraph();
        p2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r2 = p2.createRun();
        r2.setFontSize(18);
        r2.setBold(true);
        r2.setText("ÖĞRENCİ BELGESİ");

        XWPFParagraph px = document.createParagraph();
        XWPFRun rx = px.createRun();
        rx.setText("");

        String[] x = {"Adı:                    ", "Soyadı:              ", "Doğum Tarihi:  ", "T.C. Kimlik No: "};
        for(int i=1;i<5;i++){
            XWPFParagraph p = document.createParagraph();
            XWPFRun r = p.createRun();
            r.setFontSize(12);
            r.setText(x[i-1] + "     "+ ogr[i]);
        }


        XWPFParagraph p3 = document.createParagraph();
        XWPFRun r3 = p3.createRun();
        r3.setFontSize(12);
        r3.setText("Fakülte:                  " + ogr[6] + " Fakültesi");

        StringBuilder bolum = new StringBuilder();
        for(int i=7; i<ogr.length;i++) bolum.append(ogr[i]).append(" ");

        XWPFParagraph p4 = document.createParagraph();
        XWPFRun r4 = p4.createRun();
        r4.setFontSize(12);
        r4.setText("Bölüm:                   " + bolum);

        for(int i=0;i<7;i++){
            XWPFParagraph px1 = document.createParagraph();
            XWPFRun rx1 = px1.createRun();
            rx1.setText("");
        }

        XWPFParagraph p5 = document.createParagraph();
        p5.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r5 = p5.createRun();
        r5.setBold(true);
        r5.setFontSize(14);
        r5.setText("İLİGİLİ MAKAMA");

        XWPFParagraph p6 = document.createParagraph();
        XWPFRun r6 = p6.createRun();
        r6.setFontSize(12);
        r6.setText("Yukarıda kimlik bilgileri yer alan " + ogr[1] + " " + ogr[2] +
                " isimli kişinin Marmara Üniversitesi tarafından yukarıda belirtilen\n" +
                "programın kayıtlı öğrencisi olduğu bildirilmiştir.");

        document.write(out);
        out.close();

        try {if (Desktop.isDesktopSupported()) {Desktop.getDesktop().open(new File(dosya.getAbsolutePath()));}}
        //Son olarak olusturulan dosya aciliyor.
        catch (IOException ioe) {ioe.printStackTrace();}
    }

    public void transkript() throws IOException {
        //Ogrenci belgesi olusturma metoduna benzer sekilde bir dosya olusturuluyor ve not bilgileri bir tablo seklinde yazdiriliyor.
        bilgiler.setText("Not dokum belgesi olusturuluyor...");
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\use.txt");
        Scanner suse = new Scanner(file);
        String[] ogr = suse.nextLine().split(" ");
        String dosyaadi = ogr[0]+ogr[1]+ogr[2]+"transkript.docx";
        File dosya = new File(dosyaadi);
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(dosya);

        XWPFParagraph p0 = document.createParagraph();
        p0.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun r0 = p0.createRun();
        r0.setFontSize(11);
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date tarih = new Date();
        r0.setText("Belge Tarihi: " + sdf.format(tarih));
        r0.addBreak();


        XWPFParagraph p1 = document.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        r1.setFontSize(14);
        r1.setBold(true);
        r1.setText("MARMARA ÜNİVERSİTESİ");

        XWPFParagraph p2 = document.createParagraph();
        p2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r2 = p2.createRun();
        r2.setFontSize(14);
        r2.setBold(true);
        r2.setText("NOT DÖKÜM BELGESİ");


        String data = "Öğrenci No:          "+ogr[0]+"\nT.C. Kimlik No :    "+ogr[4]+
                "\nAd:                         "+ogr[1]+"\nSoyad:                   "+ogr[2]+"\nDoğum Tarihi:      "+ogr[3];

        for(int i=0;i<2;i++){
            XWPFParagraph px1 = document.createParagraph();
            XWPFRun rx1 = px1.createRun();
            rx1.setText("");
        }

        XWPFParagraph p3 = document.createParagraph();
        XWPFRun r3 = p3.createRun();
        if(data.contains("\n")){
            String[] lines = data.split("\n");
            r3.setText(lines[0], 0);
            for(int i=1; i< lines.length; i++){
                r3.addBreak();
                r3.setFontSize(12);
                r3.setText(lines[i]);
            }
        }

        else{
            r3.setText(data, 0);
        }

        r3.addBreak();
        r3.addBreak();


        XWPFTable tablo = document.createTable();
        tablo.setCellMargins(55,55,55,55);
        XWPFTableRow row1 = tablo.getRow(0);
        row1.getCell(0).setText("Ders");
        row1.addNewTableCell().setText("Vize");
        row1.addNewTableCell().setText("Final");
        row1.addNewTableCell().setText("Durum");

        String path = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\vya_proje_arayuz\\Dersler";
        File oku = new File(path);
        ArrayList<String> hepsi = new ArrayList<>();
        ArrayList<String> dersadi = new ArrayList<>();
        String ders = "";

        if(oku.isDirectory()){
            File[] tum = oku.listFiles();
            String no = ogr[0];
            for(int i = 0; i < tum.length; i++){
                Scanner s = new Scanner(tum[i]);
                int a = 0;
                while(s.hasNextLine()){
                    String str = s.nextLine();
                    if(a==0){
                        ders = str;
                        a++;
                    }
                    else{
                        if(str.contains(no)){
                            hepsi.add(str);
                            String[] d = ders.split("\\+");
                            dersadi.add(d[0]);
                        }
                    }
                }
            }
        }

        row1.setHeight(25);

        for(int i=0;i<hepsi.size();i++){
            XWPFTableRow row2 = tablo.createRow();
            row2.getCell(0).setText(dersadi.get(i));
            String [] ayir = hepsi.get(i).split(" ");
            row2.getCell(1).setText(ayir[1]);
            if(ayir.length>2) {//Satir boyutu ikiden buyuk ise yani final notu da sisteme girilmis ise bu bloga girilir.
                row2.getCell(2).setText(ayir[2]);
                if(Integer.parseInt(ayir[2])>35 && (Integer.parseInt(ayir[1])+Integer.parseInt(ayir[2]))/2>35){
                    //Final notu 35'ten buyuk ise ve vize ve final notlarinin ortalamasi 35'ten buyuk ise "gecti" yazdiriliyor.
                    row2.getCell(3).setText("Geçti");
                }
                else if(Integer.parseInt(ayir[2])<35 || (Integer.parseInt(ayir[1])+Integer.parseInt(ayir[2]))/2<35){
                    //Final notu 35'ten kucuk ise veya vize ve final ortalamasi 35'ten kucuk ise "kaldi" yazdiriliyor.
                    row2.getCell(3).setText("Kaldı");
                }
                else {
                    row2.getCell(3).setText("");
                }
            }
            else row2.getCell(2).setText("");

        }

        document.write(out);
        out.close();

        try {if (Desktop.isDesktopSupported()) {Desktop.getDesktop().open(new File(dosya.getAbsolutePath()));}}
        catch (IOException ioe) {ioe.printStackTrace();}
    }

    public void cikis(){
        System.exit(0);//Cikis yapilmak istendiginde sistem tamamen kapatiliyor.
    }
}

