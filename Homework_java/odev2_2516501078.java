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

    // ----------------------------------------------------------------
    //  1. VERİ ÇEK
    //  API'den nöron verilerini çekip dosyaya kaydeder
    //  Format: noronAdi;turAdi;beyin_bolgesi;hucre_tipi;laboratuvar
    // ----------------------------------------------------------------
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

            String[] parcalar = bulunanSatir.split(";");
            System.out.println("\nBulunan kayıt:");
            System.out.println("  Ad           : " + parcalar[0]);
            System.out.println("  Tür          : " + parcalar[1]);
            System.out.println("  Beyin Bölgesi: " + parcalar[2]);
            System.out.println("  Hücre Tipi   : " + parcalar[3]);
            System.out.println("  Laboratuvar  : " + parcalar[4]);
            System.out.print("\nBu kaydı güncellemek istiyor musunuz? (e/h): ");
            String onay = scanner.nextLine().trim();

            if (!onay.equalsIgnoreCase("e")) {
                System.out.println("Güncelleme iptal edildi.");
                return;
            }

            System.out.println("\nYeni değerleri girin (boş bırakırsanız mevcut değer korunur):");

            System.out.print("Yeni nöron adı [" + parcalar[0] + "]: ");
            String yeniAd = scanner.nextLine().trim();
            if (yeniAd.isEmpty()) yeniAd = parcalar[0];

            System.out.print("Yeni tür [" + parcalar[1] + "]: ");
            String yeniTur = scanner.nextLine().trim();
            if (yeniTur.isEmpty()) yeniTur = parcalar[1];

            System.out.print("Yeni beyin bölgesi [" + parcalar[2] + "]: ");
            String yeniBolge = scanner.nextLine().trim();
            if (yeniBolge.isEmpty()) yeniBolge = parcalar[2];

            System.out.print("Yeni hücre tipi [" + parcalar[3] + "]: ");
            String yeniHucre = scanner.nextLine().trim();
            if (yeniHucre.isEmpty()) yeniHucre = parcalar[3];

            System.out.print("Yeni laboratuvar [" + parcalar[4] + "]: ");
            String yeniLab = scanner.nextLine().trim();
            if (yeniLab.isEmpty()) yeniLab = parcalar[4];

            String yeniSatir = yeniAd + ";" + yeniTur + ";" + yeniBolge + ";" + yeniHucre + ";" + yeniLab;
            String yeniIcerik = tumIcerik.toString().replace(bulunanSatir, yeniSatir);

            FileWriter fw = new FileWriter(dosyaAdi, false);
            fw.write(yeniIcerik);

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
            }

            if (bulunanSatir == null) {
                System.out.println("Kayıt bulunamadı!");
                return;
            }

            String[] parcalar = bulunanSatir.split(";");
            System.out.println("\nBulunan kayıt:");
            System.out.println("  Ad: " + parcalar[0] + " | Tür: " + parcalar[1] +
                    " | Bölge: " + parcalar[2]);
            System.out.print("Silmek istediğiniz kayıt bu mu? (e/h): ");
            String onay = scanner.nextLine().trim();

            if (!onay.equalsIgnoreCase("e")) {
                System.out.println("Silme işlemi iptal edildi.");
                return;
            }

            String yeniIcerik = tumIcerik.toString().replace(bulunanSatir + "\n", "");

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
    static void ara() {
        System.out.println("\n══════ ARA ══════");
        System.out.print("Aramak istediğiniz nöron adını girin: ");
        String aranan = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n" + String.format("%-30s %-15s %-20s %-25s %-20s",
                "Nöron Adı", "Tür", "Beyin Bölgesi", "Hücre Tipi", "Laboratuvar"));
          String cizgiler = tekrarla("-", 120);

        int bulunan = 0;
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length >= 5 && p[0].toLowerCase().contains(aranan)) {
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

        if (bulunan == 0) System.out.println("'" + aranan + "' için sonuç bulunamadı.");
        else System.out.println("\nToplam " + bulunan + " sonuç bulundu.");
    }
    //  6. İSTATİSTİK  
    static void istatistik() {
        System.out.println("\n══════ İSTATİSTİK ══════");

        int toplamKayit = 0;
        int ratSayisi = 0, mouseSayisi = 0, humanSayisi = 0, digerTurSayisi = 0;
        int neocortexSayisi = 0, hippocampusSayisi = 0, digerBolgeSayisi = 0;

        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dosyaAdi), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length >= 5) {
                    toplamKayit++;
                    String tur = p[1].toLowerCase();
                    if (tur.contains("rat"))        ratSayisi++;
                    else if (tur.contains("mouse")) mouseSayisi++;
                    else if (tur.contains("human")) humanSayisi++;
                    else                            digerTurSayisi++;

                    String bolge = p[2].toLowerCase();
                    if (bolge.contains("neocortex"))        neocortexSayisi++;
                    else if (bolge.contains("hippocampus")) hippocampusSayisi++;
                    else                                    digerBolgeSayisi++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı! Önce veri çekin (Menü 1).");
            return;
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
        }

        System.out.println("\n  Toplam Kayıt Sayısı  : " + toplamKayit);
        System.out.println("\n  [ TÜRE GÖRE DAĞILIM ]");
        System.out.println("  Rat (Sıçan)          : " + ratSayisi);
        System.out.println("  Mouse (Fare)         : " + mouseSayisi);
        System.out.println("  Human (İnsan)        : " + humanSayisi);
        System.out.println("  Diğer                : " + digerTurSayisi);
        System.out.println("\n  [ BEYİN BÖLGESİ DAĞILIMI ]");
        System.out.println("  Neocortex            : " + neocortexSayisi);
        System.out.println("  Hippocampus          : " + hippocampusSayisi);
        System.out.println("  Diğer                : " + digerBolgeSayisi);
    }
    //  7. LOG KAYITLARINI GÖRÜNTÜLE  
    static void logGoruntule() {
        System.out.println("\n══════ LOG KAYITLARI ══════");
        try {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("log.txt"), StandardCharsets.UTF_8));
            String satir;
            int sayac = 0;
            while ((satir = br.readLine()) != null) {
                System.out.println(satir);
                sayac++;
            }
            if (sayac == 0) System.out.println("Henüz log kaydı yok.");
        } catch (FileNotFoundException e) {
            System.out.println("Henüz log kaydı oluşmamış.");
        } catch (Exception e) {
            System.out.println("HATA: " + e.getMessage());
        }
    }
    static String apidenVeriAl(String urlAdresi) {
        StringBuilder sonuc = new StringBuilder();
        try {
            URL url = new URL(urlAdresi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                logKaydet("API HATA KODU: " + responseCode);
                return null;
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String satir;
            while ((satir = br.readLine()) != null) {
                sonuc.append(satir);
            }
            conn.disconnect();

        } catch (Exception e) {
            logKaydet("API BAĞLANTI HATASI: " + e.getMessage());
            return null;
        }
        return sonuc.toString();
    }

    // JSON'dan tek değerli bir alanı String metotlarıyla çeker
    // Örnek: "neuron_name":"cnic_001"  →  "cnic_001" döndürür
    static String jsonAlanAl(String json, String alan) {
        String arama = "\"" + alan + "\":\"";
        int basla = json.indexOf(arama);
        if (basla == -1) return "";
        basla += arama.length();
        int bitis = json.indexOf("\"", basla);
        if (bitis == -1) return "";
        return json.substring(basla, bitis).trim();
    }

    // JSON dizisinin ilk elemanını String metotlarıyla çeker
    // Örnek: "brain_region":["neocortex","layer 3"]  →  "neocortex" döndürür
    static String jsonDiziIlkElemanAl(String json, String alan) {
        String arama = "\"" + alan + "\":[";
        int basla = json.indexOf(arama);
        if (basla == -1) return "";
        basla += arama.length();
        int tirnak1 = json.indexOf("\"", basla);
        if (tirnak1 == -1) return "";
        tirnak1++;
        int tirnak2 = json.indexOf("\"", tirnak1);
        if (tirnak2 == -1) return "";
        return json.substring(tirnak1, tirnak2).trim();
    }

    // Hata ve işlem loglarını log.txt dosyasına kaydeder
    static void logKaydet(String mesaj) {
        try {
            FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            long zaman = System.currentTimeMillis();
            bw.write("[" + zaman + "] " + mesaj);
            bw.newLine();
        } catch (Exception ignored) {}
    }
}