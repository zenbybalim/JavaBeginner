import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

// SSL bypass icin (hoca izin verdi)
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class Odev2_2_2516501078 {

    static String dosyaAdi    = "noronlar.txt";
    static String logDosyaAdi = "experiment_log.txt";

    // NeuroMorpho.org - Basal Ganglia noronlari
    static String apiURL = "https://neuromorpho.org/api/neuron/select?q=brain_region:basal%20ganglia&size=50&page=0";

    static Scanner giris = new Scanner(System.in);

    public static void main(String[] args) {
        int secim = 0;

        do {
            System.out.println("=== Bazal Ganglia Noron Veritabani ===");
            System.out.println("[1] Veri Cek (API -> noronlar.txt)");
            System.out.println("[2] Listele");
            System.out.println("[3] Guncelle");
            System.out.println("[4] Sil");
            System.out.println("[5] Cikis");
            System.out.print("Secim Yapiniz: ");
            secim = giris.nextInt();
            giris.nextLine();

            if (secim == 1) {
                veriCek();
            } else if (secim == 2) {
                listeAltMenu();
            } else if (secim == 3) {
                guncelle();
            } else if (secim == 4) {
                sil();
            }

        } while (secim != 5);

        System.out.println("Program sonlandiriliyor...");
    }

    // MENU 1: VERI CEK
    public static void veriCek() {
        System.out.println("API'ye baglaniliyor...");

        try {
            TrustManager[] trustHepsi = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] c, String a) {}
                    public void checkServerTrusted(X509Certificate[] c, String a) {}
                }
            };
            SSLContext sslBaglam = SSLContext.getInstance("TLS");
            sslBaglam.init(null, trustHepsi, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslBaglam.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            URI uri = URI.create(apiURL);
            URL url = uri.toURL();
            HttpURLConnection baglanti = (HttpURLConnection) url.openConnection();
            baglanti.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(baglanti.getInputStream()));
            String satir;
            String veri = "";
            while ((satir = br.readLine()) != null) {
                veri += satir;
            }

            FileWriter fw = new FileWriter(dosyaAdi, false);
            fw.write("NeuronID ; NeuronName ; Species ; BrainRegion ; CellType ; SynapticComplexityScore\n");

            String[] noronlar = veri.split("\\{\"neuron_id\"");

            int yazilan = 0;

            for (int i = 1; i < noronlar.length; i++) {
                String parca = noronlar[i];

                String id    = parcaGetirSayi(parca, "\"neuron_id\":");
                String isim  = parcaGetirStr(parca, "\"neuron_name\":\"");
                String tur   = parcaGetirStr(parca, "\"species\":\"");
                String bolge = parcaGetirStr(parca, "\"brain_region\":\"");
                String tip   = parcaGetirStr(parca, "\"cell_type\":\"");
                String yuzey = parcaGetirSayi(parca, "\"soma_surface\":");
                String stem  = parcaGetirSayi(parca, "\"n_stems\":");


                if (id == null || isim == null || tur == null) {
                    logYaz("UYARI - Eksik veri: id=" + id + " isim=" + isim + " tur=" + tur);
                }


                if (id    == null) id    = "N/A";
                if (isim  == null) isim  = "N/A";
                if (tur   == null) tur   = "N/A";
                if (bolge == null) bolge = "N/A";
                if (tip   == null) tip   = "N/A";


                String scs = "N/A";
                if (yuzey != null && stem != null) {
                    try {
                        double uzunluk = Double.parseDouble(yuzey);
                        double dal     = Double.parseDouble(stem);
                        double skor    = uzunluk * dal / 100.0;
                        skor = Math.round(skor * 100.0) / 100.0;
                        scs  = String.valueOf(skor);
                    } catch (NumberFormatException e) {
                        logYaz("UYARI - SCS hesaplanamadi: id=" + id);
                    }
                }


                isim  = isim.replace(";", " ");
                tur   = tur.replace(";", " ");
                bolge = bolge.replace(";", " ");
                tip   = tip.replace(";", " ");

                fw.write(id + " ; " + isim + " ; " + tur + " ; " + bolge + " ; " + tip + " ; " + scs + "\n");
                yazilan++;
            }


            System.out.println(yazilan + " noron noronlar.txt dosyasina yazildi.");

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
            logYaz("HATA - API baglantisi: " + e.getMessage());
        }
    }

    // MENU 2: LISTELE (alt menu)
    public static void listeAltMenu() {
        int secim2 = 0;

        do {
            System.out.println("--- Listele ---");
            System.out.println("[1] Ture gore listele");
            System.out.println("[2] Hucre Tipine gore listele");
            System.out.println("[3] Tum noronlari listele");
            System.out.println("[4] Ust menuye don");
            System.out.print("Secim: ");
            secim2 = giris.nextInt();
            giris.nextLine();

            if (secim2 == 1) {
                System.out.print("Tur giriniz (orn: rat, mouse): ");
                String aranenTur = giris.nextLine();
                listele(2, aranenTur); // kolon 2 = Species
            } else if (secim2 == 2) {
                System.out.print("Hucre tipi giriniz: ");
                String aranenTip = giris.nextLine();
                listele(4, aranenTip); // kolon 4 = CellType
            } else if (secim2 == 3) {
                listele(-1, ""); // tumu
            }

        } while (secim2 != 4);
    }

    public static void listele(int kolon, String deger) {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            System.out.println("noronlar.txt bulunamadi! Once Veri Cek yapiniz.");
            return;
        }

        try {
            Path filePath = dosya.toPath();
            List<String> satirlar = Files.readAllLines(filePath, Charset.defaultCharset());

            System.out.println("------------------------------------------------------------");
            int sayac = 0;

            for (int i = 0; i < satirlar.size(); i++) {
                if (i == 0) continue; // baslik satiri

                String[] parcalar = satirlar.get(i).split(" ; ");
                if (parcalar.length < 5) continue;

                int yazdir = 1;
                if (kolon != -1) {
                    if (!parcalar[kolon].trim().toLowerCase().contains(deger.toLowerCase())) {
                        yazdir = 0;
                    }
                }

                if (yazdir == 1) {
                    System.out.println("ID    : " + parcalar[0].trim());
                    System.out.println("Isim  : " + parcalar[1].trim());
                    System.out.println("Tur   : " + parcalar[2].trim());
                    System.out.println("Bolge : " + parcalar[3].trim());
                    System.out.println("Tip   : " + parcalar[4].trim());
                    System.out.println("SCS   : " + (parcalar.length > 5 ? parcalar[5].trim() : "N/A"));
                    System.out.println("------------------------------------------------------------");
                    sayac++;
                }
            }
            System.out.println("Toplam: " + sayac + " kayit.");

        } catch (IOException e) {
            System.out.println("Dosya okunamadi: " + e.getMessage());
        }
    }

    // MENU 3: GUNCELLE!!!!!!
    public static void guncelle() {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            System.out.println("noronlar.txt bulunamadi! Once Veri Cek yapiniz.");
            return;
        }

        try {
            Path filePath = dosya.toPath();
            List<String> satirlar = Files.readAllLines(filePath, Charset.defaultCharset());

            System.out.print("Guncellenecek NeuronID: ");
            String arananId = giris.nextLine().trim();

            String cevap;
            for //for   ??????????????????????????????????OFFFFFFFFFFFFFFF 