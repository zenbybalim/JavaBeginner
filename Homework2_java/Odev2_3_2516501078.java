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
// Author: Elif Balım Çetin [Zen]

public class Odev2_3_2516501078 { 

    public static void main(String[] args) {
        
        // 1. Bypass SSL certificate issues for academic API
        bypassSSLSecurity();

        System.out.println("Connecting to NeuroMorpho API... Please wait.");

        try {
            // Target URL for Basal Ganglia neurons
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:basal%20ganglia&size=30";

            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String currentLine;
            
            // StringBuilder to hold the massive JSON string
            StringBuilder responsePayload = new StringBuilder();

            while ((currentLine = reader.readLine()) != null) {
                responsePayload.append(currentLine);
            }
            reader.close(); 

            System.out.println("Connection Successful! Raw JSON Payload:");
            System.out.println("---------------------------------------------------");
            System.out.println(responsePayload.toString());

        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =====================================================================
    // SSL BYPASS UTILITY (To prevent Handshake Exceptions)
    // =====================================================================
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
            e.printStackTrace();
        }
    }
}