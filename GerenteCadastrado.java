package SISTEMACADASTRO;
import java.util.ArrayList;

public class GerenteCadastrado {
    private static ArrayList<GerenteCadastrado> gerentes = new ArrayList<>();
    private static int ProximocodigoG = 1;
    public static void adicionarGerente(GerenteCadastrado gerente) {
        gerentes.add(gerente);
    }
    public static boolean verificarLogin(String loginDigitado, String senhaDigitada) {
        for (GerenteCadastrado gerente : gerentes) {
            if (gerente.getLogin().equals(loginDigitado) && gerente.getsenha().equals(senhaDigitada)) {
            return true;
            }
        }
        return false;
    }
    public static GerenteCadastrado buscarGerente(String loginDigitado, String senhaDigitada) {
    for (GerenteCadastrado gerente : gerentes){
        if (gerente.getLogin().equals(loginDigitado) && gerente.getsenha().equals(senhaDigitada)) {
         return gerente;
        }
    }
         return null;
    }
    private int codigoG;
    private String nome;
    private String login;
    private String senha;

    public GerenteCadastrado(String nome, String login, String senha){
        this.codigoG = ProximocodigoG;
        ProximocodigoG++;
        this.nome = nome;
        this.login = login;
        this.senha = senha;

    }
    public int getcodigoG(){
        return codigoG;
    }
    public void setcodigoG(int codigoG) {
        this.codigoG = codigoG;
    }

    public String getnome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getsenha(){
        return senha;
    }
    public void setsenha(String senha) {
        this.senha = senha;}

}

