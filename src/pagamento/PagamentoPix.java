package pagamento;

public class PagamentoPix implements FormaPagamento{
    @Override
    public double realizarPagamento(double valor) {
        double desconto = valor * 0.10;
        return valor - desconto;
    }
}
