package SISTEMACADASTRO;

import SISTEMACADASTRO.model.Cliente;
import SISTEMACADASTRO.model.Gerente;
import java.util.Scanner;

public class TesteCadastro {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Cliente clienteLogado = null;
        Gerente gerenteLogado = null;

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
                System.out.println("Voce esta acessando como visitante.");
                System.out.println("Fique a vontade para conferir nossos veículos");
                System.out.println("ai vai entra a parte de ver os carros e motos");
            }

            if (escolha == 2) {
                if (clienteLogado != null) {
                    System.out.println("Você já está logado como " + clienteLogado.getNome());
                } else {
                    clienteLogado = PainelCadastrado.iniciarU();
                }
            }

            if (escolha == 3) {
                if (gerenteLogado != null) {
                    System.out.println("Você já está logado como " + gerenteLogado.getNome());
                } else {
                    gerenteLogado = PainelGerente.iniciarG();
                }
            }

            if (escolha == 4) {
                PainelCadastro.iniciar();
            }

        } while (escolha != 5);

    }
}