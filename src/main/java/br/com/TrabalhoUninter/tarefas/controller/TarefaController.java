package br.com.TrabalhoUninter.tarefas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.TrabalhoUninter.tarefas.dto.ErrorResponse;
import br.com.TrabalhoUninter.tarefas.exception.ResourceNotFoundException;
import br.com.TrabalhoUninter.tarefas.model.Tarefa;
import br.com.TrabalhoUninter.tarefas.service.TarefaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {
    
    private final TarefaService service;
    
    public TarefaController(TarefaService service) { 
        this.service = service; 
    }

    // Endpoint de teste para verificar se a API está funcionando
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API de Tarefas funcionando corretamente!");
    }

    // Criar nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa) {
        Tarefa salvo = service.salvar(tarefa);
        return ResponseEntity
            .created(URI.create("/api/tarefas/" + salvo.getId()))
            .body(salvo);
    }

    // Listar todas as tarefas
    @GetMapping
    public ResponseEntity<List<Tarefa>> listar() { 
        List<Tarefa> tarefas = service.listar();
        
        if (tarefas.isEmpty()) {
            // Retorna lista vazia com status 200 OK
            return ResponseEntity.ok(tarefas);
        }
        
        return ResponseEntity.ok(tarefas);
    }

    // Obter tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> obter(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("Tarefa", "ID", id));
    }

    // Atualizar tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa dados) {
        return service.buscarPorId(id)
            .map(tarefaExistente -> {
                tarefaExistente.setNome(dados.getNome());
                tarefaExistente.setDataEntrega(dados.getDataEntrega());
                tarefaExistente.setResponsavel(dados.getResponsavel());
                return ResponseEntity.ok(service.salvar(tarefaExistente));
            })
            .orElseThrow(() -> new ResourceNotFoundException("Tarefa", "ID", id));
    }

    // Remover tarefa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> remover(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(tarefa -> {
                service.deletar(id);
                
                // Retorna mensagem de sucesso
                ErrorResponse successResponse = new ErrorResponse(
                    HttpStatus.OK.value(),
                    "Tarefa com ID " + id + " foi removida com sucesso",
                    "/api/tarefas/" + id
                );
                
                return ResponseEntity.ok(successResponse);
            })
            .orElseThrow(() -> new ResourceNotFoundException(
                "Não foi possível remover. Tarefa não encontrada com ID: " + id
            ));
    }
    
    // Remover todas as tarefas (use com cuidado!)
    @DeleteMapping("/all")
    public ResponseEntity<ErrorResponse> removerTodas() {
        long count = service.contarTarefas();
        service.deletarTodas();
        
        ErrorResponse response = new ErrorResponse(
            HttpStatus.OK.value(),
            count + " tarefa(s) foram removidas com sucesso",
            "/api/tarefas/all"
        );
        
        return ResponseEntity.ok(response);
    }
}