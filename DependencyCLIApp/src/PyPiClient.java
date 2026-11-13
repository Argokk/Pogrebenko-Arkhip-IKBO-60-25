import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PyPiClient {
    private static final int SUCCESS_STATUS = 200;
    private static final int NOT_FOUND_STATUS = 404;
    private static final int NOT_FOUND_INDEX = -1;
    private static final int FIRST_LEVEL = 1;
    private static final String DEPENDENCIES_FIELD = "\"requires_dist\"";

    public List<String> getDirectDependencies(String repositoryUrl, String packageName)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(createRequestUri(repositoryUrl, packageName))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        checkResponseStatus(response.statusCode(), packageName);
        return readDependencies(response.body());
    }

    private URI createRequestUri(String repositoryUrl, String packageName) throws IOException {
        String baseUrl = removeLastSlash(repositoryUrl);
        String encodedName = URLEncoder.encode(packageName, StandardCharsets.UTF_8);
        String requestUrl = baseUrl + "/pypi/" + encodedName + "/json";

        try {
            return URI.create(requestUrl);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid repository URL: " + repositoryUrl, e);
        }
    }

    private String removeLastSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - FIRST_LEVEL);
        }
        return url;
    }

    private void checkResponseStatus(int statusCode, String packageName) throws IOException {
        if (statusCode == SUCCESS_STATUS) {
            return;
        }
        if (statusCode == NOT_FOUND_STATUS) {
            throw new IOException("Package not found: " + packageName);
        }
        throw new IOException("Repository returned HTTP status " + statusCode);
    }

    private List<String> readDependencies(String json) throws IOException {
        int fieldIndex = json.indexOf(DEPENDENCIES_FIELD);
        if (fieldIndex == NOT_FOUND_INDEX) {
            throw new IOException("Response does not contain requires_dist");
        }

        int valueStart = findValueStart(json, fieldIndex);
        if (json.startsWith("null", valueStart)) {
            return List.of();
        }
        if (json.charAt(valueStart) != '[') {
            throw new IOException("Invalid requires_dist value");
        }

        int arrayEnd = findArrayEnd(json, valueStart);
        if (arrayEnd == NOT_FOUND_INDEX) {
            throw new IOException("Dependencies array is not closed");
        }
        return readStringValues(json, valueStart + FIRST_LEVEL, arrayEnd);
    }

    private int findValueStart(String json, int fieldIndex) throws IOException {
        int colonIndex = json.indexOf(':', fieldIndex);
        if (colonIndex == NOT_FOUND_INDEX) {
            throw new IOException("Invalid response format");
        }

        int index = colonIndex + FIRST_LEVEL;
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
        if (index == json.length()) {
            throw new IOException("Invalid response format");
        }
        return index;
    }

    private int findArrayEnd(String json, int arrayStart) {
        boolean inString = false;
        boolean escaped = false;
        for (int i = arrayStart + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaped) {
                escaped = false;
            } else if (inString && c == '\\') {
                escaped = true;
            } else if (c == '"') {
                inString = !inString;
            } else if (!inString && c == ']') {
                return i;
            }
        }
        return NOT_FOUND_INDEX;
    }

    private List<String> readStringValues(String json, int start, int end) {
        List<String> dependencies = new ArrayList<>();
        String arrayText = json.substring(start, end);
        String[] values = arrayText.split("(?<!\\\\)\"\\s*,\\s*\"");

        for (String value : values) {
            String dependency = value.trim();
            dependency = removeQuotes(dependency);
            dependency = dependency.replace("\\\"", "\"");

            if (!dependency.isEmpty()) {
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }

    private String removeQuotes(String value) {
        if (value.startsWith("\"")) {
            value = value.substring(FIRST_LEVEL);
        }
        if (value.endsWith("\"")) {
            return value.substring(0, value.length() - FIRST_LEVEL);
        }
        return value;
    }
}
