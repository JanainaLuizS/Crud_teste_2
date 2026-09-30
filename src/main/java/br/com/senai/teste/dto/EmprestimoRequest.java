package br.com.senai.teste.dto;


public class EmprestimoRequest {

private Integer alunoId;
   private Integer livroId;

    public EmprestimoRequest() {}
    

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId( Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    
}
