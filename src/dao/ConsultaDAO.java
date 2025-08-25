package dao;

import model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public boolean salvarConsulta(Consulta consulta) {
        String sql = "INSERT INTO consulta (paciente_id, medico_id, data_consulta, cpf) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, consulta.getPaciente().getId());
            stmt.setLong(2, consulta.getMedico().getId());
            stmt.setString(3, consulta.getDataConsulta());
            stmt.setString(4, consulta.getCpf());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        consulta.setId(rs.getLong(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar consulta: " + e.getMessage());
        }
        return false;
    }

    public List<Consulta> listarConsultasPorPacienteRetorno(String cpf) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.id, c.data_consulta, c.paciente_id, c.medico_id, c.cpf, " +
                "m.nome AS medico_nome, m.especialidade, p.nome AS paciente_nome " +
                "FROM consulta c " +
                "JOIN medico m ON c.medico_id = m.id " +
                "JOIN paciente p ON c.paciente_id = p.id " +
                "WHERE c.cpf = ? " +
                "ORDER BY c.data_consulta";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(new Consulta(
                            rs.getLong("id"),
                            rs.getString("data_consulta"),
                            rs.getString("paciente_nome"),
                            rs.getString("medico_nome"),
                            rs.getString("especialidade"),
                            rs.getString("cpf")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }
        return consultas;
    }

    public boolean editarConsulta(long id, String novaData) {
        String sql = "UPDATE consulta SET data_consulta = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaData);
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao editar consulta: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirConsulta(long id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir consulta: " + e.getMessage());
            return false;
        }
    }
}