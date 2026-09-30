package repository;

import config.SupabaseConfig;
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

// Acessa os dados de Gerente pela API do Supabase.
public class GerenteRepository {

    // Endereço do projeto e chave usados nas requisições.
    private static final String SUPABASE_URL =
            SupabaseConfig.URL;

    private static final String SUPABASE_KEY =
            SupabaseConfig.ANON_KEY;

    // Endereço da API para esta tabela.
    private static final String BASE_URL =
            SUPABASE_URL + "/rest/v1/gerente";

    // Envia as requisições HTTP.
    private final HttpClient client =
            HttpClient.newHttpClient();

    // Converte objetos Java em JSON e JSON em objetos Java.
    private final Gson gson =
            new Gson();

    // Busca gerentes pelo e-mail; sem e-mail, consulta sem filtros.
    public List<Gerente> buscarGerentes(
            String email
    ) {

        // select=* pede todas as colunas; os if adicionam filtros de igualdade.
        StringBuilder url =
                new StringBuilder(BASE_URL + "?select=*");

        if (email != null && !email.isBlank()) {
            url.append("&email=eq.")
                    .append(encode(email));
        }

        return fazerGet(url.toString());
    }

    // Busca pelo e-mail e confere a senha; retorna null se não autenticar.
    public Gerente autenticarGerente(
            String email,
            String senha
    ) {

        List<Gerente> gerentes =
                buscarGerentes(
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

    // Executa a consulta GET e converte a resposta em uma lista.
    private List<Gerente> fazerGet(
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

            // Envia a requisição e recebe o conteúdo da resposta como texto.
            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // Informa ao Gson o tipo dos objetos contidos na lista.
            Type tipoLista =
                    new TypeToken<List<Gerente>>() {
                    }.getType();

            // Transforma o JSON recebido em objetos Java.
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
