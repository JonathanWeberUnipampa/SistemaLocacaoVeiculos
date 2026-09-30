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
import java.util.Map;

public class LocacaoRepository {

    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/locacao";

    private final HttpClient client = HttpClient.newHttpClient();

    private final Gson gson = new Gson();

    public List<Locacao> buscarLocacoes(
            Long clienteId,
            String dataInicio,
            String dataFim,
            String formaPagamento,
            String veiculoPlaca
    ) {

        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        if (clienteId != null) {
            url.append("&cliente_id=eq.")
                    .append(clienteId);
        }

        if (dataInicio != null && !dataInicio.isBlank()) {
            url.append("&data_inicio=eq.")
                    .append(encode(dataInicio));
        }

        if (dataFim != null && !dataFim.isBlank()) {
            url.append("&data_fim=eq.")
                    .append(encode(dataFim));
        }

        if (formaPagamento != null && !formaPagamento.isBlank()) {
            url.append("&forma_pagamento=eq.")
                    .append(encode(formaPagamento));
        }

        if (veiculoPlaca != null && !veiculoPlaca.isBlank()) {
            url.append("&veiculo_placa=eq.")
                    .append(encode(veiculoPlaca));
        }

        return fazerGet(url.toString());
    }

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

            return locacoes[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar locação: "
                            + e.getMessage()
            );
        }
    }

    public Locacao editarLocacao(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {
            String url =
                    BASE_URL + "?id=eq." + id;

            String json =
                    gson.toJson(dadosAtualizados);

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

            return locacoes[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao editar locação: "
                            + e.getMessage()
            );
        }
    }

    private List<Locacao> fazerGet(String url) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", SUPABASE_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            Type tipoLista =
                    new TypeToken<List<Locacao>>() {}
                            .getType();

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

    private String encode(String valor) {
        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}
