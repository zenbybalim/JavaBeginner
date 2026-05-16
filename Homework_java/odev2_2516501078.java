import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import javax.net.ssl.*;


//deneme14
public class odev2_2516501078 {

    static String dosyaAdi = "noronlar.txt";
    static Scanner scanner = new Scanner(System.in, "UTF-8");

    //  SSL SORUNU ÇÖZÜMÜ - NeuroMorpho.org eski sertifika kullanıyor
    //  Bu metot Java'nın SSL kontrolünü devre dışı bırakır

    public static String tekrarla(String metin, int miktar) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < miktar; i++) {
        sb.append(metin);
    }
    return sb.toString();
}
    static void sslSorunuCoz() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            logKaydet("SSL ayarı yapılamadı: " + e.getMessage());
        }
    }
    //  ANA MENÜ
    public static void main(String[] args) {
        sslSorunuCoz();

        System.out.println("-----------------------------------------------");
        System.out.println("      NÖRON ARAŞTIRMA KAYIT SİSTEMİ            ");
        System.out.println("      NeuroMorpho.Org Veri Tabanı              ");
        System.out.println("-----------------------------------------------");

        while (true) {
            System.out.println("\n------------- ANA MENÜ ----------------");
            System.out.println("  1. Veri Çek  (API'den indir)");
            System.out.println("  2. Listele");
            System.out.println("  3. Güncelle");
            System.out.println("  4. Sil");
            System.out.println("  5. Ara");
            System.out.println("  6. İstatistik");
            System.out.println("  7. Log Kayıtlarını Görüntüle");
            System.out.println("  8. Çıkış");
            System.out.println("-----------------------------------------");
            System.out.print("Lütfen seçim yapınız: ");

            String secimStr = scanner.nextLine().trim();

            switch (secimStr) {
                case "1": veriCek(); break;
                case "2": listeleMenu(); break;
                case "3": guncelle(); break;
                case "4": sil(); break;
                case "5": ara(); break;
                case "6": istatistik(); break;
                case "7": logGoruntule(); break;
                case "8":
                    System.out.println("\nProgramdan çıkılıyor... Güle güle!");
                    System.exit(0);
                default:
                    System.out.println("Geçersiz seçim! Lütfen 1-8 arası bir sayı girin.");
            }
        }
    }

    //  1. VERİ ÇEK
    //  Format: noronAdi;turAdi;beyin_bolgesi;hucre_tipi;laboratuvar
    static void veriCek() {
        System.out.println("\n--- VERİ ÇEKİLİYOR ---");
        System.out.println("NeuroMorpho.org'dan neocortex nöronları indiriliyor...");

        try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:neocortex&size=30&page=0";
            String jsonVeri = apidenVeriAl(apiUrl);

            if (jsonVeri == null || jsonVeri.isEmpty()) {
                System.out.println("HATA: API'den veri alınamadı!");
                logKaydet("VERİ ÇEKİLEMEDİ - API yanıt vermedi");
                return;
            }

            int embedBasla = jsonVeri.indexOf("\"neuronResources\":");
            if (embedBasla == -1) {
                System.out.println("HATA: Veri formatı beklenenden farklı!");
                logKaydet("VERİ FORMAT HATASI");
                return;
            }

            int diziBasla = jsonVeri.indexOf("[", embedBasla);
            int diziBitis = jsonVeri.lastIndexOf("]");

            if (diziBasla == -1 || diziBitis == -1) {
                System.out.println("HATA: Nöron listesi bulunamadı!");
                return;
            }

            String noronDizisi = jsonVeri.substring(diziBasla + 1, diziBitis);

            FileWriter fw = new FileWriter(dosyaAdi, false);
            BufferedWriter bw = new BufferedWriter(fw);

            int sayac = 0;
            int pos = 0;

            while (pos < noronDizisi.length() && sayac < 30) {
                int acilis = noronDizisi.indexOf("{", pos);
                if (acilis == -1) break;

                // İç içe {} bloklarını doğru kapanışa kadar bul
                int derinlik = 1;
                int i = acilis + 1;
                while (i < noronDizisi.length() && derinlik > 0) {
                    char c = noronDizisi.charAt(i);
                    if (c == '{') derinlik++;
                    else if (c == '}') derinlik--;
                    i++;
                }
                int kapanis = i - 1;

                String noronJson = noronDizisi.substring(acilis, kapanis + 1);

                // String metotlarıyla alanları çek
                String noronAdi    = jsonAlanAl(noronJson, "neuron_name");
                String tur         = jsonAlanAl(noronJson, "species");
                String beyin       = jsonDiziIlkElemanAl(noronJson, "brain_region");
                String hucreTipi   = jsonDiziIlkElemanAl(noronJson, "cell_type");
                String laboratuvar = jsonAlanAl(noronJson, "archive");

                if (noronAdi.isEmpty())    noronAdi    = "Bilinmiyor";
                if (tur.isEmpty())         tur         = "Bilinmiyor";
                if (beyin.isEmpty())       beyin       = "Bilinmiyor";
                if (hucreTipi.isEmpty())   hucreTipi   = "Bilinmiyor";
                if (laboratuvar.isEmpty()) laboratuvar = "Bilinmiyor";

                noronAdi    = noronAdi.replace(";", "-");
                tur         = tur.replace(";", "-");
                beyin       = beyin.replace(";", "-");
                hucreTipi   = hucreTipi.replace(";", "-");
                laboratuvar = laboratuvar.replace(";", "-");

                bw.write(noronAdi + ";" + tur + ";" + beyin + ";" + hucreTipi + ";" + laboratuvar);
                bw.newLine();
                sayac++;

                pos = kapanis + 1;
            }

            System.out.println("✓ " + sayac + " nöron kaydı başarıyla " + dosyaAdi + " dosyasına kaydedildi!");
            logKaydet("VERİ ÇEKİLDİ - " + sayac + " kayıt indirildi");

        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
            logKaydet("VERİ ÇEKME HATASI: " + e.getMessage());
        }
    }

    //  2. LİSTELE MENÜSÜ
    static void listeleMenu() {
        while (true) {
            System.out.println("\n══════ LİSTELEME MENÜSÜ ══════");
            System.out.println("  1. Türe göre listele");
            System.out.println("  2. Beyin bölgesine göre listele");
            System.out.println("  3. Hepsini listele");
            System.out.println("  4. Geri");
            System.out.print("Seçiminiz: ");

            String secim = scanner.nextLine().trim();

            switch (secim) {
                case "1": turGoreListele(); break;
                case "2": beyinBolgesineGoreListele(); break;
                case "3": hepsiniListele(); break;
                case "4": return;
                default: System.out.println("Geçersiz seçim!");
            }
        }
    }

    static void turGoreListele() {
        System.out.print("Tür adı girin (örn: rat, mouse, human): ");
        String turAdi = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n" + String.format("%-30s %-15s %-20s %-25s %-20s",
                "Nöron Adı", "Tür", "Beyin Bölgesi", "Hücre Tipi", "Laboratuvar"));
        String cizgiler = tekrarla("-", 115);

        int bulunan = 0;
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length >= 5 && p[1].toLowerCase().contains(turAdi)) {
                    System.out.println(String.format("%-30s %-15s %-20s %-25s %-20s",
                            p[0], p[1], p[2], p[3], p[4]));
                    bulunan++;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
            return;
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
        }

        if (bulunan == 0) System.out.println("'" + turAdi + "' türüne ait kayıt bulunamadı.");
        else System.out.println("\nToplam " + bulunan + " kayıt listelendi.");
    }

    static void beyinBolgesineGoreListele() {
        System.out.print("Beyin bölgesi girin (örn: neocortex, hippocampus): ");
        String bolge = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n" + String.format("%-30s %-15s %-20s %-25s %-20s",
                "Nöron Adı", "Tür", "Beyin Bölgesi", "Hücre Tipi", "Laboratuvar"));
        String cizgiler = tekrarla("-", 115);

        int bulunan = 0;
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length >= 5 && p[2].toLowerCase().contains(bolge)) {
                    System.out.println(String.format("%-30s %-15s %-20s %-25s %-20s",
                            p[0], p[1], p[2], p[3], p[4]));
                    bulunan++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
            return;
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
        }

        if (bulunan == 0) System.out.println("'" + bolge + "' bölgesine ait kayıt bulunamadı.");
        else System.out.println("\nToplam " + bulunan + " kayıt listelendi.");
    }

    static void hepsiniListele() {
        System.out.println("\n" + String.format("%-5s %-30s %-15s %-20s %-25s %-20s",
                "No", "Nöron Adı", "Tür", "Beyin Bölgesi", "Hücre Tipi", "Laboratuvar"));
          String cizgiler = tekrarla("-", 120);

        int no = 1;
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length >= 5) {
                    System.out.println(String.format("%-5d %-30s %-15s %-20s %-25s %-20s",
                            no++, p[0], p[1], p[2], p[3], p[4]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
            return;
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
        }

        cizgiler = tekrarla("-", 120);
        System.out.println("Toplam " + (no - 1) + " kayıt.");
    }

    //  3. GÜNCELLE
    static void guncelle() {
        System.out.println("\n══════ GÜNCELLE ══════");
        System.out.print("Güncellemek istediğiniz nöronun adını girin: ");
        String aranan = scanner.nextLine().trim();

        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            StringBuilder tumIcerik = new StringBuilder();
            String satir;
            String bulunanSatir = null;
            while ((satir = br.readLine()) != null) {
                tumIcerik.append(satir).append("\n");
                String[] p = satir.split(";");
                if (p.length >= 1 && p[0].toLowerCase().contains(aranan.toLowerCase())) {
                    bulunanSatir = satir;
                }
            }

            if (bulunanSatir == null) {
                System.out.println("Kayıt bulunamadı!");
                return;
            }
////////////////////////TEKRARYAP
          //

            System.out.println("✓ Kayıt başarıyla güncellendi!");
            logKaydet("GÜNCELLEME - Eski: " + bulunanSatir + " | Yeni: " + yeniSatir);

        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
            logKaydet("GÜNCELLEME HATASI: " + e.getMessage());
        }
    }
    //  4. SİL
    static void sil() {
        System.out.println("\n══════ SİL ══════");
        System.out.print("Silmek istediğiniz nöronun adını girin: ");
        String aranan = scanner.nextLine().trim();

        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            StringBuilder tumIcerik = new StringBuilder();
            String satir;
            String bulunanSatir = null;
            while ((satir = br.readLine()) != null) {
                tumIcerik.append(satir).append("\n");
                String[] p = satir.split(";");
                if (p.length >= 1 && p[0].toLowerCase().contains(aranan.toLowerCase())) {
                    bulunanSatir = satir;
                }
            

            FileWriter fw = new FileWriter(dosyaAdi, false);
            fw.write(yeniIcerik);

            System.out.println("✓ Kayıt başarıyla silindi!");
            logKaydet("SİLME - Silinen kayıt: " + bulunanSatir);

        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
            logKaydet("SİLME HATASI: " + e.getMessage());
        }
    }
    //  5. ARA  
   /////////////////////////////////////////////////////////////////////7
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if ???????????????????? //imdat.