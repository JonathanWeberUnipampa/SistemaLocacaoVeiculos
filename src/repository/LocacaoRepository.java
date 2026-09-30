package repository;

import config.SupabaseConfig;
import model.Locacao;

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

// Acessa os dados de Locacao pela API do Supabase.
public class LocacaoRepository {

    // Endereço do projeto e chave usados nas requisições.
    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    // Endereço da API para esta tabela.
    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/locacao";

    // Envia as requisições HTTP.
    private final HttpClient client = HttpClient.newHttpClient();

    // Converte objetos Java em JSON e JSON em objetos Java.
    private final Gson gson = new Gson();

    // Busca locações por cliente e/ou placa; sem ambos, consulta sem filtros.
    // dataInicio, dataFim e formaPagamento são ignorados; mantidos para compatibilidade.
    public List<Locacao> buscarLocacoes(
            Long clienteId,
            String dataInicio,
            String dataFim,
            String formaPagamento,
            String veiculoPlaca
    ) {

        // select=* pede todas as colunas; os if adicionam filtros de igualdade.
        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        if (clienteId != null) {
            url.append("&cliente_id=eq.")
                    .append(clienteId);
        }

        if (veiculoPlaca != null && !veiculoPlaca.isBlank()) {
            url.append("&veiculo_placa=eq.")
                    .append(encode(veiculoPlaca));
        }

        return fazerGet(url.toString());
    }

    // Envia um POST com os dados e retorna o registro cadastrado.
    public Locacao cadastrarLocacao(Locacao novaLocacao) {

        try {
            String json = gson.toJson(novaLocacao);

            HttpRequest request = HttpRequest.newBuilder()
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

            Locacao[] locacoes =
                    gson.fromJson(
                            response.body(),
                            Locacao[].class
                    );

            // Retorna o primeiro registro devolvido pelo Supabase.
            return locacoes[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar locação: "
                            + e.getMessage()
            );
        }
    }

    // Executa a consulta GET e converte a resposta em uma lista.
    private List<Locacao> fazerGet(String url) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
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
                    new TypeToken<List<Locacao>>() {}
                            .getType();

            // Transforma o JSON recebido em objetos Java.
            return gson.fromJson(
                    response.body(),
                    tipoLista
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao buscar locações: "
                            + e.getMessage()
            );
        }
    }

    // Codifica espaços e caracteres especiais para uso nos filtros da URL.
    private String encode(String valor) {
        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}
