package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService {

    private boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
    private final CargoRepository cargoRepository;

    public CrudFuncionarioService(FuncionarioRepository funcionarioRepository,
                                  UnidadeTrabalhoRepository unidadeTrabalhoRepository,
                                  CargoRepository cargoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
        this.cargoRepository = cargoRepository;
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
                    visualizar(scanner);
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
        System.out.println("Qual o nome do funcionario");
        String nome = scanner.next();

        System.out.println("Qual o cpf do funcionario");
        String cpf = scanner.next();

        System.out.println("Qual o salario do funcionario");
        BigDecimal salario = scanner.nextBigDecimal();

        System.out.println("Digite a data de contratacao");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargo");
        Integer cargoId = scanner.nextInt();

        List<UnidadeTrabalho> unidades = unidade(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhoTrabalhos(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Funcionario salvo");
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Digite o id do funcionario que deseja atualizar");
        int funcionarioId = scanner.nextInt();

        System.out.println("Qual o nome do funcionario");
        String nome = scanner.next();

        System.out.println("Qual o cpf do funcionario");
        String cpf = scanner.next();

        System.out.println("Qual o salario do funcionario");
        BigDecimal salario = scanner.nextBigDecimal();

        System.out.println("Digite a data de contratacao");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargo");
        Integer cargoId = scanner.nextInt();

        List<UnidadeTrabalho> unidades = unidade(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioId);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhoTrabalhos(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Funcionario atualizado");
    }

    private void visualizar(Scanner scanner) {
        System.out.println("Qual página deseja visualizar");
        Integer page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

        System.out.println(funcionarios);
        System.out.println("Página atual: " + funcionarios.getNumber());
        System.out.println("Total elementos: " + funcionarios.getTotalElements());
        funcionarios.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.println("Digite o id do funcionario que deseja excluir");
        int funcionarioId = scanner.nextInt();
        funcionarioRepository.deleteById(funcionarioId);
        System.out.println("Funcionario excluído");
    }

    private List<UnidadeTrabalho> unidade(Scanner scanner) {
        boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue) {
            System.out.println("Digite o unidadeId (para sair, digite 0)");
            Integer unidadeId = scanner.nextInt();

            if (unidadeId != 0) {
                Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
                unidades.add(unidade.get());
            } else {
                isTrue = false;
            }
        }

        return unidades;
    }
}
