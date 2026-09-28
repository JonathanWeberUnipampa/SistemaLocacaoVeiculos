package InterfaceGrafica;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    private final String perfil = "Cliente";

    public TelaPrincipal() {
        setTitle("Sistema de Locação de Veículos");
        setSize(460, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(criarTelaLogin());
    }

    public boolean temPerfilSelecionado() {
        return perfil != null;
    }

    private JPanel criarTelaLogin() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Acesso ao sistema");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        painel.add(titulo, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        painel.add(new JLabel("Usuario:"), constraints);

        constraints.gridx = 1;
        painel.add(campoUsuario, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        painel.add(new JLabel("Senha:"), constraints);

        constraints.gridx = 1;
        painel.add(campoSenha, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        painel.add(new JLabel("Perfil:"), constraints);

        constraints.gridx = 1;
        painel.add(comboPerfil, constraints);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener(event -> entrar());
        campoUsuario.addActionListener(event -> entrar());

        constraints.gridx = 1;
        constraints.gridy = 4;
        painel.add(botaoEntrar, constraints);

        return painel;
    }

    private void entrar() {
        String nome = campoUsuario.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o nome do usuario para entrar.",
                    "Nome obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            campoUsuario.requestFocusInWindow();
            return;
        }

        InterfaceVeiculo interfaceVeiculo = new InterfaceVeiculo(nome, (String) comboPerfil.getSelectedItem());
        interfaceVeiculo.setVisible(true);
        dispose();
    }

}

