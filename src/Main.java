import pagamento.FormaPagamento;
import pagamento.PagamentoPix;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceGrafica.TelaPrincipal().setVisible(true);
            }
        });
        
    }


}
