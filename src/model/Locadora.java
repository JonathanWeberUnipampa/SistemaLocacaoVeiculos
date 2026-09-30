
package model;

import exception.LocacaoException;

//importa todos os repositorios do banco de dados
import repository.ClienteRepository;
import repository.VeiculoRepository;
import repository.LocacaoRepository;
import repository.GerenteRepository;
//indica, importa os metodos de usuariocadastrado e categoria cnh
import SISTEMACADASTRO.UsuarioCadastrado;
import SISTEMACADASTRO.CategoriaCNH;
import SISTEMACADASTRO.Gerente;
import java.util.List;
import java.util.Map;
import java.util.HashMap;




//cria uma classe publica chamada locadora, com 3 variáveis privadas repository
//que são um encapsulamento para a interface não conseguir interagir direot com o banco de dados
//sem passar pela validação da locadora pra não ter erros
public class Locadora {

    private ClienteRepository clienteRepo = new ClienteRepository();
    private VeiculoRepository veiculoRepo = new VeiculoRepository();
    private LocacaoRepository locacaoRepo = new LocacaoRepository();
    private GerenteRepository gerenteRepo = new GerenteRepository();

    public UsuarioCadastrado autenticarCliente(String cnh, String senha) throws LocacaoException{
        try{
            return clienteRepo.autenticarUsuarioCadastrado(cnh, senha);
        } catch (RuntimeException e){
            throw new LocacaoException("Erro ao autenticar cliente" + e.getMessage());
        }
    }
    public Gerente autenticarGerente(String email, String senha) throws LocacaoException{
        try{
            return gerenteRepo.autenticarGerente(email, senha);
        } catch (RuntimeException e){
            throw new LocacaoException("Erro ao autenticar gerente" + e.getMessage());
        }
    }


    //cria uma função que não retorna nada e valida se as informações estão corretas, existentes, ou inválidas antes de cadastrar no banco de dados
    public void cadastrarCliente(UsuarioCadastrado cliente) throws LocacaoException{
        //se o cliente estiver vazio mostra:
        if (cliente == null){
            throw new LocacaoException("Dados do usuário inválidos!");
        }

        //se o getter getnome for negativo ou estivar vazio mostra:
        if (cliente.getNome() == null || cliente.getNome().isBlank()){
            throw new LocacaoException("Nome inválido! Insira um nome para continuar.");
        }

        //se a cnh do cliente for negativa ou estiver vazio mostra:
        if (cliente.getCNH() == null || cliente.getCNH().isBlank()){
            throw new LocacaoException("A CNH do cliente é obrigatória!");
        }


        //procura direto no banco de dados comunicando com o supabse, se existe alguma CNH com esse número já existente
        //depois que ela passou por todas as verificações if
        try {
            List<UsuarioCadastrado> existentes = clienteRepo.buscarUsuarioCadastrados(null, null, cliente.getCNH(), null, null);
            //verifica se já existe uma CNH com esse número ja registrada, se retornar uma lista com informações mostra o erro:
            if (!existentes.isEmpty()){
                throw new LocacaoException("Já existe um cliente cadastrado com esta CNH.");
            }
            //cadastra no banco de dados
            clienteRepo.cadastrarUsuarioCadastrado(cliente);

            //pega o erro de timeout se ocorrer e mostra a mensagem
        } catch (RuntimeException e) {
            throw new LocacaoException("Timeout erro:  " + e.getMessage());
        }
    }

