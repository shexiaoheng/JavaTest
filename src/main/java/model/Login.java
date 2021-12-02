package model;

public class Login {

    private String grant_type;
    private String username;
    private String password;
    private String recaptcha_token;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecaptcha_token() {
        return recaptcha_token;
    }

    public void setRecaptcha_token(String recaptcha_token) {
        this.recaptcha_token = recaptcha_token;
    }
}
