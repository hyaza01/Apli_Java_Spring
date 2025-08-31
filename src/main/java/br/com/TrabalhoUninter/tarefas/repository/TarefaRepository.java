package br.com.TrabalhoUninter.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.TrabalhoUninter.tarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> { }
