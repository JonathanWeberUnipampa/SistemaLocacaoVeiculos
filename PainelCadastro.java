package SISTEMACADASTRO;

import SISTEMACADASTRO.model.Cliente;
import java.util.Scanner;

public class PainelCadastro {

    public static void iniciar() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Vamos começar seu cadastro !");

        System.out.println("Digite seu nome:");
        String nome = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String senhaCliente = scanner.nextLine();

        System.out.println("Digite seu telefone:");
        String telefone = scanner.nextLine();

        System.out.println("Informe sua CNH:");
        String cnh = scanner.nextLine();

        int escolhaCat;

        do {
            System.out.println("Informe a categoria da CNH:");
            System.out.println("1 = A");
            System.out.println("2 = B");
            System.out.println("3 = AB");

            escolhaCat = scanner.nextInt();
            scanner.nextLine();

        } while (escolhaCat < 1 || escolhaCat > 3);

        String categoriaCNH;

        if (escolhaCat == 1) {
            categoriaCNH = "A";
        } else if (escolhaCat == 2) {
            categoriaCNH = "B";
        } else {
            categoriaCNH = "AB";
        }

        System.out.println("Informe seu CEP:");
        String cep = scanner.nextLine();

        Cliente cliente = new Cliente(
                nome,
                telefone,
                cnh,
                categoriaCNH,
                cep,
                senhaCliente
        );

        System.out.println("Cadastro realizado com sucesso!");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("CNH: " + cliente.getCnh());
        System.out.println("Categoria: " + cliente.getCategoriaCnh());
        System.out.println("CEP: " + cliente.getCep());

        System.out.println("Voltando a tela inicial...");
    }
}