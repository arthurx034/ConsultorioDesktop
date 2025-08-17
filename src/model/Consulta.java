package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Consulta {
    private Long id;
    private Paciente paciente;
    private Medico medico;
    private String dataConsulta; // armazenada como yyyy-MM-dd (formato MySQL)

    // Construtor completo (com ID)
    public Consulta(Long id, Paciente paciente, Medico medico, String dataConsulta) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
    }

    // Construtor sem ID (antes de salvar no banco)
    public Consulta(Paciente paciente, Medico medico, String dataConsulta) {
        this(null, paciente, medico, dataConsulta);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
}
