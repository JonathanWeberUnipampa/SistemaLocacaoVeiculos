import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TelaCadastroCliente extends JFrame {
    private final JTextField nome = new JTextField();
    private final JTextField telefone = new JTextField();
    private final JTextField cnh = new JTextField();
    private final JComboBox<String> categoria = new JComboBox<>(new String[]{"A", "B", "AB"});
    private final JTextField cep = new JTextField();
    private final JPasswordField senha = new JPasswordField();
    public TelaCadastroCliente() {
        setTitle("Cadastro de cliente"); setSize(500, 390);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); setLocationRelativeTo(null); construirTela();
    }
    private void construirTela() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        JPanel campos = new JPanel(new GridLayout(6, 2, 8, 8));
        campos.add(new JLabel("Nome:")); campos.add(nome); campos.add(new JLabel("Telefone:")); campos.add(telefone);
        campos.add(new JLabel("CNH:")); campos.add(cnh); campos.add(new JLabel("Categoria CNH:")); campos.add(categoria);
        campos.add(new JLabel("CEP:")); campos.add(cep); campos.add(new JLabel("Senha:")); campos.add(senha);
        painel.add(campos, BorderLayout.CENTER);
        JPanel botoes = new JPanel(new GridLayout(1, 2, 8, 8));
        JButton salvar = new JButton("Cadastrar"); JButton voltar = new JButton("Voltar");
        salvar.addActionListener(event -> salvar()); voltar.addActionListener(event -> dispose());
        botoes.add(salvar); botoes.add(voltar); painel.add(botoes, BorderLayout.SOUTH); setContentPane(painel);
    }
    private void salvar() {
        if (nome.getText().trim().isEmpty() || cnh.getText().trim().isEmpty() || senha.getPassword().length == 0) {
            UiSupport.aviso(this, "Nome, CNH e senha sao obrigatorios."); return;
        }
        AppData.clientes.add(new ClienteUI(nome.getText().trim(), telefone.getText().trim(), cnh.getText().trim(),
                (String) categoria.getSelectedItem(), cep.getText().trim(), new String(senha.getPassword())));
        javax.swing.JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso."); dispose();
    }
}