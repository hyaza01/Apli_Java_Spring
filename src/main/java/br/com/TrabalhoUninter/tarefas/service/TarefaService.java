package br.com.TrabalhoUninter.tarefas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.TrabalhoUninter.tarefas.model.Tarefa;
import br.com.TrabalhoUninter.tarefas.repository.TarefaRepository;

@Service
@Transactional
public class TarefaService {
    
    private final TarefaRepository repo;
    
    public TarefaService(TarefaRepository repo) { 
        this.repo = repo; 
    }

    public Tarefa salvar(Tarefa t) { 
        return repo.save(t); 
    }
    
    public List<Tarefa> listar() { 
        return repo.findAll(); 
    }
    
    public Optional<Tarefa> buscarPorId(Long id) { 
        return repo.findById(id); 
    }
    
    public void deletar(Long id) { 
        repo.deleteById(id); 
    }
    
    public void deletarTodas() {
        repo.deleteAll();
    }
    
    public long contarTarefas() {
        return repo.count();
    }
    
    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }
}