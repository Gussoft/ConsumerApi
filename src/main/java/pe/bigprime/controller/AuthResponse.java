package pe.bigprime.controller;

public class AuthResponse {
    private String token;
    private String access;
    private String refresh;

    public AuthResponse() {

    }

    public AuthResponse(String token, String access, String refresh) {
        this.token = token;
        this.access = access;
        this.refresh = refresh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
