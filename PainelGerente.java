package SISTEMACADASTRO;

import SISTEMACADASTRO.model.Gerente;
import java.util.Scanner;

public class PainelGerente {

    private static final String KEY = "123456";

    public static Gerente iniciarG() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Para prosseguir digite a chave de acesso:");
        String keyDigitada = scanner.nextLine();

        if (!KEY.equals(keyDigitada)) {
            System.out.println("Chave de acesso incorreta.");
            return null;
        }

        Gerente gerenteLogado = null;
        int escolhaGerente;

        do {
            System.out.println("\n--- Bem-vindo à tela de acesso Gerencial ---");
            System.out.println("1 - Login Gerencial");
            System.out.println("2 - Cadastrar conta Gerencial");
            System.out.println("3 - Sair");

            escolhaGerente = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolhaGerente) {

                case 1:
                    System.out.println("\n--- LOGIN GERENTE ---");
                    System.out.println("Email:");
                    String emailDigitado = scanner.nextLine();

                    System.out.println("Senha:");
                    String senhaDigitada = scanner.nextLine();

                    System.out.println("O login será conectado ao GerenteRepository.");
                    break;

                case 2:
                    System.out.println("\n--- CADASTRO GERENTE ---");

                    System.out.println("Digite o seu nome:");
                    String nome = scanner.nextLine();

                    System.out.println("Digite o seu email:");
                    String email = scanner.nextLine();

                    System.out.println("Digite a sua senha:");
                    String senhaGerente = scanner.nextLine();

                    // Criando o objeto com o construtor do modelo
                    Gerente gerente = new Gerente(
                            nome,
                            email,
                            senhaGerente
                    );

                    gerenteLogado = gerente; // Guarda a referência do gerente criado

                    System.out.println("Cadastro realizado com sucesso!");
                    System.out.println("Nome: " + gerente.getNome());
                    System.out.println("Email: " + gerente.getEmail());

                    break;

                case 3:
                    System.out.println("A sair do painel gerencial...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (escolhaGerente != 3);

        return gerenteLogado;
    }
}