package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionario por nome");
            System.out.println("2 - Busca funcionario nome, salario e data contratação");
            System.out.println("3 - Busca funcionario por data contratação");
            System.out.println("4 - Busca funcionario por id, nome e salario");


            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioNomeSalarioMaiorData(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Qual o nome que deseja pesquisar");
        String nome = scanner.next();
        List<Funcionario> list = funcionarioRepository.findByNome(nome);
        list.forEach(System.out::println);
    }

    private void  buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
        System.out.println("Qual o nome que deseja pesquisar");
        String nome = scanner.next();

        System.out.println("Qual a data deseja pesquisar");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);

        System.out.println("Qual o salario que deseja pesquisar");
        BigDecimal salario = scanner.nextBigDecimal();

        List<Funcionario> listaFuncionarios =
                funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, dataContratacao);

        listaFuncionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Qual a data deseja pesquisar");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);

        List<Funcionario> listaFuncionarios =
                funcionarioRepository.findDataContratacaoMaior(dataContratacao);

        listaFuncionarios.forEach(System.out::println);
    }

    private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> listaFuncionarios =
                funcionarioRepository.findFuncionarioSalario();
        listaFuncionarios.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
                + ", nome: " + f.getNome() + ", salário: " + f.getSalario()));
    }
}
