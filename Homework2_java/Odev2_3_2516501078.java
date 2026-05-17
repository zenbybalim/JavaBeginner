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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class Odev2_3_2516501078 { 

    static Scanner commandScanner = new Scanner(System.in);

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

    public static String findData(String text, String target) {
        String targetKeyFormat = "\"" + target + "\":";
        int startIndex = text.indexOf(targetKeyFormat);
        
        if (startIndex == -1) {
            return "NOT FOUND!"; 
        }
        
        startIndex = startIndex + targetKeyFormat.length();
        int endIndex = text.indexOf(",", startIndex);
        
        if (endIndex == -1) {
            endIndex = text.indexOf("}", startIndex);
        }
        
        String rawResult = text.substring(startIndex, endIndex);
        String cleanResult = rawResult.replace("\"", "").trim();
        return cleanResult;
    }

    public static void runPipeline() {
        bypassSSLSecurity();
        System.out.println("\n Connecting to NeuroMorpho API, wait...");

        try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:%22basal%20ganglia%22&page=0&size=30";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000); 
            conn.setReadTimeout(90000); 
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");

            int serverStatusCode = conn.getResponseCode();
            BufferedReader reader;

            if (serverStatusCode >= 200 && serverStatusCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
                System.out.println("Connection established. Initiating data transfer...");
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("UTF-8")));
                System.out.println(" Target server rejected the request.");
                return; 
            }
            
            String dataStreamBuffer = "";
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                dataStreamBuffer = dataStreamBuffer + currentLine;
            }

            if (serverStatusCode >= 200 && serverStatusCode < 300) {
                String rawData = dataStreamBuffer;
                String[] neurons = rawData.split("\"neuron_id\":");
                System.out.println("\n--- INITIATING BACKGROUND SAVING PROCESS ---");

                FileWriter fileWriter = new FileWriter("noronlar.txt");
                fileWriter.write("ID;Surface;Volume;Score\n");

                for (int i = 1; i < neurons.length; i++) {
                    String currentNeuronText = neurons[i];
                    
                    int commaIndex = currentNeuronText.indexOf(",");
                    String id = currentNeuronText.substring(0, commaIndex).replace("\"", "").trim();
                    
                    String surface = findData(currentNeuronText, "surface");
                    String volume = findData(currentNeuronText, "volume");

                    System.out.println("Processing Neuron ID: " + id + " (Saving to file...)");

                    if (!surface.equals("NOT FOUND") && !surface.equals("null") && 
                        !volume.equals("NOT FOUND") && !volume.equals("null") &&
                        !surface.equals("NOT FOUND!") && !volume.equals("NOT FOUND!")) {
                        
                        try {
                            double surfaceValue = Double.parseDouble(surface);
                            double volumeValue = Double.parseDouble(volume);
                            double synapticComplexityScore = surfaceValue / volumeValue;
                            
                            fileWriter.write(id + ";" + surfaceValue + ";" + volumeValue + ";" + synapticComplexityScore + "\n");

                        } catch (NumberFormatException e) {
                            fileWriter.write(id + ";error;error;error\n");
                        }
                    } else {
                        fileWriter.write(id + ";error;error;error\n");
                    }
                }

                fileWriter.flush();
                System.out.println(" Calculations complete. All data saved to 'noronlar.txt'.");
            }

        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    public static void fetchAndDisplayLive() {
        bypassSSLSecurity();
        System.out.println("\nConnecting to NeuroMorpho API for Live Data...");

        try {
            String apiUrl = "https://neuromorpho.org/api/neuron/select?q=brain_region:%22basal%20ganglia%22&page=0&size=30";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000); 
            conn.setReadTimeout(90000);    
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");

            int serverStatusCode = conn.getResponseCode();
            BufferedReader reader;

            if (serverStatusCode >= 200 && serverStatusCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
                System.out.println(" Connection successful. Streaming data...\n");
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("UTF-8")));
                System.out.println(" Target server rejected the request.");
                return; 
            }
            
            String dataStreamBuffer = "";
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                dataStreamBuffer = dataStreamBuffer + currentLine;
            }

            if (serverStatusCode >= 200 && serverStatusCode < 300) {
                String rawData = dataStreamBuffer;
                String[] neurons = rawData.split("\"neuron_id\":");
                System.out.println("=");
                System.out.println("LIVE NEURON METRICS");
                System.out.println("=");

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
                        !volume.equals("NOT FOUND") && !volume.equals("null") &&
                        !surface.equals("NOT FOUND!") && !volume.equals("NOT FOUND!")) {
                        
                        try {
                            double surfaceValue = Double.parseDouble(surface);
                            double volumeValue = Double.parseDouble(volume);
                            double synapticComplexityScore = surfaceValue / volumeValue;
                            
                            System.out.println("S.C. Score: " + synapticComplexityScore);

                        } catch (NumberFormatException e) {
                            System.out.println("S.C. Score: Error :(");
                        }
                    } else {
                        System.out.println("S.C. Score: Insufficient Data :/");
                    }
                    System.out.println("-------------------------");
                }
                System.out.println(" Live streaming complete.");
            }
            
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

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
                System.out.println("-");
            } else {
                System.out.println("!!! 'noronlar.txt' does not exist. Please run Option 1 first.");
            }
        } catch (Exception e) {
            System.out.println(" An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void modifySavedData() {
        System.out.println("\n--- MODIFY SAVED DATABASE ---");
        
        try {
            File myObj = new File("noronlar.txt");
            if (!myObj.exists()) {
                System.out.println("!!! 'noronlar.txt' does not exist. Please fetch data first.");
                return;
            }

            String[] fileLines = new String[100]; 
            int lineCount = 0;
            
            Scanner fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                fileLines[lineCount] = fileReader.nextLine();
                lineCount++;
            }

            System.out.print("Enter the Neuron ID you want to modify: ");
            String targetNeuronId = commandScanner.nextLine().trim();
            
            int foundIndex = -1;
            
            for (int i = 0; i < lineCount; i++) {
                if (fileLines[i] != null && fileLines[i].startsWith(targetNeuronId + ";")) {
                    foundIndex = i;
                    i = lineCount; 
                }
            }

            if (foundIndex != -1) {
                System.out.println("\n Record Foundd: " + fileLines[foundIndex]);
                
                System.out.print("Do you want to change this data? |Press 1 for Yes, 0 for No|: ");
                String confirmCommand = commandScanner.nextLine().trim();
                
                if (confirmCommand.equals("1")) {
                    System.out.print("new Surface value: ");
                    String newSurfaceStr = commandScanner.nextLine().trim();
                    
                    System.out.print("new Volume value: ");
                    String newVolumeStr = commandScanner.nextLine().trim();
                    
                    try {
                        double newSurf = Double.parseDouble(newSurfaceStr);
                        double newVol = Double.parseDouble(newVolumeStr);
                        double newScore = newSurf / newVol;
                        
                        fileLines[foundIndex] = targetNeuronId + ";" + newSurf + ";" + newVol + ";" + newScore;
                        
                        FileWriter fileWriter = new FileWriter("noronlar.txt");
                        for (int i = 0; i < lineCount; i++) {
                            if(fileLines[i] != null) fileWriter.write(fileLines[i] + "\n");
                        }
                        fileWriter.flush();
                        
                        System.out.println("\nNeuron data updated and score recalculated successfully!");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid number format. Update cancelled.");
                    }
                } else {
                    System.out.println("\n Modification cancelled by user.");
                }
            } else {
                System.out.println("\n Neuron ID '" + targetNeuronId + "' not found in the database.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while modifying the file.");
            e.printStackTrace();
        }
    }

    public static void compareSavedData() {
        System.out.println("\n--- COMPARE & RANK NEURONS ---");

        try {
            File myObj = new File("noronlar.txt");
            if (!myObj.exists()) {
                System.out.println("!! 'noronlar.txt' does not exist. Please fetch data first.");
                return;
            }

            String[] fileLines = new String[100];
            int lineCount = 0;
            Scanner fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                fileLines[lineCount] = fileReader.nextLine();
                lineCount++;
            }

            System.out.println("Enter the Neuron IDs you want to compare separated by space (like, 100001 100002).");
            System.out.print("Or type 'ALL' to see the global ranking of all saved neurons: ");
            String userInput = commandScanner.nextLine().trim();

            String[] compareIds = new String[100];
            double[] compareScores = new double[100];
            int compareCount = 0;

            if (userInput.equalsIgnoreCase("ALL")) {
                for (int i = 0; i < lineCount; i++) {
                    if (fileLines[i] != null && fileLines[i].contains(";") && !fileLines[i].startsWith("ID;")) {
                        String[] parts = fileLines[i].split(";");
                        
                        if (parts.length >= 4) {
                            String extractedId = parts[0].trim();
                            String scoreStr = parts[3].trim();

                            if (!scoreStr.equalsIgnoreCase("error")) {
                                compareIds[compareCount] = extractedId;
                                compareScores[compareCount] = Double.parseDouble(scoreStr);
                                compareCount++;
                            }
                        }
                    }
                }
            } else {
                String[] requestedIds = userInput.split(" ");
                for (int i = 0; i < requestedIds.length; i++) {
                    String reqId = requestedIds[i].trim();
                    if (reqId.length() > 0) {
                        
                        int foundIndex = -1;
                        for (int j = 0; j < lineCount; j++) {
                            if (fileLines[j] != null && fileLines[j].startsWith(reqId + ";")) {
                                foundIndex = j;
                                j = lineCount; 
                            }
                        }
                        
                        if (foundIndex != -1) {
                            String[] parts = fileLines[foundIndex].split(";");
                            
                            if (parts.length >= 4) {
                                String scoreStr = parts[3].trim();
                                
                                if (!scoreStr.equalsIgnoreCase("error")) {
                                    compareIds[compareCount] = reqId;
                                    compareScores[compareCount] = Double.parseDouble(scoreStr);
                                    compareCount++;
                                } else {
                                    System.out.println("! Neuron " + reqId + " has an error score. Skipping.");
                                }
                            }
                        } else {
                            System.out.println("! Neuron ID '" + reqId + "' not found.");
                        }
                    }
                }
            }

            if (compareCount == 0) {
                System.out.println(" No valid data found to compare.");
                return;
            }

            for (int i = 0; i < compareCount - 1; i++) {
                for (int j = 0; j < compareCount - i - 1; j++) {
                    if (compareScores[j] < compareScores[j + 1]) {
                        
                        double tempScore = compareScores[j];
                        compareScores[j] = compareScores[j + 1];
                        compareScores[j + 1] = tempScore;

                        String tempId = compareIds[j];
                        compareIds[j] = compareIds[j + 1];
                        compareIds[j + 1] = tempId;
                    }
                }
            }

            System.out.println("\n=");
            System.out.println("SYNAPTIC COMPLEXITY RANKING (HIGH TO LOW) ");
            System.out.println("=");
            for (int i = 0; i < compareCount; i++) {
                System.out.println("Rank " + (i + 1) + " | Neuron ID: " + compareIds[i] + " | Score: " + compareScores[i]);
            }
            System.out.println("=");

        } catch (Exception e) {
            System.out.println("[ERROR] An error occurred while comparing data.");
            e.printStackTrace();
        }
    }

    public static void deleteSavedData() {
        System.out.println("\n--- DELETE SAVED DATABASE ---");
        
        try {
            File myObj = new File("noronlar.txt");
            if (!myObj.exists()) {
                System.out.println("!!! 'noronlar.txt' does not exist. Please fetch data first.");
                return;
            }

            String[] fileLines = new String[100]; 
            int lineCount = 0;
            
            Scanner fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                fileLines[lineCount] = fileReader.nextLine();
                lineCount++;
            }

            System.out.print("Enter the Neuron ID you want to DELETE: ");
            String targetNeuronId = commandScanner.nextLine().trim();
            
            int foundIndex = -1;
            
            for (int i = 0; i < lineCount; i++) {
                if (fileLines[i] != null && fileLines[i].startsWith(targetNeuronId + ";")) {
                    foundIndex = i;
                    i = lineCount; 
                }
            }

            if (foundIndex != -1) {
                System.out.println("\n Record Foundd: " + fileLines[foundIndex]);
                
                System.out.print("Are you sure you want to DELETE this data? |Press 1 for Yes, 0 for No|: ");
                
                String deletionConfirmCommand = commandScanner.nextLine().trim();
                
                if (deletionConfirmCommand.equals("1")) {
                    
                    FileWriter fileWriter = new FileWriter("noronlar.txt");
                    for (int i = 0; i < lineCount; i++) {
                        if(fileLines[i] != null && i != foundIndex) {
                            fileWriter.write(fileLines[i] + "\n");
                        }
                    }
                    fileWriter.flush();
                    
                    System.out.println("\n Neuron data DELETED successfully! Bye bye data.");
                } else {
                    System.out.println("\n Deletion cancelled by user.");
                }
            } else {
                System.out.println("\n Neuron ID '" + targetNeuronId + "' not found in the database.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while deleting the data.");
            e.printStackTrace();
        }
    }

    public static void showSystemGuide() {
        System.out.println("\n=");
        System.out.println("SYSTEM GUIDE & INSTRUCTIONS");
        System.out.println("=");
        System.out.println("|+| About Systemm");
        System.out.println("This system automatically connects to the NeuroMorpho");
        System.out.println("database and extracts morphological data (Surface,");
        System.out.println("Volume) for 30 Basal Ganglia neurons.");
        System.out.println("\n|+| HOW TO USE?");
        System.out.println("- Enter a number from the menu |1-8|:");
        System.out.println("  |1| Secretly fetches and saves data to a txt file.");
        System.out.println("  |2| Fetches data and displays calculations live.");
        System.out.println("  |3| Reads the saved 'noronlar.txt' file.");
        System.out.println("  |4| Interactively modifies existing neuron metrics.");
        System.out.println("  |5| Compares and ranks neurons by Complexity Score.");
        System.out.println("  |6| Deletes a specific neuron from the database.");
        System.out.println("=");
    }

    public static void main(String[] args) {
        String choice = "";
        int mControl = 1; 

        while (mControl == 1) {
            System.out.println("\n=");
            System.out.println("NEUROMORPHO DATA - SYSTEM LOBBY    ");
            System.out.println("=");
            System.out.println("|1| Fetch Data & Save to File (Background)");
            System.out.println("|2| Fetch Data & Display on Screen (Live View)");
            System.out.println("|3| View Saved Results (Read Text File)");
            System.out.println("|4| Modify Saved Data (Interactive)");
            System.out.println("|5| Compare Neurons by Score (Ranking)");
            System.out.println("|6| Delete Saved Data (Remove Neuron)");
            System.out.println("|7| System Guide & Instructions");
            System.out.println("|8| Exit System");
            System.out.println("=");
            System.out.print("Please select an operation -> |1-8|: ");
            
            choice = commandScanner.nextLine().trim();

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
                modifySavedData();
            }
            else if (choice.equals("5")) {
                compareSavedData();
            }
            else if (choice.equals("6")) {
                deleteSavedData();
            }
            else if (choice.equals("7")) {
                showSystemGuide();
            } 
            else if (choice.equals("8")) {
                System.out.println("\n Exiting. Thank you for using!");
                mControl = 0; 
            } 
            else {
                System.out.println("\n :( Invalid selection. Please try again.");
            }
        }
    }
}