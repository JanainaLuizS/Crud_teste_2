package br.com.senai.teste.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;

import br.com.senai.teste.dto.EmprestimoRequest;
import br.com.senai.teste.dto.RenovacaoRequest;
import br.com.senai.teste.model.Emprestimo;
import br.com.senai.teste.service.EmprestimoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping //Realiza um novo empréstimo de livro para um aluno.
    public ResponseEntity<Emprestimo> cadastrar(@Valid @RequestBody EmprestimoRequest dados) {
        Integer alunoId = dados.getAlunoId();
        Integer livroId = dados.getLivroId();

        LocalDate dataPrevistaDevolucao = dados.getDataPrevistaDevolucao();

        
        if (alunoId == null || livroId == null || dataPrevistaDevolucao == null) {
            return ResponseEntity.badRequest().build();

        }
        Optional<Emprestimo> emprestimo = emprestimoService.cadastrar(alunoId, livroId, dataPrevistaDevolucao);
        if (emprestimo.isEmpty()) {
            return ResponseEntity.notFound().build();
        } 

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(emprestimo.get());
    }
    @GetMapping //Retorna a lista completa de todos os empréstimos registrados no sistema (ativos, devolvidos e atrasados).
    public ResponseEntity <List<Emprestimo>> listar() {
        List<Emprestimo> emprestimos = emprestimoService.listar();
        return ResponseEntity.ok(emprestimos);
    }
    @GetMapping("/{id}")//Busca os detalhes de um empréstimo específico através do seu ID.
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(emprestimo.get());
        }
        return ResponseEntity.notFound().build();
    }   
    @PatchMapping("/{id}/devolucao")//Registra a devolução do livro, gravando a data de entrega atual e alterando a situação do empréstimo para "Devolvido".
    public ResponseEntity<Emprestimo> devolver(@PathVariable Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoService.devolver(id);
        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(emprestimo.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping ("/ativos")//Retorna apenas os empréstimos que ainda estão em aberto (livros que ainda não foram devolvidos).
    public ResponseEntity<List<Emprestimo>> listarAtivos() {
        List<Emprestimo> emprestimos  = emprestimoService.listarAtivos();
        return ResponseEntity.ok(emprestimos);
    }
    @GetMapping("/aluno/{alunoId}")//Traz o histórico completo de empréstimos de um aluno específico.
    public ResponseEntity<List<Emprestimo>> listarPorAluno(@PathVariable Integer alunoId) {
        
        List<Emprestimo> emprestimos = emprestimoService.listarPorAluno(alunoId);
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/atrasados")//Retorna todos os empréstimos do sistema que estão com a data de devolução vencida em relação a hoje e ainda não foram entregues.
    public ResponseEntity<List<Emprestimo>> listarAtrasados() {
        List<Emprestimo> emprestimos = emprestimoService.listarAtrasados();
        return ResponseEntity.ok(emprestimos);
    }

    @PatchMapping ("/{id}/renovacao")//Renova o empréstimo, atualizando a dataPrevistaDevolucao para uma nova data enviada na requisição.
    public ResponseEntity<Emprestimo> renovar(
        @PathVariable Integer id, @Valid @RequestBody RenovacaoRequest dados) {
        
            Optional<Emprestimo> emprestimo = emprestimoService.renovar(id, dados.getNovaDataPrevista());
            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(emprestimo.get());
        }

    @GetMapping("/livro/{livroId}")//Traz o histórico completo de empréstimos de um livro específico (quais alunos já pegaram esse livro).
    public ResponseEntity<List<Emprestimo>> listarPorLivro(@PathVariable Integer livroId) {
        List<Emprestimo> emprestimos = emprestimoService.listarPorLivro(livroId);
        return ResponseEntity.ok(emprestimos);
    }

    // Mostra apenas os empréstimos em atraso de um aluno específico.
    @GetMapping("/aluno/{alunoId}/atrasados")
    public ResponseEntity<List<Emprestimo>> listarAtrasadosPorAluno(@PathVariable Integer alunoId) {
        List<Emprestimo> emprestimos = emprestimoService.listarAtrasadosPorAluno(alunoId);
        return ResponseEntity.ok(emprestimos);
    }
}     
