import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatGPT {
    String url;
    private final String apiKey = "ADD OPENAI KEY";
    String model;

    public ChatGPT(String model) {
        url = "https://api.openai.com/v1/chat/completions";
        this.model = model;
    }

    public String getGPT(String prompt) {
        try {
            // Using URI to handle URL
            URI uri = new URI(url);
            URL obj = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Print full response for debugging
            //System.out.println("Full Response: " + response.toString());

            // Extract the message using regex
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Error communicating with the API", e);
        }
    }

    // Use regex to extract the content from the JSON response
    public static String extractMessageFromJSONResponse(String response) {
        // Define a regex pattern to find content field
        String pattern = "\"content\": "+ "\"(.*?)" +"\"";

        // Compile the pattern and create a matcher
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(response);

        // If a match is found, return the content
        if (m.find()) {
            return m.group(0);  // Group 1 will contain the message content
        } else {
            return "Error: Content not found in the response.";
        }
    }
}
