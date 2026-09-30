import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaLocacao extends JFrame {
    private final model.Cliente cliente;
    private final JTextField placa = new JTextField();
    private final JTextField dataInicio = new JTextField();
    private final JTextField dataFim = new JTextField();
    private final JComboBox<String> pagamento = new JComboBox<>(new String[]{"dinheiro", "cartao", "pix"});
    public TelaLocacao(JFrame anterior, model.Cliente cliente) {
        this.cliente = cliente;
        setTitle("Nova locacao"); setSize(470, 300); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(anterior); construirTela();
    }
    private void construirTela() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JPanel campos = new JPanel(new GridLayout(4, 2, 8, 8));
        campos.add(new JLabel("Placa:")); campos.add(placa); campos.add(new JLabel("Data inicio (AAAA-MM-DD):")); campos.add(dataInicio);
        campos.add(new JLabel("Data fim (AAAA-MM-DD):")); campos.add(dataFim); campos.add(new JLabel("Forma de pagamento:")); campos.add(pagamento);
        painel.add(campos, BorderLayout.CENTER); JButton confirmar = new JButton("Confirmar locacao");
        confirmar.addActionListener(event -> confirmar()); painel.add(confirmar, BorderLayout.SOUTH); setContentPane(painel);
    }
    private void confirmar() {
        try {
            model.Locacao locacao = new model.Locacao();
            locacao.setClienteId(cliente.getId());
            locacao.setVeiculoPlaca(placa.getText().trim());
            locacao.setDataInicio(dataInicio.getText().trim());
            locacao.setDataFim(dataFim.getText().trim());
            locacao.setFormaPagamento((String) pagamento.getSelectedItem());
            AppServices.cadastrarLocacao(locacao);
            JOptionPane.showMessageDialog(this, "Locacao registrada com sucesso.");
            dispose();
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel registrar a locacao.", erro);
        }
    }
}