public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            InterfaceGrafica.TelaPrincipal telaPrincipal = new InterfaceGrafica.TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
