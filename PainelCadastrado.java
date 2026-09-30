package SISTEMACADASTRO;

import SISTEMACADASTRO.model.Cliente;
import java.util.Scanner;

public class PainelCadastrado {

    public static Cliente iniciarU() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- LOGIN DE CLIENTE ---");
        System.out.println("Digite o seu nome:");
        String nomeDigitado = scanner.nextLine();

        System.out.println("Senha: ");
        String senhaDigitada = scanner.nextLine();

        System.out.println("A autenticação do cliente será conectada ao ClienteRepository.");

        return null;
    }
}