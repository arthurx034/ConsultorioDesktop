package controller;

import dao.ConsultaDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import model.Consulta;
import model.Medico;
import model.Paciente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsultaController {

    private final Scanner sc;
    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    public ConsultaController(Scanner sc) {
        this.sc = sc;
    }

    public void agendarConsulta() {
        List<Medico> medicos = medicoDAO.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("😔 Nenhum médico cadastrado.");
            return;
        }

        Paciente paciente = cadastrarOuBuscarPaciente();
        Medico medico = escolherMedico(medicos);
        String dataConsulta = solicitarDataConsulta();
        if (paciente == null || medico == null || dataConsulta == null) return;

        Consulta consulta = new Consulta(paciente, medico, dataConsulta);
        if (consultaDAO.salvarConsulta(consulta)) {
            System.out.println("✅ Consulta agendada com sucesso!");
        } else {
            System.out.println("❌ Falha ao agendar a consulta.");
        }
    }

    public void listarConsultasPorPaciente() {
        List<Consulta> consultas = buscarConsultasPorCpf();
        if (consultas.isEmpty()) return;

        System.out.println("\n📋 Consultas encontradas:");
        consultas.forEach(System.out::println);
    }

    public void editarConsulta() {
        List<Consulta> consultas = buscarConsultasPorCpf();
        if (consultas.isEmpty()) return;

        exibirConsultas(consultas);
        Consulta consultaEscolhida = escolherConsulta(consultas);
        String novaData = solicitarDataConsulta();
        if (consultaEscolhida == null || novaData == null) return;

        if (consultaDAO.editarConsulta(consultaEscolhida.getId(), novaData)) {
            System.out.println("✅ Consulta atualizada com sucesso!");
        } else {
            System.out.println("❌ Falha ao atualizar a consulta.");
        }
    }

    public void excluirConsulta() {
        List<Consulta> consultas = buscarConsultasPorCpf();
        if (consultas.isEmpty()) return;

        exibirConsultas(consultas);
        Consulta consultaEscolhida = escolherConsulta(consultas);
        if (consultaEscolhida == null) return;

        if (consultaDAO.excluirConsulta(consultaEscolhida.getId())) {
            System.out.println("✅ Consulta excluída com sucesso!");
        } else {
            System.out.println("❌ Falha ao excluir a consulta.");
        }
    }

    private Paciente cadastrarOuBuscarPaciente() {
        String cpf = solicitarCpf();
        Paciente paciente = pacienteDAO.buscarPorCpf(cpf);
        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente.getNome());
            return paciente;
        }

        System.out.println("Novo paciente!");
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        int idade = solicitarIdade();

        paciente = new Paciente(nome, idade, cpf);
        pacienteDAO.salvarPaciente(paciente);
        return paciente;
    }

    private String solicitarCpf() {
        while (true) {
            System.out.print("Digite o CPF do paciente (11 números): ");
            String cpf = sc.nextLine().replaceAll("\\D", "");
            if (cpf.length() == 11 && !cpf.matches("(\\d)\\1{10}")) return cpf;
            System.out.println("❌ CPF inválido.");
        }
    }

    private int solicitarIdade() {
        while (true) {
            System.out.print("Idade: ");
            try {
                int idade = Integer.parseInt(sc.nextLine());
                if (idade > 0 && idade <= 120) return idade;
                System.out.println("❌ Idade inválida. Digite entre 1 e 120.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido.");
            }
        }
    }

    private Medico escolherMedico(List<Medico> medicos) {
        System.out.println("\nEscolha o médico:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.printf("%d - %s (%s)%n", i + 1, m.getNome(), m.getEspecialidade());
        }

        while (true) {
            System.out.print("Número do médico: ");
            try {
                int opcao = Integer.parseInt(sc.nextLine());
                if (opcao >= 1 && opcao <= medicos.size()) return medicos.get(opcao - 1);
                System.out.println("❌ Opção inválida.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido.");
            }
        }
    }

    private String solicitarDataConsulta() {
        System.out.print("Data da consulta (dd/MM/yyyy): ");
        String dataInput = sc.nextLine();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date data = sdf.parse(dataInput);

            if (data.before(new Date())) {
                System.out.println("❌ A data não pode ser anterior à atual!");
                return null;
            }

            return new SimpleDateFormat("yyyy-MM-dd").format(data);

        } catch (ParseException e) {
            System.out.println("❌ Formato inválido. Use dd/MM/yyyy.");
            return null;
        }
    }

    private List<Consulta> buscarConsultasPorCpf() {
        String cpf = solicitarCpf();
        List<Consulta> consultas = consultaDAO.listarConsultasPorPacienteRetorno(cpf);

        if (consultas.isEmpty()) {
            System.out.println("😔 Nenhuma consulta encontrada para este paciente.");
        }

        return consultas;
    }

    private void exibirConsultas(List<Consulta> consultas) {
        System.out.println("\n📋 Consultas encontradas:");
        consultas.forEach(System.out::println);
    }

    private Consulta escolherConsulta(List<Consulta> consultas) {
        while (true) {
            System.out.print("Digite o ID da consulta: ");
            try {
                long id = Long.parseLong(sc.nextLine());
                for (Consulta c : consultas) {
                    if (c.getId() == id) return c;
                }
                System.out.println("❌ ID inválido.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido.");
            }
        }
    }
}