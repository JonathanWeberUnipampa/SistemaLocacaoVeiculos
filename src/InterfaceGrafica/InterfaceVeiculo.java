

import SISTEMACADASTRO.CategoriaCNH;
import SISTEMACADASTRO.UsuarioCadastrado;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InterfaceVeiculo extends JFrame {
	private static final List<UsuarioCadastrado> clientes = new ArrayList<>();
	private final String nomeUsuario;
	private final String perfil;

	public InterfaceVeiculo(String nomeCliente, String perfil) {
		this.nomeUsuario = nomeCliente;
		this.perfil = perfil;
		setTitle("Locacao de Veiculos - " + perfil);
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		construirTela();
	}

	private void construirTela() {
		JPanel menu = new JPanel(new GridLayout(0, 1, 6, 6));
		menu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		menu.add(new JLabel("Ola, " + nomeUsuario));
		JButton veiculos = new JButton("Consultar veiculos");
		JButton locacao = new JButton("Nova locacao");
		JButton sair = new JButton("Sair");
		menu.add(veiculos);
		menu.add(locacao);

		JButton cadastro = null;
		JButton clientesButton = null;
		if ("Gerente".equals(perfil)) {
			cadastro = new JButton("Cadastrar cliente");
			clientesButton = new JButton("Clientes cadastrados");
			menu.add(cadastro);
			menu.add(clientesButton);
		}

		menu.add(sair);
		add(menu, BorderLayout.WEST);

		JPanel inicio = new JPanel(new BorderLayout(8, 8));
		inicio.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
		inicio.add(new JLabel("Escolha uma operacao no menu para continuar.", JLabel.CENTER), BorderLayout.CENTER);
		add(inicio, BorderLayout.CENTER);

		veiculos.addActionListener(event -> mostrarVeiculos());
		locacao.addActionListener(event -> realizarLocacao());
		if ("Gerente".equals(perfil)) {
			cadastro.addActionListener(event -> cadastrarCliente());
			clientesButton.addActionListener(event -> mostrarClientes());
		}
		sair.addActionListener(event -> {
			dispose();
			new TelaPrincipal().setVisible(true);
		});
	}

	private void cadastrarCliente() {
		JTextField nome = new JTextField();
		JTextField telefone = new JTextField();
		JTextField cnh = new JTextField();
		JComboBox<CategoriaCNH> categoria = new JComboBox<>(CategoriaCNH.values());
		JTextField endereco = new JTextField();
		Object[] campos = {"Nome:", nome, "Telefone:", telefone, "CNH:", cnh,
				"Categoria CNH:", categoria, "Endereco:", endereco};
		int resultado = JOptionPane.showConfirmDialog(this, campos, "Cadastro de cliente",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (resultado != JOptionPane.OK_OPTION || nome.getText().trim().isEmpty()) {
			return;
		}
		clientes.add(new UsuarioCadastrado(nome.getText().trim(), telefone.getText().trim(),
				cnh.getText().trim(), (CategoriaCNH) categoria.getSelectedItem(), endereco.getText().trim()));
		JOptionPane.showMessageDialog(this, "Cliente cadastrado com codigo " +
				clientes.get(clientes.size() - 1).getCodigo() + ".");
	}

	private void mostrarClientes() {
		DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Codigo", "Nome", "Telefone", "CNH", "Categoria"}, 0);
		for (UsuarioCadastrado cliente : clientes) {
			modelo.addRow(new Object[]{cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(),
					cliente.getCNH(), cliente.getCategoriaCNH()});
		}
		add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
		revalidate();
	}

	private void mostrarVeiculos() {
		DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Placa", "Tipo", "Marca", "Modelo", "Status"}, 0);
		modelo.addRow(new Object[]{"A DEFINIR", "Carro", "Cadastro pendente", "-", "Disponivel"});
		modelo.addRow(new Object[]{"A DEFINIR", "Moto", "Cadastro pendente", "-", "Disponivel"});
		add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
		revalidate();
	}

	private void realizarLocacao() {
		JTextField placa = new JTextField();
		JTextField inicio = new JTextField("dd/mm/aaaa");
		JTextField fim = new JTextField("dd/mm/aaaa");
		JComboBox<String> pagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartao", "Pix"});
		Object[] campos = {"Placa do veiculo:", placa, "Data inicial:", inicio,
				"Data final:", fim, "Pagamento:", pagamento};
		int resultado = JOptionPane.showConfirmDialog(this, campos, "Nova locacao",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (resultado == JOptionPane.OK_OPTION && !placa.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Solicitacao registrada para a placa " + placa.getText().trim() + ".");
		}
	}
}
