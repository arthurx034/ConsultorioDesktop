package model;

public class Paciente {
    private Long id;
    private String nome;
    private int idade;
    private String cpf;

    public Paciente(Long id, String nome, int idade, String cpf) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    public Paciente(String nome, int idade, String cpf) {
        this(null, nome, idade, cpf);
    }

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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Paciente: " + (nome != null ? nome : "N/A") +
                " | Idade: " + idade +
                " | CPF: " + (cpf != null ? cpf : "N/A");
    }
}