package pagamento;

public class PagamentoCartao implements FormaPagamento {
    @Override
    public double realizarPagamento(double valor) {
        return valor;
    }
    @Override
    public String getNome() {
        return "CARTAO";
    }
}
