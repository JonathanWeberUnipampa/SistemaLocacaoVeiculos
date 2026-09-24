package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class TelaPrincipal extends JFrame {
    private final JTextField campoNome;
    private final String perfil;

    public TelaPrincipal() {
        setTitle("Sistema de Locação de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoNome = new JTextField(20);
        perfil = selecionarPerfil();
        setContentPane(criarTelaLogin());
    }

    private String selecionarPerfil() {
        String[] opcoesPerfil = {"Cliente", "Gerente"};
        int perfilSelecionado = JOptionPane.showOptionDialog(
                this,
                "Você está entrando como cliente ou gerente?",
                "Tipo de usuário",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesPerfil,
                opcoesPerfil[0]);

        if (perfilSelecionado == JOptionPane.CLOSED_OPTION) {
            return null;
        }

        return opcoesPerfil[perfilSelecionado];
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

        JLabel titulo = new JLabel("Login do cliente");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        painel.add(titulo, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        painel.add(new JLabel("Nome:"), constraints);

        constraints.gridx = 1;
        painel.add(campoNome, constraints);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener(event -> entrar());
        campoNome.addActionListener(event -> entrar());

        constraints.gridx = 1;
        constraints.gridy = 2;
        painel.add(botaoEntrar, constraints);

        return painel;
    }

    private void entrar() {
        String nome = campoNome.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o nome do usuário para entrar.",
                    "Nome obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocusInWindow();
            return;
        }

        InterfaceVeiculo interfaceVeiculo = new InterfaceVeiculo(nome, perfil);
        interfaceVeiculo.setVisible(true);
        dispose();
    }

}

