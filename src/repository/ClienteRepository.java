package repository;

import config.SupabaseConfig;
import model.Cliente;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

// Acessa os dados de Cliente pela API do Supabase.
public class ClienteRepository {

    // Endereço do projeto e chave usados nas requisições.
    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    // Endereço da API para esta tabela.
    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/cliente";

    // Envia as requisições HTTP.
    private final HttpClient client =
            HttpClient.newHttpClient();

    // Converte objetos Java em JSON e JSON em objetos Java.
    private final Gson gson =
            new Gson();

    // Busca clientes pela CNH; sem CNH, consulta sem filtros.
    // nome, telefone, categoriaCnh e cep são ignorados; mantidos para compatibilidade.
    public List<Cliente> buscarClientes(
            String nome,
            String telefone,
            String cnh,
            String categoriaCnh,
            String cep
    ) {

        // select=* pede todas as colunas da tabela cliente.
        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        // Adiciona o filtro de igualdade somente se a CNH estiver preenchida.
        if (cnh != null && !cnh.isBlank()) {
            url.append("&cnh=eq.")
                    .append(encode(cnh));
        }

        return fazerGet(url.toString());
    }

    // Busca pela CNH e confere a senha; retorna null se não autenticar.
    public Cliente autenticarCliente(
            String cnh,
            String senha
    ) {

        // Consulta pela CNH digitada no login; os demais filtros ficam sem valor.
        List<Cliente> clientes =
                buscarClientes(
                        null,
                        null,
                        cnh,
                        null,
                        null
                );

        // Encerra o login se nenhum cliente foi encontrado.
        if (clientes.isEmpty()) {
            return null;
        }

        // Usa o primeiro cliente encontrado para conferir a senha.
        Cliente cliente =
                clientes.get(0);

        // Aprova o login se a senha cadastrada for igual à senha digitada.
        if (cliente.getSenhaCliente() != null
                && cliente.getSenhaCliente().equals(senha)) {
            return cliente;
        }

        // Senha incorreta ou não cadastrada: login recusado.
        return null;
    }

    // Envia um POST com os dados e retorna o registro cadastrado.
    public Cliente cadastrarCliente(
            Cliente novoCliente
    ) {

        try {
            // Converte os dados do novo cliente para o formato enviado à API.
            String json =
                    gson.toJson(novoCliente);

            // Prepara o POST com a chave de acesso e pede o registro salvo na resposta.
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL))
                            .header("apikey", SUPABASE_KEY)
                            .header("Authorization", "Bearer " + SUPABASE_KEY)
                            .header("Content-Type", "application/json")
                            .header("Prefer", "return=representation")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();

            // Envia a requisição e recebe o conteúdo da resposta como texto.
            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // Converte o JSON retornado em um array de clientes.
            Cliente[] clientes =
                    gson.fromJson(
                            response.body(),
                            Cliente[].class
                    );

            // Retorna o primeiro registro devolvido pelo Supabase.
            return clientes[0];

        } catch (Exception e) {
            // Repassa a falha para a classe que solicitou o cadastro.
            throw new RuntimeException(
                    "Erro ao cadastrar cliente: "
                            + e.getMessage()
            );
        }
    }

    // Executa a consulta GET e converte a resposta em uma lista.
    private List<Cliente> fazerGet(
            String url
    ) {

        try {
            // Prepara a consulta ao endereço recebido, incluindo a chave de acesso.
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("apikey", SUPABASE_KEY)
                            .header("Authorization", "Bearer " + SUPABASE_KEY)
                            .GET()
                            .build();

            // Envia a requisição e recebe o conteúdo da resposta como texto.
            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // Informa ao Gson o tipo dos objetos contidos na lista.
            Type tipoLista =
                    new TypeToken<List<Cliente>>() {
                    }.getType();

            // Transforma o JSON recebido em objetos Java.
            return gson.fromJson(
                    response.body(),
                    tipoLista
            );

        } catch (Exception e) {
            // Repassa a falha para a classe que solicitou a busca.
            throw new RuntimeException(
                    "Erro ao buscar clientes: "
                            + e.getMessage()
            );
        }
    }

    // Codifica espaços e caracteres especiais para uso nos filtros da URL.
    private String encode(
            String valor
    ) {
        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}
