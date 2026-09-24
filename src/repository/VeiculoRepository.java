package repository;

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

public class VeiculoRepository {

    private static final String SUPABASE_URL =
            "https://dicvxojuilstdgszzqbi.supabase.co";

    private static final String SUPABASE_KEY =
            System.getenv("SUPABASE_KEY");

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/veiculo";

    private final HttpClient client = HttpClient.newHttpClient();

    private final Gson gson = new Gson();


    // =========================================================
    // GET
    // =========================================================

    public List<Veiculo> buscarVeiculos(
            String tipoVeiculo,
            String statusVeiculo,
            String marca,
            String modelo,
            String placa
    ) {

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

        if (marca != null && !marca.isBlank()) {
            url.append("&marca=eq.")
                    .append(encode(marca));
        }

        if (modelo != null && !modelo.isBlank()) {
            url.append("&modelo=eq.")
                    .append(encode(modelo));
        }

        if (placa != null && !placa.isBlank()) {
            url.append("&placa=eq.")
                    .append(encode(placa));
        }

        return fazerGet(url.toString());
    }


    // =========================================================
    // POST
    // =========================================================

    public Veiculo cadastrarVeiculo(Veiculo novoVeiculo) {

        try {

            // JAVA -> JSON
            String json = gson.toJson(novoVeiculo);

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

            // JSON -> JAVA
            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar veículo: " + e.getMessage()
            );
        }
    }


    // =========================================================
    // PATCH
    // =========================================================

    public Veiculo editarVeiculo(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {

            String url = BASE_URL + "?id=eq." + id;

            // JAVA -> JSON
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

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // JSON -> JAVA
            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao editar veículo: " + e.getMessage()
            );
        }
    }


    // =========================================================
    // GET INTERNO
    // =========================================================

    private List<Veiculo> fazerGet(String url) {

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
                    new TypeToken<List<Veiculo>>() {}.getType();

            // JSON -> JAVA
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