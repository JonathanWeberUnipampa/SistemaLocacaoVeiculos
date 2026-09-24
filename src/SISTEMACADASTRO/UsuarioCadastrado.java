package SISTEMACADASTRO;

public class UsuarioCadastrado {

    private static int ProximoCodigo = 1;

    private int codigo;
    private String nome;
    private String telefone;
    private String CNH;
    private CategoriaCNH categoriaCNH;
    private String Endereco;

    public UsuarioCadastrado(String nome, String telefone, String CNH, CategoriaCNH categoriaCNH, String Endereco){
        this.codigo = ProximoCodigo;
        ProximoCodigo++;

        this.nome = nome;
        this.telefone = telefone;
        this.CNH = CNH;
        this.categoriaCNH = categoriaCNH;
        this.Endereco = Endereco;

    }
    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone(){
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCNH(){
        return CNH;
    }
    public void setCNH(String CNH) {
        this.CNH = CNH;
    }

    public CategoriaCNH getCategoriaCNH() {
        return categoriaCNH;
    }
    public void setCategoriaCNH(CategoriaCNH categoriaCNH) {
        this.categoriaCNH = categoriaCNH;
    }

    public String getEndereco(){
        return Endereco;
    }
    public void setEndereco(String Endereco){
        this.Endereco = Endereco;
    }

}
