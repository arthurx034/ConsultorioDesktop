package view;

import dao.ConsultaDAO;
import dao.DatabaseConnection;
import model.Consulta;
import model.Medico;
import model.Paciente;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsultaDAO consultaDAO = new ConsultaDAO();

        System.out.println("=== SISTEMA DE CONSULTAS MÉDICAS ===");

        // --- 1. Listar médicos do banco ---
        List<Medico> medicos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nome, especialidade, crm FROM medico")) {

            while (rs.next()) {
                medicos.add(new Medico(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("crm")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar médicos do banco: " + e.getMessage());
            return;
        }

        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado no banco. Cadastre médicos primeiro.");
            return;
        }

        // --- 2. Cadastro do paciente ---
        System.out.print("Digite o nome do paciente: ");
        String nome = sc.nextLine();

        System.out.print("Digite a idade do paciente: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Digite o CPF do paciente: ");
        String cpf = sc.nextLine();

        Paciente paciente = new Paciente(nome, idade, cpf);

        // --- 3. Escolha do médico ---
        System.out.println("\nQual médico você deseja consultar?");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.println(i + 1 + " - " + m.getNome() + " (" + m.getEspecialidade() + ") - CRM: " + m.getCrm());
        }

        int medicoEscolhido;
        while (true) {
            System.out.print("Digite o número do médico: ");
            medicoEscolhido = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha
            if (medicoEscolhido >= 0 && medicoEscolhido < medicos.size()) break;
            System.out.println("Opção inválida, tente novamente.");
        }

        // --- 4. Data da consulta ---
        System.out.print("Qual data deseja agendar a consulta? (dd/MM/yyyy): ");
        String dataInput = sc.nextLine();

        String dataConsulta;
        try {
            // Converte do formato do usuário (dd/MM/yyyy) para o formato MySQL (yyyy-MM-dd)
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataInput);
            dataConsulta = new SimpleDateFormat("yyyy-MM-dd").format(data);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido! Use dd/MM/yyyy");
            return;
        }

        // --- 5. Criar e salvar a consulta ---
        Consulta consulta = new Consulta(paciente, medicos.get(medicoEscolhido), dataConsulta);
        consultaDAO.salvarConsulta(consulta);

        System.out.println("\nConsulta agendada com sucesso!");

        // --- 6. Buscar todas as consultas do paciente no banco ---
        System.out.println("\nConsultas do paciente " + paciente.getNome() + ":");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.data_consulta, m.nome AS medico_nome, m.especialidade " +
                             "FROM consulta c " +
                             "JOIN medico m ON c.medico_id = m.id " +
                             "JOIN paciente p ON c.paciente_id = p.id " +
                             "WHERE p.cpf = ? " +
                             "ORDER BY c.data_consulta")) {

            stmt.setString(1, paciente.getCpf());
            ResultSet rs = stmt.executeQuery();

            boolean hasConsultas = false;
            while (rs.next()) {
                hasConsultas = true;
                String data = rs.getString("data_consulta");
                String nomeMedico = rs.getString("medico_nome");
                String especialidade = rs.getString("especialidade");
                System.out.println(data + " - " + nomeMedico + " (" + especialidade + ")");
            }

            if (!hasConsultas) {
                System.out.println("Nenhuma consulta encontrada para este paciente.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas do paciente: " + e.getMessage());
        }

        sc.close();
    }
}
