package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaVeiculos extends JFrame {
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Placa", "Tipo", "Marca", "Modelo", "Status", "Valor diario"}, 0);
    private final JTable tabela = new JTable(modelo);
    private final JComboBox<String> filtro = new JComboBox<>(new String[]{"todos", "carro", "moto"});
    private List<model.Veiculo> veiculosAtuais = new ArrayList<>();

    public TelaVeiculos(JFrame anterior) {
        setTitle("Veiculos");
        setSize(760, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(anterior);

        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton buscar = new JButton("Buscar");
        JButton salvar = new JButton("Salvar alteracoes");

        buscar.addActionListener(event -> atualizarTabela());
        salvar.addActionListener(event -> salvarAlteracoes());

        topo.add(filtro);
        topo.add(buscar);
        topo.add(salvar);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        setContentPane(painel);
        atualizarTabela();
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        String tipo = "todos".equals(filtro.getSelectedItem()) ? null : (String) filtro.getSelectedItem();

        try {
            veiculosAtuais = AppServices.buscarVeiculos(tipo, null);
            for (model.Veiculo veiculo : veiculosAtuais) {
                modelo.addRow(new Object[]{
                        veiculo.getPlaca(),
                        veiculo.getTipoVeiculo(),
                        veiculo.getMarca(),
                        veiculo.getModelo(),
                        veiculo.getStatusVeiculo(),
                        veiculo.getValorDiario()
                });
            }
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel consultar os veiculos.", erro);
        }
    }

    private void salvarAlteracoes() {
        if (tabela.isEditing()) {
            tabela.getCellEditor().stopCellEditing();
        }

        try {
            List<Map<String, Object>> alteracoesPorLinha = new ArrayList<>();

            for (int linha = 0; linha < modelo.getRowCount(); linha++) {
                String placa = valorDaCelula(linha, 0);
                String tipo = valorDaCelula(linha, 1).toLowerCase();
                String marca = valorDaCelula(linha, 2);
                String nomeModelo = valorDaCelula(linha, 3);
                String status = valorDaCelula(linha, 4).toLowerCase();
                BigDecimal valorDiario = new BigDecimal(valorDaCelula(linha, 5).replace(",", "."));

                if (placa.isEmpty()) {
                    throw new IllegalArgumentException("A placa nao pode ficar vazia.");
                }
                if (!"carro".equals(tipo) && !"moto".equals(tipo)) {
                    throw new IllegalArgumentException("O tipo deve ser carro ou moto.");
                }
                if (!"disponivel".equals(status) && !"indisponivel".equals(status)) {
                    throw new IllegalArgumentException("O status deve ser disponivel ou indisponivel.");
                }
                if (valorDiario.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("O valor diario nao pode ser negativo.");
                }

                Map<String, Object> alteracoes = new HashMap<>();
                alteracoes.put("placa", placa);
                alteracoes.put("tipo_veiculo", tipo);
                alteracoes.put("marca", marca);
                alteracoes.put("modelo", nomeModelo);
                alteracoes.put("statusVeiculo", status);
                alteracoes.put("valor_diario", valorDiario);
                alteracoesPorLinha.add(alteracoes);
            }

            for (int linha = 0; linha < veiculosAtuais.size(); linha++) {
                AppServices.editarVeiculo(
                        veiculosAtuais.get(linha).getId(),
                        alteracoesPorLinha.get(linha)
                );
            }

            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Alteracoes salvas com sucesso.");
        } catch (IllegalArgumentException erro) {
            UiSupport.aviso(this, erro.getMessage());
        } catch (RuntimeException erro) {
            UiSupport.erro(this, "Nao foi possivel salvar as alteracoes.", erro);
        }
    }

    private String valorDaCelula(int linha, int coluna) {
        Object valor = modelo.getValueAt(linha, coluna);
        return valor == null ? "" : valor.toString().trim();
    }
}
