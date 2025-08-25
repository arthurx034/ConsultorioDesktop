package model;

public class Medico {
    private Long id;
    private String nome;
    private String especialidade;
    private String crm;

    public Medico(Long id, String nome, String especialidade, String crm) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.crm = crm;
    }

    public Medico(String nome, String especialidade, String crm) {
        this(null, nome, especialidade, crm);
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public String toString() {
        return "Dr(a). " + (nome != null ? nome : "N/A") +
                " | Especialidade: " + (especialidade != null ? especialidade : "N/A") +
                " | CRM: " + (crm != null ? crm : "N/A");
    }
}
