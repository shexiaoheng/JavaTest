import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class TestExample {

    private final OkHttpClient client = new OkHttpClient();


    public String httpGet(String url, String token) throws IOException {
        String respStr = "";
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            respStr = Objects.requireNonNull(response.body()).string();
        }
        return respStr;
    }

    public String httpPost(String url, String token, RequestBody body) throws IOException {
        String respStr = "";
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            respStr = Objects.requireNonNull(response.body()).string();
        }
        return respStr;
    }

}
