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
import java.util.Map;

public class ClienteRepository {

    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/cliente";

    private final HttpClient client =
            HttpClient.newHttpClient();

    private final Gson gson =
            new Gson();

    public List<Cliente> buscarClientes(
            String nome,
            String telefone,
            String cnh,
            String categoriaCnh,
            String cep
    ) {

        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        if (nome != null && !nome.isBlank()) {
            url.append("&nome=eq.")
                    .append(encode(nome));
        }

        if (telefone != null && !telefone.isBlank()) {
            url.append("&telefone=eq.")
                    .append(encode(telefone));
        }

        if (cnh != null && !cnh.isBlank()) {
            url.append("&cnh=eq.")
                    .append(encode(cnh));
        }

        if (categoriaCnh != null && !categoriaCnh.isBlank()) {
            url.append("&categoria_cnh=eq.")
                    .append(encode(categoriaCnh));
        }

        if (cep != null && !cep.isBlank()) {
            url.append("&cep=eq.")
                    .append(encode(cep));
        }

        return fazerGet(url.toString());
    }

    public Cliente autenticarCliente(
            String cnh,
            String senha
    ) {

        List<Cliente> clientes =
                buscarClientes(
                        null,
                        null,
                        cnh,
                        null,
                        null
                );

        if (clientes.isEmpty()) {
            return null;
        }

        Cliente cliente =
                clientes.get(0);

        if (cliente.getSenhaCliente() != null
                && cliente.getSenhaCliente().equals(senha)) {
            return cliente;
        }

        return null;
    }

    public Cliente cadastrarCliente(
            Cliente novoCliente
    ) {

        try {
            String json =
                    gson.toJson(novoCliente);

            HttpRequest request =
                    HttpRequest.newBuilder()
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

            Cliente[] clientes =
                    gson.fromJson(
                            response.body(),
                            Cliente[].class
                    );

            return clientes[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao cadastrar cliente: "
                            + e.getMessage()
            );
        }
    }

    public Cliente editarCliente(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {
            String url =
                    BASE_URL + "?id=eq." + id;

            String json =
                    gson.toJson(dadosAtualizados);

            HttpRequest request =
                    HttpRequest.newBuilder()
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

            Cliente[] clientes =
                    gson.fromJson(
                            response.body(),
                            Cliente[].class
                    );

            return clientes[0];

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao editar cliente: "
                            + e.getMessage()
            );
        }
    }

    private List<Cliente> fazerGet(
            String url
    ) {

        try {
            HttpRequest request =
                    HttpRequest.newBuilder()
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
                    new TypeToken<List<Cliente>>() {
                    }.getType();

            return gson.fromJson(
                    response.body(),
                    tipoLista
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao buscar clientes: "
                            + e.getMessage()
            );
        }
    }

    private String encode(
            String valor
    ) {
        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}
