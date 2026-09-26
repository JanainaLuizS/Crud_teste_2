package br.com.senai.teste.model;

import java.security.PublicKey;
import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "emprestimo")
public class Emprestimo {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataEmprestimo;

    @ManyToOne 
    @JoinColumn (name = "aluno_id", nullable = =false)
    private aluno;

    @ManyToOne 
    @JoinColumn (name = "livro_id", nullable = false)
    private Livro livro;

    public Emprestimo(){}

    public Integer getId(){
        return id;
    }

    public LocalDate getdataEmprestimo(){
    return dataEmprestimo;
}
    public void setDataEmprestimo(LocalDate dataEmprestimo){
        this.dataEmprestimo = dataEmprestimo;
    }

    public Aluno getAluno(){
        return aluno;
    }
    public void setAluno(Aluno aluno){
        this.aluno = aluno;
    }

    public void setAluno (Aluno aluno){
        this.aluno = aluno;
    }
        public Livro getLivro(){
            return livro;
        }

        public void setLivro(Livro livro){
            this.livro = livro;
        }
}
