package br.com.senai.teste.dto;

public class TokenResponse {
    private String token;

    public TokenResponse(String Token){
        this.token = token;
    }
    
    public String getToken(){
        return  token;
    }
}
