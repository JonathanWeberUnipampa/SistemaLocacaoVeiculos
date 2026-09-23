package PACKAGE_NAME;

public class Locadora {

    // Método cadastrarVeiculo, recebe como parâmetro um objeto veículo já montado e cadastra no sistema
    public void cadastrarVeiculo (Veiculo veiculo) throws LocacaoException{
        //If para verificar se a condição é verdadeira, caso contrário mostra um erro.
        if (){
            throw new LocacaoException("Erro!");
        } //Else para mostrar a segunda exceção, se precisar de mais adiciono outras exceções
        throw new LocacaoException("Erro 2!")
    }
    //Método para cadastrarCliente, recebe como parâmetro um objeto cliente já montado com as informações
    //como nome, CPF, CEP e cadastra no sistema
    public void cadastrarCliente(Cliente cliente) throws LocacaoException {
        //Verifica se a condição do If é verdadeira, no caso seria algo do tipo if nome já existe > erro
        if(){
            throw new LocacaoException("Erro!");
        } //Else para mostrar outra exceção
        throw new LocacaoException("Erro 2!")
    }
    //Método para buscar veículo no banco de dados, liga diretamente na API e procura todos os veículos presentes

    public Veiculo buscarVeiculo(String placa) throws LocacaoException{
        //Verifica se a condição IF é verdadeira, se o veículo existe ou não
        if(){
            throw new LocacaoException("Erro!");
        }
    }
    /*Quando usar LocacaoException, regras:
    *Quando veículo já estiver alugado: Indica um erro com a função IF
    *O cliente precisa estar cadastrado para alugar um carro, sem cadastro não é possível
    *A CNH do cliente precisa ser compatível com o tipo que está alugando, se a gente
    *ainda manter motos e carros, não deve ser possível um cliente com CNH B alugar um carro
    *Quando um cadastro for duplicado, com a mesma placa ou código, o que não pode acontecer
    *Quando o nome do cliente for inválido, não pode aceitar números no nome por exemplo
    */
