package SISTEMACADASTRO;
import java.util.ArrayList;

public class UsuarioCadastrado {
    private static ArrayList<UsuarioCadastrado> usuarios = new ArrayList<>();
    private static int ProximoCodigo = 1;
    public static void adicionarUsuario(UsuarioCadastrado usuario) {
        usuarios.add(usuario);
    }
    public static boolean verificarLogin(String loginDigitado, String senhaDigitada){
        for (UsuarioCadastrado usuario : usuarios){
            if (usuario.getlogin().equals(loginDigitado) && usuario.getsenha().equals(senhaDigitada)) {
            return true;
            }
        } return false;
    }
    public static UsuarioCadastrado buscarUsuario(String loginDigitado, String senhaDigitada) {
        for (UsuarioCadastrado usuario : usuarios){
            if (usuario.getlogin().equals(loginDigitado) && usuario.getsenha().equals(senhaDigitada)) {
                return usuario;
            }
        }
        return null;
    }
    private int codigo;
    private String nome;
    private String telefone;
    private String cnh;
    private CategoriaCNH categoriaCNH;
    private String endereco;
    private String login;
    private String senha;

    public UsuarioCadastrado(String nome, String telefone, String cnh, CategoriaCNH categoriaCNH, String endereco, String login, String senha){
        this.codigo = ProximoCodigo;
        ProximoCodigo++;
        this.nome = nome;
        this.telefone = telefone;
        this.cnh = cnh;
        this.categoriaCNH = categoriaCNH;
        this.endereco = endereco;
        this.login = login;
        this.senha = senha;

    }
    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getnome() {
        return nome;
    }
    public void setnome(String nome) {
        this.nome = nome;
    }

    public String gettelefone(){
        return telefone;
    }
    public void settelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getcnh(){
        return cnh;
    }
    public void setcnh(String cnh) {
        this.cnh = cnh;
    }

    public CategoriaCNH getcategoriaCNH() {
        return categoriaCNH;
    }
    public void setcategoriaCNH(CategoriaCNH categoriaCNH) {
        this.categoriaCNH = categoriaCNH;
    }

    public String getendereco(){
        return endereco;
    }
    public void setendereco(String endereco){
        this.endereco = endereco;
    }

    public String getlogin(){return login;}
    public void setlogin(String login){this.login = login;}

    public String getsenha(){return senha;}
    public void setsenha(){this.senha = senha;}

}
