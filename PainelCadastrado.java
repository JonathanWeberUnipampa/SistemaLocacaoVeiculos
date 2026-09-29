package SISTEMACADASTRO;

import java.util.Scanner;

public class PainelCadastrado {
    public static UsuarioCadastrado iniciarU(){
    Scanner scanner = new Scanner (System.in);
    System.out.println("Login: ");
    String loginDigitado = scanner.nextLine();

    System.out.println("Senha: ");
    String senhaDigitada = scanner.nextLine();

    UsuarioCadastrado usuariologado = UsuarioCadastrado.buscarUsuario(loginDigitado, senhaDigitada);
    if (usuariologado != null){
        System.out.println("Bem-vindo a nossa paltaforma," + usuariologado.getnome());
       }
    return usuariologado;
    }
}
