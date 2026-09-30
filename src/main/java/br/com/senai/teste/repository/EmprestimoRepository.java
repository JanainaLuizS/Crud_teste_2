package br.com.senai.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.teste.model.Emprestimo; 


public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    boolean existsByLivroIdAndDataDevolucaoIsNull(Integer livroId);
    
}
