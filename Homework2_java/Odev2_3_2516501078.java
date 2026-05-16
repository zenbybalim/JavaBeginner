package Homework2_java;

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
import java.net.URLEncoder;

//For SSL error
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

//Computational Neuroscience Data Pipeline
//Focus: Basal Ganglia Morphological Analysis
//By: Elif Balım Çetin [Zen]

public class Odev2_3_2516501078 { 

    public static void main(String[] args) {
        
        // 1. Bypass SSL certificate issues for academic API
        bypassSSLSecurity();

        System.out.println("Connecting to NeuroMorpho API, Please wait.");

                try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:%22basal%20ganglia%22&page=0&size=30";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println("wwaiting server response...");
            int responseCode = conn.getResponseCode();
            System.out.println("Server Response Code: " + responseCode);

            BufferedReader reader;

            if (responseCode >= 200 && responseCode < 300) {
                
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
                System.out.println("Connection established successfully. Initiating data transfer...");
            } else {
               
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("UTF-8")));
                System.out.println("WARNING: Target server rejected the request. Analyzing error stream...");
            }
            
           
            StringBuilder responsePayload = new StringBuilder();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                responsePayload.append(currentLine);
            }
            reader.close();

            if (responseCode >= 200 && responseCode < 300) {
                System.out.println("Data transfer complete. Payload size: " + responsePayload.length() + " characters.");
                
                String rawData = responsePayload.toString();
                String[] neuronChunks = rawData.split("\"neuron_id\":");
                System.out.println("Estimated neuron chunks extracted: " + (neuronChunks.length - 1));
            } else {
                System.out.println("Server Error Details: \n" + responsePayload.toString());
            }

        } catch (Exception e) {
            System.out.println("CRITICAL ERROR: Pipeline connection failed.");
            System.out.println("Exception details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =====================================================
    // SSL BYPASS UTILITY (To prevent Handshake Exceptions)
    // =====================================================
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
}