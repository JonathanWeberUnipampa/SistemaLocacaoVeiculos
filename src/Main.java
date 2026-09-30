import InterfaceGrafica.TelaPrincipal;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
