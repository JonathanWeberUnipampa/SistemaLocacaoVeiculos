package SISTEMACADASTRO;
import java.util.Scanner;

public class TesteCadastro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioCadastrado usuarioLogado = null;
        GerenteCadastrado gerenteLogado = null;
        int escolha;
        do {
            System.out.println("Bem-vindo a locadora de veiculos dos cabra");
            System.out.println("Escolha seu tipo de acesso:");
            System.out.println("1 - Visitante");
            System.out.println("2 - Cadastrado");
            System.out.println("3 - Gerenciamento");
            System.out.println("4 - Criar Conta");
            System.out.println("5 - Sair");
            escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha == 1) {
                Usuario usuario = new Usuario();
                System.out.println("Voce esta acessando como visitante.");
                System.out.println("Fique a vontade para conferir nossos veículos");
                System.out.println("ai vai entra a parte de ver os carros e motos");
            }
            if (escolha == 2) {
                if (usuarioLogado != null) {
                    System.out.println("Você já está logado como " + usuarioLogado.getnome());
                } else {
                    usuarioLogado = PainelCadastrado.iniciarU();
                }
            }
                if (escolha == 3) {
                    if (gerenteLogado != null) {
                        System.out.println("Você já está logado como " + gerenteLogado.getnome());
                    } else {
                        gerenteLogado = PainelGerente.iniciarG();
                    }
                }
                    if (escolha == 4) {
                        PainelCadastro.iniciar();
                    }
                }
                while (escolha != 5);

            }
        }