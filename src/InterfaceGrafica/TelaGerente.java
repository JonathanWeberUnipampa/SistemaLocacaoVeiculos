import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaGerente extends JFrame {
    public TelaGerente(model.Gerente gerente) {
        setTitle("Painel do gerente - " + gerente.getEmail()); setSize(720, 430); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); construirTela();
    }
    private void construirTela() {
        JPanel painel = new JPanel(new BorderLayout(10, 10)); painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        painel.add(new JLabel("Administracao da locadora", JLabel.CENTER), BorderLayout.NORTH);
        JPanel menu = new JPanel(new GridLayout(1, 4, 8, 8)); JButton clientes = new JButton("Clientes");
        JButton veiculos = new JButton("Veiculos"); JButton cadastrar = new JButton("Cadastrar veiculo"); JButton sair = new JButton("Sair");
        clientes.addActionListener(event -> mostrarClientes()); veiculos.addActionListener(event -> new TelaVeiculos(this).setVisible(true));
        cadastrar.addActionListener(event -> cadastrarVeiculo()); sair.addActionListener(event -> { dispose(); new TelaPrincipal().setVisible(true); });
        menu.add(clientes); menu.add(veiculos); menu.add(cadastrar); menu.add(sair); painel.add(menu, BorderLayout.SOUTH); setContentPane(painel);
    }
    private void mostrarClientes() {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "CNH", "Categoria", "Telefone"}, 0);
        try {
            for (model.Cliente cliente : AppServices.buscarClientes()) {
                modelo.addRow(new Object[]{cliente.getNome(), cliente.getCnh(), cliente.getCategoriaCnh(), cliente.getTelefone()});
            }
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel consultar os clientes.", erro);
            return;
        }
        JPanel painel = new JPanel(new BorderLayout()); painel.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        painel.setPreferredSize(new java.awt.Dimension(650, 280));
        javax.swing.JOptionPane.showMessageDialog(this, painel, "Clientes cadastrados", javax.swing.JOptionPane.PLAIN_MESSAGE);
    }
    private void cadastrarVeiculo() {
        JTextField placa = new JTextField(); JTextField marca = new JTextField(); JTextField modelo = new JTextField();
        JTextField ano = new JTextField(); JComboBox<String> tipo = new JComboBox<>(new String[]{"carro", "moto"}); JTextField valor = new JTextField();
        Object[] campos = {"Placa:", placa, "Tipo:", tipo, "Marca:", marca, "Modelo:", modelo, "Ano:", ano, "Valor diario:", valor};
        if (javax.swing.JOptionPane.showConfirmDialog(this, campos, "Cadastrar veiculo", javax.swing.JOptionPane.OK_CANCEL_OPTION)
                != javax.swing.JOptionPane.OK_OPTION) return;
        try {
            if (placa.getText().trim().isEmpty() || marca.getText().trim().isEmpty()) throw new IllegalArgumentException();
                model.Veiculo veiculo = new model.Veiculo();
                veiculo.setPlaca(placa.getText().trim());
                veiculo.setTipoVeiculo((String) tipo.getSelectedItem());
                veiculo.setMarca(marca.getText().trim());
                veiculo.setModelo(modelo.getText().trim());
                veiculo.setAno(Integer.valueOf(ano.getText().trim()));
                veiculo.setStatusVeiculo("disponivel");
                veiculo.setValorDiario(new java.math.BigDecimal(valor.getText().trim()));
                AppServices.cadastrarVeiculo(veiculo);
            javax.swing.JOptionPane.showMessageDialog(this, "Veiculo cadastrado com sucesso.");
        } catch (RuntimeException erro) { UiSupport.aviso(this, "Preencha os dados do veiculo corretamente."); }
    }
}