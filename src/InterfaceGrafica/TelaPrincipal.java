package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
        String senha = new String(campoSenha.getPassword());
        if (usuario.isEmpty() || senha.isEmpty()) {
            UiSupport.aviso(this, "Informe o usuario e a senha.");
            campoUsuario.requestFocusInWindow();
            return;
        }
        try {
            if ("Gerente".equals(comboPerfil.getSelectedItem())) {
                model.Gerente gerente = AppServices.autenticarGerente(usuario, senha);
                if (gerente == null) {
                    UiSupport.aviso(this, "Email ou senha invalidos.");
                    return;
                }
                abrir(new TelaGerente(gerente));
            } else {
                model.Cliente cliente = AppServices.autenticarCliente(usuario, senha);
                if (cliente == null) {
                    UiSupport.aviso(this, "CNH ou senha invalidas.");
                    return;
                }
                abrir(new TelaCliente(cliente));
            }
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel acessar o banco de dados.", erro);
        }
    }

    private void abrir(JFrame tela) { tela.setVisible(true); dispose(); }
}

class UiSupport {
    static void aviso(JFrame parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Atencao", JOptionPane.WARNING_MESSAGE);
    }
    static void erro(JFrame parent, String mensagem, RuntimeException erro) {
        String detalhe = erro.getMessage() == null ? "Erro desconhecido." : erro.getMessage();
        JOptionPane.showMessageDialog(parent, mensagem + "\n" + detalhe, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

final class AppServices {
    private static final repository.ClienteRepository CLIENTES = new repository.ClienteRepository();
    private static final repository.GerenteRepository GERENTES = new repository.GerenteRepository();
    private static final repository.VeiculoRepository VEICULOS = new repository.VeiculoRepository();
    private static final repository.LocacaoRepository LOCACOES = new repository.LocacaoRepository();

    private AppServices() { }

    static model.Cliente autenticarCliente(String cnh, String senha) {
        return CLIENTES.autenticarCliente(cnh, senha);
    }

    static model.Gerente autenticarGerente(String email, String senha) {
        return GERENTES.autenticarGerente(email, senha);
    }

    static model.Cliente cadastrarCliente(model.Cliente cliente) {
        return CLIENTES.cadastrarCliente(cliente);
    }

    static java.util.List<model.Cliente> buscarClientes() {
        return CLIENTES.buscarClientes(null, null, null, null, null);
    }

    static java.util.List<model.Veiculo> buscarVeiculos(String tipo, String status) {
        return VEICULOS.buscarVeiculos(tipo, status, null, null, null);
    }

    static model.Veiculo cadastrarVeiculo(model.Veiculo veiculo) {
        return VEICULOS.cadastrarVeiculo(veiculo);
    }

    static model.Veiculo editarVeiculo(long id, java.util.Map<String, Object> dadosAtualizados) {
        return VEICULOS.editarVeiculo(id, dadosAtualizados);
    }

    static model.Locacao cadastrarLocacao(model.Locacao locacao) {
        return LOCACOES.cadastrarLocacao(locacao);
    }
}
