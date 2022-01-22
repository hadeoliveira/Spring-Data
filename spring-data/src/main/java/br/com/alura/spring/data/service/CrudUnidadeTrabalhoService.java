package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudUnidadeTrabalhoService {

    private boolean system = true;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudUnidadeTrabalhoService (UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {
        while(system) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner) {
        System.out.println("Qual a descricao da unidade de trabalho");
        String descricao = scanner.next();

        System.out.println("Qual o endereço da unidade de trabalho");
        String endereco = scanner.next();

        UnidadeTrabalho unidade = new UnidadeTrabalho();
        unidade.setDescricao(descricao);
        unidade.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidade);
        System.out.println("Unidade salva");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Digite o id da unidade que deseja alterar");
        Integer id = scanner.nextInt();

        System.out.println("Qual a descricao da unidade de trabalho");
        String descricao = scanner.next();

        System.out.println("Qual o endereço da unidade de trabalho");
        String endereco = scanner.next();

        UnidadeTrabalho unidade = new UnidadeTrabalho();
        unidade.setId(id);
        unidade.setDescricao(descricao);
        unidade.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidade);
        System.out.println("Unidade alterado");
    }

    private void visualizar() {
        Iterable<UnidadeTrabalho> unidades = unidadeTrabalhoRepository.findAll();
        unidades.forEach(unidade -> System.out.println(unidade));
    }

    private void deletar(Scanner scanner) {
        System.out.println("Digite o id que deseja deletar");
        int id = scanner.nextInt();
        unidadeTrabalhoRepository.deleteById(id);
        System.out.println("Unidade de trabalho deletada");
    }
}
