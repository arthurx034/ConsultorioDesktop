package view;

import model.Medico;
import model.Paciente;
import model.Consulta;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Medico> medicos = List.of(
            new Medico("Dr. Silva", "Cardiologia", "123456789"),
            new Medico("Dra. Souza", "Pediatria", "987654321"),
            new Medico("Dr. Oliveira", "Ortopedia", "456789123")
        );
        List<Object> consultas = List.of();

        System.out.println("Digite o nome do paciente:");
        String nome = sc.nextLine();
        System.out.println("Digite a idade do paciente:");
        int idade = sc.nextInt();
        System.out.println("Digite o cpf do paciente:");
        String cpf = sc.next();

        Paciente paciente = new Paciente(nome, idade, cpf);

        System.out.println("Qual médico você deseja consultar?");

        for (Medico medico : medicos) {
            System.out.println("Nome: " + medico.getNome() + ", Especialidade: " + medico.getEspecialidade() + ", CRM: " + medico.getCrm());
        }

        int medicoEscolhido = sc.nextInt();

        System.out.println("Qual data você deseja agendar a consulta? (dd/mm/yyyy)");
        String dataConsulta = sc.next();

        Consulta consulta = new Consulta(paciente, medicos.get(medicoEscolhido), dataConsulta);
        consultas.add(consulta);

        System.out.println("Consulta agendada com sucesso!");
        System.out.println("Consultas agendadas: ");
        for (Object c : consultas) {
            System.out.println("Paciente: " + c.getPaciente().getNome() + ", Médico: " + c.getMedico().getNome() + ", Data: " + c.getDataConsulta());
        }
    }
}