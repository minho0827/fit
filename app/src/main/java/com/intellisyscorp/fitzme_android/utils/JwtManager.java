package com.intellisyscorp.fitzme_android.utils;


import android.util.Log;

public class JwtManager {

    private static JwtManager instance;
    private String authToken;

    // Singleton
    private JwtManager() {
    }

    static {
        try {
            instance = new JwtManager();
        } catch (Exception e) {
            throw new RuntimeException("Exception creating JwtManager instance.");
        }
    }

    public static JwtManager getInstance() {
        return instance;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        Log.d("JwtManager :", "=================[ " + authToken + " ]=================");
        this.authToken = authToken;
    }

}
