package model;

public class Consulta {

    private Long id;
    private Paciente paciente;
    private Medico medico;
    private String dataConsulta;
    private String cpf;

    private String pacienteNome;
    private String medicoNome;
    private String especialidade;

    public Consulta(Paciente paciente, Medico medico, String dataConsulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
        this.cpf = paciente.getCpf();
    }

    public Consulta(Long id, String dataConsulta, String pacienteNome, String medicoNome, String especialidade, String cpf) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.pacienteNome = pacienteNome;
        this.medicoNome = medicoNome;
        this.especialidade = especialidade;
        this.cpf = cpf;
    }

    // Getters e setters
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        String nomePaciente = paciente != null ? paciente.getNome() : pacienteNome;
        String nomeMedico = medico != null ? medico.getNome() : medicoNome;
        String especialidadeMedico = medico != null ? medico.getEspecialidade() : especialidade;

        return "ID: " + id +
                " | Data: " + dataConsulta +
                " | Dr(a). " + (nomeMedico != null ? nomeMedico : "N/A") +
                " (" + (especialidadeMedico != null ? especialidadeMedico : "N/A") + ")" +
                " | Paciente: " + (nomePaciente != null ? nomePaciente : "N/A");
    }
}
