package InterfaceGrafica;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class InterfaceVeiculo extends JFrame {
	public InterfaceVeiculo(String nomeCliente, String perfil) {
		setTitle("Locação de Veículos");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JLabel mensagem = new JLabel(
				"Bem-vindo(a), " + nomeCliente + "! Perfil: " + perfil,
				JLabel.CENTER);
		setLayout(new BorderLayout());
		add(mensagem, BorderLayout.CENTER);
	}
}
