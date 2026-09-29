package SISTEMACADASTRO;
import java.util.Scanner;

public class PainelGerente {
    public static void iniciarG() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Para prosseguir digite a chave de acesso:");
        String KeyDigitada = scanner.nextLine();
        boolean permitirAcesso = Gerente.verificarKey(KeyDigitada);
        if (permitirAcesso) {
            int escolhaGerente;
            do {
                System.out.println("Bem-vindo a tela de acesso Gerencial");
                System.out.println("1- Login Gerencial");
                System.out.println("2- Cadastrar conta Gerencial");
                System.out.println("3- Sair");
                escolhaGerente = scanner.nextInt();
                scanner.nextLine();
                switch (escolhaGerente) {
                    case 1:
                        System.out.println("Login: ");
                        String loginDigitado = scanner.nextLine();

                        System.out.println("Senha: ");
                        String senhaDigitada = scanner.nextLine();

                        GerenteCadastrado gerenteLogado = GerenteCadastrado.buscarGerente(loginDigitado, senhaDigitada);
                        if (gerenteLogado != null) {
                            System.out.println("Login Realizado com sucesso");
                            System.out.println("Bem-Vindo a nossa plataforma " + gerenteLogado.getnome());
                        } else {
                            System.out.println("Falha ao logar dados incompativeis ou conta inexistente");
                        }
                        break;

                    case 2:
                        System.out.println("Vamos começar seu cadastro Gerencial:");
                        System.out.println("Digite seu nome:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite seu login:");
                        String login = scanner.nextLine();
                        System.out.println("Digite sua senha:");
                        String senha = scanner.nextLine();
                        GerenteCadastrado gerente = new GerenteCadastrado(
                                nome,
                                login,
                                senha
                        );
                        System.out.println("Cadastro realizado com sucesso");
                        GerenteCadastrado.adicionarGerente(gerente);
                        System.out.println("Codigo: " + gerente.getcodigoG());
                        System.out.println("Nome: " + gerente.getnome());
                        System.out.println("Login: " + gerente.getLogin());
                        break;
                }
            } while (escolhaGerente != 3);
        }
    }
}