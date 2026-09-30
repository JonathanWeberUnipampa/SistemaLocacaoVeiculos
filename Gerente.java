package SISTEMACADASTRO;

public class Gerente {
    private static final String Key = "123456";        //senha pra entrar no painel do gerente
    public static boolean verificarKey (String KeyDigitada){
        return Key.equals(KeyDigitada);
    }
}
