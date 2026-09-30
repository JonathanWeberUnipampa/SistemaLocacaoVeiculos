import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaVeiculos extends JFrame {
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Placa", "Tipo", "Marca", "Modelo", "Status", "Valor diario"}, 0);
    private final JComboBox<String> filtro = new JComboBox<>(new String[]{"todos", "carro", "moto"});
    public TelaVeiculos(JFrame anterior) {
        setTitle("Veiculos"); setSize(760, 420); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(anterior);
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT)); JButton buscar = new JButton("Buscar");
        buscar.addActionListener(event -> atualizarTabela()); topo.add(filtro); topo.add(buscar);
        painel.add(topo, BorderLayout.NORTH); painel.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        setContentPane(painel); atualizarTabela();
    }
    private void atualizarTabela() {
        modelo.setRowCount(0);
        String tipo = "todos".equals(filtro.getSelectedItem()) ? null : (String) filtro.getSelectedItem();
        try {
            List<model.Veiculo> veiculos = AppServices.buscarVeiculos(tipo, null);
            for (model.Veiculo veiculo : veiculos) {
                modelo.addRow(new Object[]{veiculo.getPlaca(), veiculo.getTipoVeiculo(), veiculo.getMarca(),
                        veiculo.getModelo(), veiculo.getStatusVeiculo(), veiculo.getValorDiario()});
            }
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel consultar os veiculos.", erro);
        }
    }
}