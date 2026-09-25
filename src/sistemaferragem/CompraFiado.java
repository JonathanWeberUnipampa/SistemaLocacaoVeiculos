package sistemaferragem;
import java.time.LocalDate;
import java.time.LocalTime;

public class CompraFiado {

    private double valor;
    private LocalDate data;
    private LocalTime hora;
    private TipoMaterial tipoMaterial;

    public CompraFiado(double valor, LocalDate data, LocalTime hora, TipoMaterial tipoMaterial) {
        this.valor = valor;
        this.data = data;
        this.hora = hora;
        this.tipoMaterial = tipoMaterial;
    }
}
