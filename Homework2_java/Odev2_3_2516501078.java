package Homework2_java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

//For SSL error
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class Odev2_3_2516501078 { 

    // SSL BYPASS UTILITY 
    public static void bypassSSLSecurity() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { } 
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        } catch (Exception e) {
            System.out.println("Something is wrong:");
            e.printStackTrace();
        }
    }

    // JSON PARSER
    public static String findData(String text, String target) {
        String searchWord = "\"" + target + "\":";
        int startIndex = text.indexOf(searchWord);
        
        if (startIndex == -1) {
            return "NOT FOUND"; 
        }
        
        startIndex = startIndex + searchWord.length();
        int endIndex = text.indexOf(",", startIndex);
        
        if (endIndex == -1) {
            endIndex = text.indexOf("}", startIndex);
        }
        
        String rawResult = text.substring(startIndex, endIndex);
        String cleanResult = rawResult.replace("\"", "").trim();
        return cleanResult;
    }

    // OPTION 1: PIPELINE (FETCH & SAVE TO FILE)
    public static void runPipeline() {
        bypassSSLSecurity();
        System.out.println("\n[SYSTEM] Connecting to NeuroMorpho API, Please wait...");

        try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:%22basal%20ganglia%22&page=0&size=30";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000); 
            conn.setReadTimeout(15000); 
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            BufferedReader reader;

            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
                System.out.println("[SYSTEM] Connection established. Initiating data transfer...");
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("UTF-8")));
                System.out.println("[WARNING] Target server rejected the request.");
                return; 
            }
            
            StringBuilder responsePayload = new StringBuilder();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                responsePayload.append(currentLine);
            }

            if (responseCode >= 200 && responseCode < 300) {
                String rawData = responsePayload.toString();
                String[] neurons = rawData.split("\"neuron_id\":");
                System.out.println("\n--- INITIATING BACKGROUND SAVING PROCESS ---");

                FileWriter fileWriter = new FileWriter("noronlar.txt");
                fileWriter.write("--- BASAL GANGLIA NEURON DATA PIPELINE ---\n");
                fileWriter.write("==========================================\n");

                for (int i = 1; i < neurons.length; i++) {
                    String currentNeuronText = neurons[i];
                    
                    int commaIndex = currentNeuronText.indexOf(",");
                    String id = currentNeuronText.substring(0, commaIndex).replace("\"", "").trim();
                    
                    String surface = findData(currentNeuronText, "surface");
                    String volume = findData(currentNeuronText, "volume");

                    System.out.println("Processing Neuron ID: " + id + " (Saving to file...)");

                    if (!surface.equals("NOT FOUND") && !surface.equals("null") && 
                        !volume.equals("NOT FOUND") && !volume.equals("null")) {
                        
                        try {
                            double surfaceValue = Double.parseDouble(surface);
                            double volumeValue = Double.parseDouble(volume);
                            double synapticComplexityScore = surfaceValue / volumeValue;
                            
                            fileWriter.write("ID: " + id + " | Surface: " + surfaceValue + " | Volume: " + volumeValue + " | Score: " + synapticComplexityScore + "\n");

                        } catch (NumberFormatException e) {
                            fileWriter.write("ID: " + id + " | ERROR: Not suitable for numerical analysis.\n");
                        }
                    } else {
                        fileWriter.write("ID: " + id + " | ERROR: Insufficient morphological data.\n");
                    }
                }

                System.out.println("[SUCCESS] Calculations complete. All data saved to 'noronlar.txt'.");
            }
            
            if (reader != null) {  
            }

        } catch (Exception e) {
            System.out.println("[CRITICAL ERROR] Pipeline connection failed.");
            e.printStackTrace();
        }
    }

    // OPTION 2: FETCH & DISPLAY ON SCREEN
    public static void fetchAndDisplayLive() {
        bypassSSLSecurity();
        System.out.println("\n[SYSTEM] Connecting to NeuroMorpho API for Live Data...");

        try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:%22basal%20ganglia%22&page=0&size=30";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000); 
            conn.setReadTimeout(15000); 
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            BufferedReader reader;

            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
                System.out.println("[SYSTEM] Connection successful. Streaming data...\n");
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("UTF-8")));
                System.out.println("[1WARNING!] Target server rejected the request.");
                return; 
            }
            
            StringBuilder responsePayload = new StringBuilder();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                responsePayload.append(currentLine);
            }

            if (responseCode >= 200 && responseCode < 300) {
                String rawData = responsePayload.toString();
                String[] neurons = rawData.split("\"neuron_id\":");
                System.out.println("==========================================");
                System.out.println("             LIVE NEURON METRICS                  ");
                System.out.println("==========================================");

                for (int i = 1; i < neurons.length; i++) {
                    String currentNeuronText = neurons[i];
                    
                    int commaIndex = currentNeuronText.indexOf(",");
                    String id = currentNeuronText.substring(0, commaIndex).replace("\"", "").trim();
                    
                    String surface = findData(currentNeuronText, "surface");
                    String volume = findData(currentNeuronText, "volume");
                    String slicingThickness = findData(currentNeuronText, "slicing_thickness");

                    System.out.println("Neuron ID : " + id);
                    System.out.println("Surface   : " + surface);
                    System.out.println("Volume    : " + volume);
                    System.out.println("Thickness : " + slicingThickness);

                    if (!surface.equals("NOT FOUND") && !surface.equals("null") && 
                        !volume.equals("NOT FOUND") && !volume.equals("null")) {
                        
                        try {
                            double surfaceValue = Double.parseDouble(surface);
                            double volumeValue = Double.parseDouble(volume);
                            double synapticComplexityScore = surfaceValue / volumeValue;
                            
                            System.out.println("S.C. Score: " + synapticComplexityScore);

                        } catch (NumberFormatException e) {
                            System.out.println("S.C. Score: [DATA ERROR]");
                        }
                    } else {
                        System.out.println("S.C. Score: [INSUFFICIENT DATA]");
                    }
                    System.out.println("-------------------------");
                }
                System.out.println("[SUCCESS] Live streaming complete.");
            }
            if (reader != null) {
            }

        } catch (Exception e) {
            System.out.println("[CRITICAL ERROR] Connection failed.");
            e.printStackTrace();
        }
    }

    // OPTION 3: READ SAVED DATA
    public static void viewSavedData() {
        System.out.println("\n--- READING SAVED DATABASE (noronlar.txt) ---");
        try {
            File myObj = new File("noronlar.txt");
            if (myObj.exists()) {
                Scanner fileReader = new Scanner(myObj);
                while (fileReader.hasNextLine()) {
                    String data = fileReader.nextLine();
                    System.out.println(data);
                } 
                System.out.println("---------------------------------------------");
            } else {
                System.out.println("[WARNING] 'noronlar.txt' does not exist. Please run Option 1 first.");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void showSystemGuide() {
        System.out.println("\n=================================================");
        System.out.println("             SYSTEM GUIDE & INSTRUCTIONS          ");
        System.out.println("==================================================");
        System.out.println("[ABOUT THE PIPELINE]");
        System.out.println("This system automatically connects to the NeuroMorpho");
        System.out.println("database and extracts morphological data (Surface,");
        System.out.println("Volume) for 30 Basal Ganglia neurons.");
        System.out.println("\n[HOW TO USE]");
        System.out.println("- You do not need to enter complex API queries.");
        System.out.println("- Simply enter a number from the menu (1-5):");
        System.out.println("  [1] Secretly fetches and saves data to a txt file.");
        System.out.println("  [2] Fetches data and displays calculations live.");
        System.out.println("  [3] Reads the saved 'noronlar.txt' file.");
        System.out.println("==================================================");
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String choice = "";
        
        int mControl = 1; 

        while (mControl == 1) {
            System.out.println("\n==================================================");
            System.out.println("      NEUROMORPHO DATA PIPELINE - SYSTEM LOBBY    ");
            System.out.println("==================================================");
            System.out.println("[1] Fetch Data & Save to File (Background)");
            System.out.println("[2] Fetch Data & Display on Screen (Live View)");
            System.out.println("[3] View Saved Results (Read Text File)");
            System.out.println("[4] System Guide & Instructions");
            System.out.println("[5] Exit System");
            System.out.println("==================================================");
            System.out.print("Please select an operation (1-5): ");
            
            choice = inputScanner.nextLine();

            if (choice.equals("1")) {
                runPipeline();
            } 
            else if (choice.equals("2")) {
                fetchAndDisplayLive();
            }
            else if (choice.equals("3")) {
                viewSavedData();
            } 
            else if (choice.equals("4")) {
                showSystemGuide();
            } 
            else if (choice.equals("5")) {
                System.out.println("\n[SYSTEM] Shutting down... Goodbye!");
                mControl = 0; 
            } 
            else {
                System.out.println("\n[ERROR] Invalid selection. Please try again.");
            }
        }
        
    }
}