    //cria a função cadastrar veiculo que não retorna nada e valida se as informações estão corretas, existentes ou inválidas
    public void cadastrarVeiculo(Veiculo veiculo) throws LocacaoException{
        //verifica se o veículo está preenchido se não estiver mostra erro:
        if (veiculo == null){
            throw new LocacaoException("Não foi fornecido dados do veículo!");
        }
        //usa getPlaca para verificar se a placa é negativa ou está em branco, se estiver mostra:
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()){
            throw new LocacaoException("A placa do veículo é obrigatória.");
        }
        //Comunica diretamente com o supabase para verificar com buscarveiculos se a placa já existe no sistema
        try {
            List<Veiculo> existentes = veiculoRepo.buscarVeiculos(null, null, null, null, veiculo.getPlaca());
            //usa ! para ver se NÃO existe no sistema, se voltar com uma lista preenchida mostra o erro:
            if (!existentes.isEmpty()){
                throw new LocacaoException("Já existe um veículo com essa placa!");
            }

            //se passar pelos if e tries, cadastra indicando para função onde armazenar no banco de dados
            veiculoRepo.cadastrarVeiculo(veiculo);
        //pega o erro de timeout e mostra na tela
        } catch (RuntimeException e){
            throw new LocacaoException("Erro ao cadastrar veículo no banco, Timeout:" + e.getMessage());
        }
    }

    //Cria a função buscar veiculo usando como parametro o String placa do veículo
    public Veiculo buscarVeiculo(String placa) throws LocacaoException{
        //se a placa estiver vazia ou negativa mostra erro:
        if (placa == null || placa.isBlank()){
            throw new LocacaoException("Placa inválida para buscar.");
        }
        //comunica direto com o supabase pra buscar a placa no banco de dados
        try {
            List<Veiculo> veiculos = veiculoRepo.buscarVeiculos(null, null, null, null, placa);
            //se não achar nada, usa a função isEmpty para verificar e mostra o erro:
            if (veiculos.isEmpty()) {
                throw new LocacaoException("Placa inválida, veículo não encontrado.");
            }
            //retorna o primeiro veículo da lista, se passar pelas verificações, irá aparecer um unico veiculo, o primeiro número 0
            return veiculos.get(0);

        } catch(RuntimeException e){
            throw new LocacaoException("Erro ao buscar veículo" + e.getMessage());
        }
    }

    //cria uma função booleana para verificar se está disponível usando como parametro a placa do veiculo
    public boolean verificarDisponibilidade(String placa) throws LocacaoException{
        Veiculo veiculo = buscarVeiculo(placa);
        //Confirma se o veículo esta disponível usando getStatus para saber como está, compara os dois usando ignore case para não ligar
        // se tem maiúscula ou minúscula e passa disponível se estiver
        if (veiculo == null) return false;
        return "disponivel".equalsIgnoreCase(veiculo.getStatusVeiculo());
    }

    //cria uma função chamada realizarLocação que tem como parâmetros o Usuário cliente
    //o Veículo, e a locação
    public Locacao realizarLocacao(UsuarioCadastrado cliente, Veiculo veiculo, Locacao locacao) throws LocacaoException{
        //se estiver tudo negativo dá erro:
        if (cliente == null || veiculo == null || locacao == null){
            throw new LocacaoException("Dados da locação inválidos, preencha corretamente!");
        }

        //verifica se a pessoa pode alugar
        if (!podeAlugar(cliente, veiculo)) {
            throw new LocacaoException("Cliente com CNH " + cliente.getCategoriaCNH() + " não pode alugar um veículo do tipo " + veiculo.getTipoVeiculo());
        }
        //verifica se está disponível se a pessoa nao verificou antes
        if (!verificarDisponibilidade(veiculo.getPlaca())) {
            throw new LocacaoException("O veículo de placa " + veiculo.getPlaca() + " não está disponível.");
        }
        //Comunica direto para cadastrar no banco de dados a locação, se der erro de Timeout mostra mensagem
        try {
            Locacao locacaoRealizada = locacaoRepo.cadastrarLocacao(locacao);

            Map<String, Object> alteracoes = new HashMap<>();
            alteracoes.put("statusVeiculo", "indisponivel");
            veiculoRepo.editarVeiculo(veiculo.getId(), alteracoes);

            return locacaoRealizada;

        } catch (RuntimeException e){
            throw new LocacaoException("Erro ao registrar locação: " + e.getMessage());
        }
    }

    public boolean podeAlugar(UsuarioCadastrado cliente, Veiculo veiculo) {
        if (cliente == null || veiculo == null) return false;

        CategoriaCNH cnh = cliente.getCategoriaCNH();
        String tipo = veiculo.getTipoVeiculo();

        if (cnh == CategoriaCNH.AB || "AB".equalsIgnoreCase(String.valueOf(cnh))) {
            return true;
        }
        if ((cnh == CategoriaCNH.A || "A".equalsIgnoreCase(String.valueOf(cnh))) && "moto".equalsIgnoreCase(tipo)) {
            return true;
        }
        return (cnh == CategoriaCNH.B || "B".equalsIgnoreCase(String.valueOf(cnh))) && "carro".equalsIgnoreCase(tipo);
    }

    public List<Veiculo> buscarVeiculos(String tipoVeiculo, String statusVeiculo) throws LocacaoException {
        try {
            return veiculoRepo.buscarVeiculos(tipoVeiculo, statusVeiculo, null, null, null);
        } catch (RuntimeException e) {
            throw new LocacaoException("Erro ao buscar veículos: " + e.getMessage());
        }
    }

    //Cria uma função para listar as locações por cliente ou por placa do veículo
    public List<Locacao> consultarLocacoesCliente(Long clienteId) throws LocacaoException {
        try {
            return locacaoRepo.buscarLocacoes(clienteId, null, null, null, null);
        } catch (RuntimeException e) {
            throw new LocacaoException("Erro ao consultar histórico do cliente: " + e.getMessage());
        }
    }
    public List<Locacao> consultarLocacoesVeiculo(String placa) throws LocacaoException {
        try {
            return locacaoRepo.buscarLocacoes(null, null, null, null, placa);
        } catch (RuntimeException e) {
            throw new LocacaoException("Erro ao consultar histórico do veículo: " + e.getMessage());
        }
    }

}





