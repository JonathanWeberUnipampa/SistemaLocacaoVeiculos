package model;

import com.google.gson.annotations.SerializedName;
import SISTEMACADASTRO.CategoriaCNH;

public class Cliente {

    private Long id;

    @SerializedName("created_at")
    private String createdAt;

    private String nome;
    private String telefone;
    private String cnh;

    @SerializedName("categoria_cnh")
    private CategoriaCNH categoriaCnh;

    private String cep;

    @SerializedName("senha_cliente")
    private String senhaCliente;

    // Construtor sem argumentos (obrigatório para o Gson)
    public Cliente() {
    }

    // Construtor completo para criar instâncias facilmente
    public Cliente(String nome, String telefone, String cnh, CategoriaCNH categoriaCnh, String cep, String senhaCliente) {
        this.nome = nome;
        this.telefone = telefone;
        this.cnh = cnh;
        this.categoriaCnh = categoriaCnh;
        this.cep = cep;
        this.senhaCliente = senhaCliente;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public CategoriaCNH getCategoriaCnh() {
        return categoriaCnh;
    }

    public void setCategoriaCnh(CategoriaCNH categoriaCnh) {
        this.categoriaCnh = categoriaCnh;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }
}