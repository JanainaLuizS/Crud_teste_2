package br.com.senai.teste.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.repository.AlunoRepository;


@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

   public List<Aluno> listar() {
        return alunoRepository.findAll();
    }   

    public Optional<Aluno> buscarPorId(int id) {
        return alunoRepository.findById(id);
    }

    public Optional <Aluno> atualizar(int id, Aluno novosDados) {
        Optional<Aluno> alunoEncontrado = alunoRepository.findById(id);
        if (alunoEncontrado.isEmpty()) {
            return Optional.empty();
        } 
        Aluno aluno = alunoEncontrado.get();

        aluno.setNome(novosDados.getNome());
        aluno.setEmail(novosDados.getEmail());
          
                return Optional.of(alunoRepository.save(aluno));
        }

    public boolean excluir(Integer id) {
       
        if (!alunoRepository.existsById(id)){
            return false;
        }
        alunoRepository.deleteById(id);
        return true;
    }
public Aluno salvarFoto(Integer id, MultipartFile arquivo) {

    Optional<Aluno> alunoEncontrado = alunoRepository.findById(id);

    if (alunoEncontrado.isEmpty()) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Aluno não encontrado");
    }

    Aluno aluno = alunoEncontrado.get();

    String nomeArquivo = System.currentTimeMillis()
            + "_"
            + arquivo.getOriginalFilename();

    File pasta = new File("uploads/fotos-alunos");

    if (!pasta.exists()) {
        pasta.mkdirs();
    }

    File destino = new File(
            pasta,
            nomeArquivo).getAbsoluteFile();

    try {
        arquivo.transferTo(destino);
    } catch (IOException erro) {
        erro.printStackTrace();

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível salvar a foto");
    }

    aluno.setNomeArquivoFoto(nomeArquivo);

    return alunoRepository.save(aluno);
}


    }
