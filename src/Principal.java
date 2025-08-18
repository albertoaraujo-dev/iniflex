
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import model.Funcionario;

public class Principal {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Funcionario> funcionarios = new ArrayList<>();
        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"),
                "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"),
                "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"),
                "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"),
                "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.64"),
                "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"),
                "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"),
                "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"),
                "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"),
                "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"),
                "Gerente"));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:
                //• informação de data deve ser exibido no formato dd/mm/aaaa;
                //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        System.out.println("----------------------------------------------------------------------------");
        // Alinhamento das colunas 15 caracteres para nome, 20 para data, 15 para salário e 15 para função
        System.out.printf("%-20s%-20s%-20s%-20s\n", "| Nome ", "| Data Nascimento ", "| Salário ", "| Função ");
        System.out.println("----------------------------------------------------------------------------");
        funcionarios.forEach(funcionario -> {
            String nome = funcionario.getNome();
            String dataNascimento = funcionario.getDataNascimento().format(formatter);
            String salario = "R$ " + String.format(new Locale("pt", "BR"),"%,.2f", funcionario.getSalario());
            String funcao = funcionario.getFuncao();
            System.out.printf("%-20s%-20s%-20s%-20s\n", "| " + nome, "| " + dataNascimento, "| " + salario, "| " + funcao);
        });

        System.out.println(""); // pular linha

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarios.forEach(funcionario -> {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        });

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários, agrupados por função.
        System.out.println("Funcionários agrupados por função: \n");
        System.out.printf("%-20s%s\n", "| Função", "| Funcionários");
        System.out.println("------------------------------------");
        funcionariosPorFuncao.forEach((funcao, lista) -> {

            System.out.printf("%-20s", "| " + funcao);
            System.out.printf("| ");

            for (int i = 0; i < lista.size(); i++) {
                System.out.print(lista.get(i).getNome());
                if (i < lista.size() - 1) {
                        System.out.print(", ");
                }
            }
            System.out.println();
        });

        System.out.println(""); // pular linha

        // 3.7 – Não tem no teste.

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        System.out.println("Funcionários que fazem aniversário nos meses 10 e 12 \n");
        int[] mesesAniversario = { 10, 12 };
        funcionarios.stream()
                .filter(funcionario -> Arrays.stream(mesesAniversario)
                        .anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes))
                .forEach(funcionario -> System.out.println(funcionario.getNome()));

        System.out.println(""); // pular linha

        //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        System.out.println("Funcionário com a maior idade: \n");
        Funcionario maisVelho = Collections.min(funcionarios,
                Comparator.comparing(Funcionario::getDataNascimento));
        System.out.println("Nome: " + maisVelho.getNome() +
                ", Idade: " + maisVelho.getDataNascimento().until(LocalDate.now()).toTotalMonths() / 12 + " anos");

        System.out.println(""); // pular linha

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        System.out.println("Funcionários por ordem alfabética: \n");
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        funcionarios.forEach(f -> System.out.println(f.getNome()));

        System.out.println(""); // pular linha

        // 3.11 – Imprimir o total dos salários dos funcionários.
        System.out.println("Valor total dos salários dos funcionários: \n");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String totalSalariosFormatado = String.format("%,.2f", totalSalarios);
        System.out.println("Total dos salários: R$" + totalSalariosFormatado + "\n");

        System.out.println(""); // pular linha

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        System.out.println("Quantos salários mínimos ganha cada funcionário: \n");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioEmSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2,
                    RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salarioEmSalariosMinimos + " salários mínimos.");
        });
    }
}
