

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class TelaPrincipal extends JFrame {
    private final JTextField campoUsuario = new JTextField(20);
    private final JPasswordField campoSenha = new JPasswordField(20);
    private final JComboBox<String> comboPerfil = new JComboBox<>(new String[]{"Cliente", "Gerente"});

    public TelaPrincipal() {
        setTitle("Sistema de Locacao de Veiculos");
        setSize(460, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(criarPainel());
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        painel.add(new JLabel("Acesso ao sistema"));
        painel.add(new JPanel());
        painel.add(new JLabel("Usuario:"));
        painel.add(campoUsuario);
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);
        painel.add(new JLabel("Perfil:"));
        painel.add(comboPerfil);
        JButton entrar = new JButton("Entrar");
        JButton cadastrar = new JButton("Criar cadastro");
        entrar.addActionListener(event -> entrar());
        campoUsuario.addActionListener(event -> entrar());
        cadastrar.addActionListener(event -> abrir(new TelaCadastroCliente()));
        painel.add(entrar);
        painel.add(cadastrar);
        return painel;
    }

    private void entrar() {
        String usuario = campoUsuario.getText().trim();
        if (usuario.isEmpty() || campoSenha.getPassword().length == 0) {
            UiSupport.aviso(this, "Informe o usuario e a senha.");
            campoUsuario.requestFocusInWindow();
            return;
        }
        if ("Gerente".equals(comboPerfil.getSelectedItem())) {
            abrir(new TelaGerente(usuario));
        } else {
            abrir(new TelaCliente(usuario));
        }
    }

    private void abrir(JFrame tela) { tela.setVisible(true); dispose(); }
}

class UiSupport {
    static void aviso(JFrame parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Atencao", JOptionPane.WARNING_MESSAGE);
    }
}

class AppData {
    static final List<ClienteUI> clientes = new ArrayList<>();
    static final List<VeiculoUI> veiculos = new ArrayList<>();
    static final List<LocacaoUI> locacoes = new ArrayList<>();
    static {
        veiculos.add(new VeiculoUI("ABC-1234", "carro", "Fiat", "Argo", "disponivel", 150.0));
        veiculos.add(new VeiculoUI("XYZ-9876", "moto", "Honda", "CG 160", "disponivel", 85.0));
    }
}

class ClienteUI {
    String nome, telefone, cnh, categoriaCnh, cep, senha;
    ClienteUI(String nome, String telefone, String cnh, String categoriaCnh, String cep, String senha) {
        this.nome = nome; this.telefone = telefone; this.cnh = cnh;
        this.categoriaCnh = categoriaCnh; this.cep = cep; this.senha = senha;
    }
}

class VeiculoUI {
    String placa, tipo, marca, modelo, status;
    double valorDiario;
    VeiculoUI(String placa, String tipo, String marca, String modelo, String status, double valorDiario) {
        this.placa = placa; this.tipo = tipo; this.marca = marca; this.modelo = modelo;
        this.status = status; this.valorDiario = valorDiario;
    }
}

class LocacaoUI {
    String placa, inicio, fim, pagamento;
    int dias;
    double total;
    LocacaoUI(String placa, String inicio, String fim, String pagamento, int dias, double total) {
        this.placa = placa; this.inicio = inicio; this.fim = fim; this.pagamento = pagamento;
        this.dias = dias; this.total = total;
    }
}

