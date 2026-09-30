package repository;

import config.SupabaseConfig;
import model.Veiculo;

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
import java.util.Map;

// Acessa os dados de Veiculo pela API do Supabase.
public class VeiculoRepository {

    // Endereço do projeto e chave usados nas requisições.
    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    // Endereço da API para esta tabela.
    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/veiculo";

    // Envia as requisições HTTP.
    private final HttpClient client = HttpClient.newHttpClient();

    // Converte objetos Java em JSON e JSON em objetos Java.
    private final Gson gson = new Gson();

    // Busca veículos por tipo, status e/ou placa; sem valores, consulta sem filtros.
    // marca e modelo são ignorados; mantidos para compatibilidade.
    public List<Veiculo> buscarVeiculos(
            String tipoVeiculo,
            String statusVeiculo,
            String marca,
            String modelo,
            String placa
    ) {

        // select=* pede todas as colunas; os if adicionam filtros de igualdade.
        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        if (tipoVeiculo != null && !tipoVeiculo.isBlank()) {
            url.append("&tipo_veiculo=eq.")
                    .append(encode(tipoVeiculo));
        }

        if (statusVeiculo != null && !statusVeiculo.isBlank()) {
            url.append("&statusVeiculo=eq.")
                    .append(encode(statusVeiculo));
        }

        if (placa != null && !placa.isBlank()) {
            url.append("&placa=eq.")
                    .append(encode(placa));
        }

        return fazerGet(url.toString());
    }

    // Envia um POST com os dados e retorna o registro cadastrado.
    public Veiculo cadastrarVeiculo(Veiculo novoVeiculo) {

        try {
            String json = gson.toJson(novoVeiculo);

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

            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            // Retorna o primeiro registro devolvido pelo Supabase.
            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar veículo: " + e.getMessage()
            );
        }
    }

    // Atualiza por ID apenas os campos do mapa, usando PATCH.
    public Veiculo editarVeiculo(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {
            String url = BASE_URL + "?id=eq." + id;

            String json = gson.toJson(dadosAtualizados);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", SUPABASE_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_KEY)
                    .header("Content-Type", "application/json")
                    .header("Prefer", "return=representation")
                    .method(
                            "PATCH",
                            HttpRequest.BodyPublishers.ofString(json)
                    )
                    .build();

            // Envia a requisição e recebe o conteúdo da resposta como texto.
            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            // Retorna o primeiro registro devolvido pelo Supabase.
            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao editar veículo: " + e.getMessage()
            );
        }
    }

    // Executa a consulta GET e converte a resposta em uma lista.
    private List<Veiculo> fazerGet(String url) {

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
                    new TypeToken<List<Veiculo>>() {}.getType();

            // Transforma o JSON recebido em objetos Java.
            return gson.fromJson(
                    response.body(),
                    tipoLista
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao buscar veículos: " + e.getMessage()
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
