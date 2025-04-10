import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Users_Import_Java {

    private static final String API_URL = "https://sandbox.piano.io/api/v3/publisher/user/list";
    private static final String API_TOKEN = "xeYjNEhmutkgkqCZyhBn6DErVntAKDx30FqFOS6D";
    private static final String AID = "o1sRRZSLlw";

    public static void main(String[] args) throws Exception {
        HashMap<String, String> emailToUid = fetchUsersFromPiano();

        List<String[]> customerA = readCSV("userdata_A.csv");
        List<String[]> customerB = readCSV("userdata_B.csv");

        String[] headerA = customerA.get(0);
        String[] headerB = customerB.get(0);

        HashMap<String, String[]> customerBMap = new HashMap<>();
        for (int i = 1; i < customerB.size(); i++) {
            customerBMap.put(customerB.get(i)[0], customerB.get(i)); 
        }
        
        List<String[]> output = new ArrayList<>();
        String[] newHeader = Arrays.copyOf(headerA, headerA.length + headerB.length - 1);
        System.arraycopy(headerB, 1, newHeader, headerA.length, headerB.length - 1);
        output.add(newHeader);

        for (int i = 1; i < customerA.size(); i++) {
            String[] rowA = customerA.get(i);
            String userId = rowA[0];
            String email = rowA[1];

            String uid = emailToUid.getOrDefault(email, userId); 
            rowA[0] = uid;

            String[] rowB = customerBMap.get(userId);
            String[] merged = Arrays.copyOf(rowA, newHeader.length);
            if (rowB != null) {
                System.arraycopy(rowB, 1, merged, headerA.length, rowB.length - 1);
            }
            output.add(merged);
        }

        writeCSV("users_output.csv", output);
        System.out.println("Output written to users_output.csv");
    }

    private static HashMap<String, String> fetchUsersFromPiano() throws Exception {
        HashMap<String, String> result = new HashMap<>();

        String payload = "aid=" + AID;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?" + payload))
                .header("api_token", API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode users = mapper.readTree(response.body()).get("users");
        // System.out.println(users);
        for (JsonNode user : users) {
            String email = user.get("email").asText();
            String uid = user.get("uid").asText();
            result.put(email, uid);
        }
        // System.out.println(result);
        return result;
    }

    private static List<String[]> readCSV(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }

    private static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(data);
        }
    }
}