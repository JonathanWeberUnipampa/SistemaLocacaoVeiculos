package pagamento;

public class PagamentoDinheiro implements FormaPagamento {

    @Override
    public double realizarPagamento(double valor) {
        return valor;
    }

    @Override
    public String getNome() {
        return "DINHEIRO";
    }
}
