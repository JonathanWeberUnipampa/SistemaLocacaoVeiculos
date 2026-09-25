package repository;

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
            "https://dicvxojuilstdgszzqbi.supabase.co";

    private static final String SUPABASE_KEY =
            System.getenv("SUPABASE_KEY");

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/locacao";

    private final HttpClient client = HttpClient.newHttpClient();

    private final Gson gson = new Gson();


    // =========================================================
    // GET
    // =========================================================

    public List<Locacao> buscarLocacoes(
            Long clienteId,
            String dataInicio,
            String dataFim,
            String formaPagamento,
            String veiculoPlaca
    ) {

        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");


        // FILTRO POR CLIENTE
        if (clienteId != null) {
            url.append("&cliente_id=eq.")
                    .append(clienteId);
        }


        // FILTRO POR DATA DE INÍCIO
        if (dataInicio != null && !dataInicio.isBlank()) {
            url.append("&data_inicio=eq.")
                    .append(encode(dataInicio));
        }


        // FILTRO POR DATA DE FIM
        if (dataFim != null && !dataFim.isBlank()) {
            url.append("&data_fim=eq.")
                    .append(encode(dataFim));
        }


        // FILTRO POR FORMA DE PAGAMENTO
        if (formaPagamento != null && !formaPagamento.isBlank()) {
            url.append("&forma_pagamento=eq.")
                    .append(encode(formaPagamento));
        }


        // FILTRO POR PLACA DO VEÍCULO
        if (veiculoPlaca != null && !veiculoPlaca.isBlank()) {
            url.append("&veiculo_placa=eq.")
                    .append(encode(veiculoPlaca));
        }


        return fazerGet(url.toString());
    }


    // =========================================================
    // POST
    // =========================================================

    public Locacao cadastrarLocacao(Locacao novaLocacao) {

        try {

            // JAVA -> JSON
            String json = gson.toJson(novaLocacao);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("apikey", SUPABASE_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_KEY)
                    .header("Content-Type", "application/json")
                    .header("Prefer", "return=representation")
                    .POST(
                            HttpRequest.BodyPublishers.ofString(json)
                    )
                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // JSON -> JAVA
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


    // =========================================================
    // PATCH
    // =========================================================

    public Locacao editarLocacao(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {

            String url =
                    BASE_URL + "?id=eq." + id;

            // JAVA -> JSON
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

            // JSON -> JAVA
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


    // =========================================================
    // MÉTODO INTERNO DO GET
    // =========================================================

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

            // JSON -> JAVA
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


    // =========================================================
    // AUXILIAR DOS FILTROS
    // =========================================================

    private String encode(String valor) {

        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}