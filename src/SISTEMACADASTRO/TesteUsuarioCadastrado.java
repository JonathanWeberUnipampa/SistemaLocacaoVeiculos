package SISTEMACADASTRO;
import java.util.Scanner;

public class TesteUsuarioCadastrado {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
            System.out.println("Deseja se cadastrar agora ?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
                int escolha = scanner.nextInt();
                 scanner.nextLine();
                 if (escolha == 1) {
                     System.out.println("Vamos começar seu cadastro !");
                     System.out.println("Digite seu nome:");
                     String nome = scanner.nextLine();
                     System.out.println("Digite seu telefone:");
                     String Telefone = scanner.nextLine();
                     System.out.println("Informe sua CNH:");
                     String CNH = scanner.nextLine();

                     int escolhaCat;
                     do {
                         System.out.println("Informe a categoria da CNH:");
                         System.out.println("1 = A");
                         System.out.println("2 = B");
                         System.out.println("3 = AB");

                         escolhaCat = scanner.nextInt();
                         scanner.nextLine();
                     } while (escolhaCat < 1 || escolhaCat > 3);

                     CategoriaCNH categoriaCNH;
                     if (escolhaCat == 1) {
                         categoriaCNH = CategoriaCNH.A;
                     } else if (escolhaCat == 2) {
                         categoriaCNH = CategoriaCNH.B;
                     } else {
                         categoriaCNH = CategoriaCNH.AB;
                     }

                     System.out.println("Informe seu endereço:");
                     String Endereco = scanner.nextLine();
                     UsuarioCadastrado Usuario = new UsuarioCadastrado(
                             nome,
                             Telefone,
                             CNH,
                             categoriaCNH,
                             Endereco
                     );
                     System.out.println("Cadastro realizado com sucesso!:");
                     System.out.println("Codigo:" + Usuario.getCodigo());
                     System.out.println("Nome:" + Usuario.getNome());
                     System.out.println("Tel:" + Usuario.getTelefone());
                     System.out.println("CNH:" + Usuario.getCNH());
                     System.out.println("CatCNH:" + Usuario.getCategoriaCNH());
                     System.out.println("Endereço:" + Usuario.getEndereco());
        } else {
                     System.out.println("Voce esta acessando como visitante.");
                     Usuario usuario = new Usuario();
                 }

    }
}
