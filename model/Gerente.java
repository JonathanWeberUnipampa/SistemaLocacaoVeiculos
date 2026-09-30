package SISTEMACADASTRO.model;

import com.google.gson.annotations.SerializedName;

public class Gerente {

    private Long id;

    @SerializedName("created_at")
    private String createdAt;

    private String nome;
    private String email;

    @SerializedName("senha_gerente")
    private String senhaGerente;


    public Gerente() {
    }


    public Gerente(String nome, String email, String senhaGerente) {
        this.nome = nome;
        this.email = email;
        this.senhaGerente = senhaGerente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaGerente() {
        return senhaGerente;
    }

    public void setSenhaGerente(String senhaGerente) {
        this.senhaGerente = senhaGerente;
    }
}