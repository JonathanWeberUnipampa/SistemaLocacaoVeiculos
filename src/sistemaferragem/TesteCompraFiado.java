package sistemaferragem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TesteCompraFiado {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        CompraFiado compra;
        compra = new CompraFiado(
                80.0,
                LocalDate.of(2026, 9, 22),
                LocalTime.of(18, 30),
                TipoMaterial.MATERIAL_FERRAGEM);
    }
}
