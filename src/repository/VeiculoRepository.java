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

public class VeiculoRepository {

    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/veiculo";

    private final HttpClient client = HttpClient.newHttpClient();

    private final Gson gson = new Gson();

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

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar veículo: " + e.getMessage()
            );
        }
    }

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

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            Veiculo[] veiculos =
                    gson.fromJson(response.body(), Veiculo[].class);

            return veiculos[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao editar veículo: " + e.getMessage()
            );
        }
    }

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

    private String encode(String valor) {
        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}
