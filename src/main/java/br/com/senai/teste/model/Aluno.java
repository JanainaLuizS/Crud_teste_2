package br.com.senai.teste.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "aluno")
public class Aluno {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    private int id;

    private String nome;    
    private String email;

    public Aluno() {
    }       
    public Aluno(String nome, String email) {
        
        this.nome = nome;
        this.email = email;
    }   

   
    public String getNome() {
        return nome;
    }   
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
