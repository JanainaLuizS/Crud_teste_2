package br.com.senai.teste.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank (message = "O login é obrigatório")
    private String login;

    @NotBlank (message = "A senha é obrigatória")
    private String senha;

public String getLogin(){
    return login;
}

public void setLogin(String login){
    this.login =login;
}
public String getSenha(){
    return senha;
}
public void setSenha(String senha){
    this.senha = senha;
}
    
}
