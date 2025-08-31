package br.com.TrabalhoUninter.tarefas.config;

import br.com.TrabalhoUninter.tarefas.model.Tarefa;
import br.com.TrabalhoUninter.tarefas.repository.TarefaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(TarefaRepository repository) {
        return args -> {
            // Verifica se o banco já tem dados
            if (repository.count() == 0) {
                System.out.println("Banco de dados vazio. Inserindo dados iniciais...");
                
                // Cria algumas tarefas de exemplo apenas se o banco estiver vazio
                Tarefa tarefa1 = new Tarefa();
                tarefa1.setNome("Implementar API REST");
                tarefa1.setDataEntrega(LocalDate.now().plusDays(7));
                tarefa1.setResponsavel("João Silva");
                repository.save(tarefa1);

                Tarefa tarefa2 = new Tarefa();
                tarefa2.setNome("Testar endpoints");
                tarefa2.setDataEntrega(LocalDate.now().plusDays(10));
                tarefa2.setResponsavel("Maria Santos");
                repository.save(tarefa2);

                Tarefa tarefa3 = new Tarefa();
                tarefa3.setNome("Documentar API");
                tarefa3.setDataEntrega(LocalDate.now().plusDays(14));
                tarefa3.setResponsavel("Pedro Oliveira");
                repository.save(tarefa3);

                System.out.println("Dados iniciais inseridos com sucesso!");
                System.out.println("Total de tarefas criadas: " + repository.count());
            } else {
                System.out.println("Banco de dados já contém dados.");
                System.out.println("Total de tarefas existentes: " + repository.count());
            }
        };
    }
}