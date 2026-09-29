package repository;

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
            "https://dicvxojuilstdgszzqbi.supabase.co";

    private static final String SUPABASE_KEY =
            System.getenv("SUPABASE_KEY");

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/cliente";

    private final HttpClient client =
            HttpClient.newHttpClient();

    private final Gson gson =
            new Gson();


    // =========================================================
    // GET
    // =========================================================

    public List<Cliente> buscarClientes(
            String nome,
            String telefone,
            String cnh,
            String categoriaCnh,
            String cep
    ) {

        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");


        // FILTRO POR NOME
        if (nome != null && !nome.isBlank()) {

            url.append("&nome=eq.")
                    .append(encode(nome));
        }


        // FILTRO POR TELEFONE
        if (telefone != null && !telefone.isBlank()) {

            url.append("&telefone=eq.")
                    .append(encode(telefone));
        }


        // FILTRO POR CNH
        if (cnh != null && !cnh.isBlank()) {

            url.append("&cnh=eq.")
                    .append(encode(cnh));
        }


        // FILTRO POR CATEGORIA DA CNH
        if (categoriaCnh != null && !categoriaCnh.isBlank()) {

            url.append("&categoria_cnh=eq.")
                    .append(encode(categoriaCnh));
        }


        // FILTRO POR CEP
        if (cep != null && !cep.isBlank()) {

            url.append("&cep=eq.")
                    .append(encode(cep));
        }


        return fazerGet(url.toString());
    }


    // =========================================================
    // LOGIN DO CLIENTE
    // =========================================================

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


        // CNH não encontrada
        if (clientes.isEmpty()) {

            return null;
        }


        Cliente cliente =
                clientes.get(0);


        // CONFERE A SENHA
        if (cliente.getSenhaCliente() != null
                && cliente.getSenhaCliente().equals(senha)) {

            return cliente;
        }


        // SENHA INCORRETA
        return null;
    }


    // =========================================================
    // POST
    // =========================================================

    public Cliente cadastrarCliente(
            Cliente novoCliente
    ) {

        try {

            // JAVA -> JSON
            String json =
                    gson.toJson(novoCliente);


            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL))
                            .header(
                                    "apikey",
                                    SUPABASE_KEY
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + SUPABASE_KEY
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Prefer",
                                    "return=representation"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )
                            .build();


            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );


            // JSON -> JAVA
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


    // =========================================================
    // PATCH
    // =========================================================

    public Cliente editarCliente(
            long id,
            Map<String, Object> dadosAtualizados
    ) {

        try {

            String url =
                    BASE_URL + "?id=eq." + id;


            // JAVA -> JSON
            String json =
                    gson.toJson(dadosAtualizados);


            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header(
                                    "apikey",
                                    SUPABASE_KEY
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + SUPABASE_KEY
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Prefer",
                                    "return=representation"
                            )
                            .method(
                                    "PATCH",
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )
                            .build();


            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );


            // JSON -> JAVA
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


    // =========================================================
    // MÉTODO INTERNO DO GET
    // =========================================================

    private List<Cliente> fazerGet(
            String url
    ) {

        try {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header(
                                    "apikey",
                                    SUPABASE_KEY
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + SUPABASE_KEY
                            )
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


            // JSON -> JAVA
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


    // =========================================================
    // AUXILIAR DOS FILTROS
    // =========================================================

    private String encode(
            String valor
    ) {

        return URLEncoder.encode(
                valor,
                StandardCharsets.UTF_8
        );
    }
}