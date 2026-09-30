package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaCliente extends JFrame {
    private final model.Cliente cliente;
    public TelaCliente(model.Cliente cliente) {
        this.cliente = cliente;
        setTitle("Area do cliente"); setSize(520, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null); construirTela();
    }
    private void construirTela() {
        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.add(new JLabel("Bem-vindo, " + cliente.getNome(), JLabel.CENTER), BorderLayout.NORTH);
        JPanel menu = new JPanel(new GridLayout(3, 1, 8, 8));
        JButton veiculos = new JButton("Consultar veiculos");
        JButton locacao = new JButton("Realizar locacao");
        JButton sair = new JButton("Sair");
        veiculos.addActionListener(event -> new TelaVeiculos(this).setVisible(true));
        locacao.addActionListener(event -> new TelaLocacao(this, cliente).setVisible(true));
        sair.addActionListener(event -> { dispose(); new TelaPrincipal().setVisible(true); });
        menu.add(veiculos); menu.add(locacao); menu.add(sair); painel.add(menu, BorderLayout.CENTER);
        setContentPane(painel);
    }
}