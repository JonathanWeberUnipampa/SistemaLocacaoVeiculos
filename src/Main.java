import pagamento.FormaPagamento;
import pagamento.PagamentoPix;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfaceGrafica.TelaPrincipal telaPrincipal = new InterfaceGrafica.TelaPrincipal();
                if (telaPrincipal.temPerfilSelecionado()) {
                    telaPrincipal.setVisible(true);
                }
            }
        });
        
    }


}
