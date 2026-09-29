import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaLocacao extends JFrame {
    private final JTextField placa = new JTextField();
    private final JTextField dataInicio = new JTextField("2026-10-01");
    private final JTextField dataFim = new JTextField("2026-10-02");
    private final JComboBox<String> pagamento = new JComboBox<>(new String[]{"dinheiro", "cartao", "pix"});
    public TelaLocacao(JFrame anterior) {
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
            VeiculoUI veiculo = AppData.veiculos.stream().filter(item -> item.placa.equalsIgnoreCase(placa.getText().trim()))
                    .findFirst().orElse(null);
            if (veiculo == null || !"disponivel".equals(veiculo.status)) { UiSupport.aviso(this, "Veiculo inexistente ou indisponivel."); return; }
            long dias = ChronoUnit.DAYS.between(LocalDate.parse(dataInicio.getText().trim()), LocalDate.parse(dataFim.getText().trim()));
            if (dias < 1) { UiSupport.aviso(this, "A data final deve ser posterior a inicial."); return; }
            double total = dias * veiculo.valorDiario;
            AppData.locacoes.add(new LocacaoUI(veiculo.placa, dataInicio.getText().trim(), dataFim.getText().trim(),
                    (String) pagamento.getSelectedItem(), (int) dias, total)); veiculo.status = "indisponivel";
            JOptionPane.showMessageDialog(this, String.format("Locacao registrada. Total: R$ %.2f", total)); dispose();
        } catch (RuntimeException erro) { UiSupport.aviso(this, "Use datas no formato AAAA-MM-DD."); }
    }
}