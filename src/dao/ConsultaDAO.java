package dao;

import model.Consulta;
import model.Medico;
import model.Paciente;

import java.sql.*;

public class ConsultaDAO {

    public void salvarConsulta(Consulta consulta) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false); // Inicia transação

            // --- 1. Verificar se paciente já existe pelo CPF ---
            int pacienteId;
            String sqlBuscarPaciente = "SELECT id FROM paciente WHERE cpf = ?";
            try (PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscarPaciente)) {
                stmtBuscar.setString(1, consulta.getPaciente().getCpf());
                ResultSet rs = stmtBuscar.executeQuery();
                if (rs.next()) {
                    pacienteId = rs.getInt("id");
                    System.out.println("Paciente já cadastrado. ID usado: " + pacienteId);
                } else {
                    // Se não existir, insere
                    String sqlPaciente = "INSERT INTO paciente (nome, idade, cpf) VALUES (?, ?, ?)";
                    try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente, Statement.RETURN_GENERATED_KEYS)) {
                        stmtPaciente.setString(1, consulta.getPaciente().getNome());
                        stmtPaciente.setInt(2, consulta.getPaciente().getIdade());
                        stmtPaciente.setString(3, consulta.getPaciente().getCpf());
                        stmtPaciente.executeUpdate();

                        ResultSet rsPaciente = stmtPaciente.getGeneratedKeys();
                        rsPaciente.next();
                        pacienteId = rsPaciente.getInt(1);
                        System.out.println("Paciente cadastrado com sucesso. ID: " + pacienteId);
                    }
                }
            }

            // --- 2. Obter médico existente pelo CRM ---
            int medicoId;
            String sqlMedico = "SELECT id FROM medico WHERE crm = ?";
            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setString(1, consulta.getMedico().getCrm());
                ResultSet rsMedico = stmtMedico.executeQuery();
                if (rsMedico.next()) {
                    medicoId = rsMedico.getInt("id");
                } else {
                    throw new SQLException("Médico não encontrado no banco: " + consulta.getMedico().getNome());
                }
            }

            // --- 3. Salvar consulta ---
            String sqlConsulta = "INSERT INTO consulta (paciente_id, medico_id, data_consulta) VALUES (?, ?, ?)";
            try (PreparedStatement stmtConsulta = conn.prepareStatement(sqlConsulta)) {
                stmtConsulta.setInt(1, pacienteId);
                stmtConsulta.setInt(2, medicoId);
                stmtConsulta.setString(3, consulta.getDataConsulta()); // yyyy-MM-dd
                stmtConsulta.executeUpdate();
            }

            conn.commit(); // Confirma a transação
            System.out.println("Consulta salva com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
