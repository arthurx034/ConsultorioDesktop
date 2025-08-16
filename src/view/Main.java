package view;

import model.Medico;
import model.Paciente;
import model.Consulta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Medico> medicos = List.of(
                new Medico("Dr. Silva", "Cardiologia", "123456789"),
                new Medico("Dra. Souza", "Pediatria", "987654321"),
                new Medico("Dr. Oliveira", "Ortopedia", "456789123")
        );

        List<Consulta> consultas = new ArrayList<>();

        System.out.println("Digite o nome do paciente:");
        String nome = sc.nextLine();

        System.out.println("Digite a idade do paciente:");
        int idade = sc.nextInt();
        sc.nextLine(); // consumir quebra de linha pendente

        System.out.println("Digite o cpf do paciente:");
        String cpf = sc.nextLine();

        Paciente paciente = new Paciente(nome, idade, cpf);

        System.out.println("\nQual médico você deseja consultar?");
        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            System.out.println(i + " - Nome: " + medico.getNome() +
                    ", Especialidade: " + medico.getEspecialidade() +
                    ", CRM: " + medico.getCrm());
        }

        int medicoEscolhido;
        while (true) {
            System.out.print("Digite o número do médico: ");
            medicoEscolhido = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha
            if (medicoEscolhido >= 0 && medicoEscolhido < medicos.size()) {
                break;
            }
            System.out.println("Opção inválida, tente novamente.");
        }

        System.out.println("Qual data você deseja agendar a consulta? (dd/mm/yyyy)");
        String dataConsulta = sc.nextLine();

        Consulta consulta = new Consulta(paciente, medicos.get(medicoEscolhido), dataConsulta);
        consultas.add(consulta);

        System.out.println("\nConsulta agendada com sucesso!");
        System.out.println("Consultas agendadas: ");
        for (Consulta c : consultas) {
            System.out.println("Paciente: " + c.getPaciente().getNome() +
                    ", Médico: " + c.getMedico().getNome() +
                    ", Data: " + c.getDataConsulta());
        }

        sc.close();
    }
}