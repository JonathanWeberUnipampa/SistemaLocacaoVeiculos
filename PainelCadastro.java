package SISTEMACADASTRO;
import java.util.Scanner;

public class PainelCadastro {
public static void iniciar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vamos começar seu cadastro !");
        System.out.println("Digite seu nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite seu login: ");
        String login = scanner.nextLine();
        System.out.println("Digite sua senha");
        String senha = scanner.nextLine();
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
}while (escolhaCat < 1 || escolhaCat > 3);
    CategoriaCNH categoriaCNH;
    if (escolhaCat == 1) {
        categoriaCNH = CategoriaCNH.A;
} else if (escolhaCat == 2) {
        categoriaCNH = CategoriaCNH.B;
} else {
        categoriaCNH = CategoriaCNH.AB;
    }

    System.out.println("Informe seu endereço:");
    String endereco = scanner.nextLine();
    UsuarioCadastrado usuario = new UsuarioCadastrado(
            nome,
            telefone,
            cnh,
            categoriaCNH,
            endereco,
            login,
            senha
    );
    UsuarioCadastrado.adicionarUsuario(usuario);
    System.out.println("Cadastro realizado com sucesso!:");
    System.out.println("Codigo:" + usuario.getCodigo());
    System.out.println("Nome:" + usuario.getnome());
    System.out.println("Login: " + usuario.getlogin());
    System.out.println("Tel:" + usuario.gettelefone());
    System.out.println("CNH:" + usuario.getcnh());
    System.out.println("CatCNH:" + usuario.getcategoriaCNH());
    System.out.println("Endereço:" + usuario.getendereco());
    System.out.println("Voltando a tela inicial...");
    }
}
