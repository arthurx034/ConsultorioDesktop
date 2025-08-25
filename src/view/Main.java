package view;

import controller.ConsultaController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ConsultaController controller = new ConsultaController(sc);
            int opcao;

            do {
                exibirMenu();
                opcao = lerOpcao(sc);
                executarOpcao(opcao, controller);
            } while (opcao != 5);

            System.out.println("Saindo do sistema. Até logo!");
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE CONSULTAS ===");
        System.out.println("1 - Agendar nova consulta");
        System.out.println("2 - Listar consultas de um paciente");
        System.out.println("3 - Editar consulta");
        System.out.println("4 - Excluir consulta");
        System.out.println("5 - Sair");
        System.out.print("Escolha: ");
    }

    private static int lerOpcao(Scanner sc) {
        while (true) {
            try {
                int opcao = sc.nextInt();
                sc.nextLine();
                if (opcao >= 1 && opcao <= 5) return opcao;
                System.out.print("❌ Opção inválida. Digite novamente: ");
            } catch (InputMismatchException e) {
                System.out.print("❌ Entrada inválida. Digite um número de 1 a 5: ");
                sc.nextLine();
            }
        }
    }

    private static void executarOpcao(int opcao, ConsultaController controller) {
        switch (opcao) {
            case 1 -> controller.agendarConsulta();
            case 2 -> controller.listarConsultasPorPaciente();
            case 3 -> controller.editarConsulta();
            case 4 -> controller.excluirConsulta();
            case 5 -> System.out.println("Finalizando o sistema...");
        }
    }
}
