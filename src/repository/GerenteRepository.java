package repository;

import model.Gerente;

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

public class GerenteRepository {

    private static final String SUPABASE_URL =
            "https://dicvxojuilstdgszzqbi.supabase.co";

    private static final String SUPABASE_KEY =
            System.getenv("SUPABASE_KEY");

    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/gerente";

    private final HttpClient client =
            HttpClient.newHttpClient();

    private final Gson gson =
            new Gson();


    // =========================================================
    // GET
    // =========================================================

    public List<Gerente> buscarGerentes(
            String nome,
            String email
    ) {

        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");


        // FILTRO POR NOME
        if (nome != null && !nome.isBlank()) {

            url.append("&nome=eq.")
                    .append(encode(nome));
        }


        // FILTRO POR EMAIL
        if (email != null && !email.isBlank()) {

            url.append("&email=eq.")
                    .append(encode(email));
        }


        return fazerGet(url.toString());
    }


    // =========================================================
    // LOGIN DO GERENTE
    // =========================================================

    public Gerente autenticarGerente(
            String email,
            String senha
    ) {

        List<Gerente> gerentes =
                buscarGerentes(
                        null,
                        email
                );

        if (gerentes.isEmpty()) {
            return null;
        }

        Gerente gerente =
                gerentes.get(0);

        if (gerente.getSenhaGerente() != null
                && gerente.getSenhaGerente().equals(senha)) {

            return gerente;
        }

        return null;
    }


    // =========================================================
    // POST
    // =========================================================

    public Gerente cadastrarGerente(
            Gerente novoGerente
    ) {

        try {

            // JAVA -> JSON
            String json =
                    gson.toJson(novoGerente);

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

            Gerente[] gerentes =
                    gson.fromJson(
                            response.body(),
                            Gerente[].class
                    );

            return gerentes[0];

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao cadastrar gerente: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // PATCH
    // =========================================================

    public Gerente editarGerente(
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

            Gerente[] gerentes =
                    gson.fromJson(
                            response.body(),
                            Gerente[].class
                    );

            return gerentes[0];

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao editar gerente: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // MÉTODO INTERNO DO GET
    // =========================================================

    private List<Gerente> fazerGet(
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
                    new TypeToken<List<Gerente>>() {
                    }.getType();

            // JSON -> JAVA
            return gson.fromJson(
                    response.body(),
                    tipoLista
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao buscar gerentes: "
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