import com.google.gson.Gson;
import model.*;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.jupiter.api.Test;
import util.UrlManager;

import java.io.IOException;

class TestExampleTest {

    public final static String Default_RToken = "03AGdBq279VI1dExrRrjvbL1z9iKcFDz5rAyFtWhSsIXsmZVwLNvgY8TWAjjy7-9f_" +
            "PIixL8ncg2NWE0VCC4nNUJbcfLH68JjM3WRa6ewliOwPd9LG7ZiELxxFuSO9GvtO3IxHqJJ5qBY1OJ-FkDWz4R6DBAKo1WHAkHqVrd" +
            "LNuUR-Avthj2QMiIPZLMF97lH0HfPpv_hs29TiJHkQT4_kdoW7-glGWWYqbET0qPfRYyJkxA2VxTzEl255HOKSX-XkI-qvujDRS1tM" +
            "qKrkx1wsQReWA8yTJV8Ve1Lag3QmwnPRAZNWXCojpR2Ub7mlYmavpkdEsW57U6jfgoXTC2AJfMmSsgja5B4gSlWS1ei6e2jXjBjjZw" +
            "Qyk4vyddhFsb6YzY0MGenfoKbP9LC3NccWB5IvGnBhmaEGAl46jhw--CfDJjeYXnI5g_hHkyL9AN0rKseRG3Hji9gs";

    @Test
    void httpGet() {
    }

    @Test
    void httpPost() throws IOException {
        TestExample example = new TestExample();
        Gson gson = new Gson();

        // login and get token
        RequestBody loginBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", "shexiaoheng@163.com")
                .add("password", "ZHIhengankr.")
                .add("recaptcha_token", Default_RToken)
                .build();
        String respLogin = example.httpPost(UrlManager.Login, "", loginBody);
        System.out.println(respLogin);
        Token token = gson.fromJson(respLogin, Token.class);
        String curToken = token.getAccess_token();
        System.out.println(curToken);
    }

    @Test
    void creatAPI() throws IOException {
        TestExample example = new TestExample();

        Gson gson = new Gson();

        // login and get token
        RequestBody loginBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", "shexiaoheng@163.com")
                .add("password", "ZHIhengankr.")
                .add("recaptcha_token", Default_RToken)
                .build();
        String respLogin = example.httpPost(UrlManager.Login, "", loginBody);
        System.out.println(respLogin);
        Token token = gson.fromJson(respLogin, Token.class);
        String curToken = token.getAccess_token();

        // get team id
        String respTeam = example.httpGet(UrlManager.Team, curToken);
        System.out.println(respTeam);
        Team team = gson.fromJson(respTeam, Team.class);
        String teamId = team.getDefault_team().getId();

        // create api
        Create create = new Create();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setAuth_type("api_auth_type_token");
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPayment_methods("payment_ankr_credit");
        create.setAuth_info(authInfo);
        create.setPayment_info(paymentInfo);
        create.setProject_name("test_create_by_java");
        create.setApi_cluster_name("hk");
        create.setNode_code("0031");
        create.setService_id("service-71108452-62f7-4a9c-aa4d-675d5f93c79a");
        create.setSubscribe_status("subscribe_enable");
        create.setTeam_id(teamId);
        String reqStrCreate = gson.toJson(create);
        System.out.println(reqStrCreate);

        MediaType mediaJson = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(reqStrCreate, mediaJson);
        String respCreate = example.httpPost(UrlManager.Create_API, curToken, body);
        System.out.println(respCreate);
    }
}