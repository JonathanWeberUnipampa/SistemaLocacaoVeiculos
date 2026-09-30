import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TelaLoginGerente extends JFrame {
    private final JTextField email = new JTextField(); private final JPasswordField senha = new JPasswordField();
    public TelaLoginGerente() {
        setTitle("Login do gerente"); setSize(420, 250); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24)); JPanel campos = new JPanel(new GridLayout(2, 2, 8, 8));
        campos.add(new JLabel("E-mail:")); campos.add(email); campos.add(new JLabel("Senha:")); campos.add(senha); painel.add(campos, BorderLayout.CENTER);
        JButton entrar = new JButton("Entrar"); entrar.addActionListener(event -> entrar()); painel.add(entrar, BorderLayout.SOUTH); setContentPane(painel);
    }
    private void entrar() {
        if (email.getText().trim().isEmpty() || senha.getPassword().length == 0) { UiSupport.aviso(this, "Informe e-mail e senha."); return; }
        try {
            model.Gerente gerente = AppServices.autenticarGerente(email.getText().trim(), new String(senha.getPassword()));
            if (gerente == null) { UiSupport.aviso(this, "Email ou senha invalidos."); return; }
            new TelaGerente(gerente).setVisible(true);
            dispose();
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel acessar o banco de dados.", erro);
        }
    }
}