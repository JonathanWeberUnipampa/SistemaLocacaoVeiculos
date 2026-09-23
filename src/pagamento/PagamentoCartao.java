package pagamento;

public class PagamentoCartao implements FormaPagamento {
    @Override
    public double realizarPagamento(double valor) {
        return valor;
    }
}
