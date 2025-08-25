package dao;

import model.Paciente;

import java.sql.*;

public class PacienteDAO {

    public Paciente salvarPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente (nome, idade, cpf) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getIdade());
            stmt.setString(3, paciente.getCpf());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar paciente: " + e.getMessage());
        }
        return paciente;
    }

    public Paciente buscarPorCpf(String cpf) {
        String sql = "SELECT id, nome, idade, cpf FROM paciente WHERE cpf = ?";
        Paciente paciente = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("cpf")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar paciente: " + e.getMessage());
        }
        return paciente;
    }
}