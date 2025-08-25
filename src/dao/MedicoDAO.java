package dao;

import model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public List<Medico> listarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT id, nome, especialidade, crm FROM medico";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicos.add(new Medico(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("crm")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar médicos: " + e.getMessage());
        }
        return medicos;
    }
}