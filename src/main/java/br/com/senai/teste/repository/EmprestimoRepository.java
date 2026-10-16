package br.com.senai.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.teste.model.Emprestimo;

import java.time.LocalDate;
import java.util.List;


public interface EmprestimoRepository extends JpaRepository
<Emprestimo, Integer> {

    boolean existsByLivroIdAndDataDevolucaoIsNull(Integer livroId);

    List<Emprestimo> findByDataDevolucaoIsNull();

    List<Emprestimo> findByAlunoId(Integer alunoId);
    
    List<Emprestimo> findByDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull
        (LocalDate dataAtual);

// Busca histórico por livro (Pág 18)
List<Emprestimo> findByLivroId(Integer livroId);

// Busca atrasados por aluno (Pág29)
List<Emprestimo> findByAlunoIdAndDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(Integer alunoId, LocalDate dataAtual);

}



    


