package com.netflix.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MovieService {
    // Chave API do TMDB
    private static final String API_KEY = "f9cd2450f48051acd8e97a0403b5f2ce";
    // URL base do TMDB
    private static final String API_URL = "https://api.themoviedb.org/3";
    // Instancia do Singleton
    private static MovieService instance;


    private MovieService() {
    }

    // Criação da instância do Singleton
    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }
        return instance;
    }

    // Método para obter filmes de uma categoria específica
    // O argumento "includeAdultContent" controla se o conteúdo para maiores de 18 deve ser incluído nos resultados
    public String getMovies(String category, boolean includeAdultContent) throws IOException {
        String path;

        // Definindo o caminho de solicitação da API com base na categoria solicitada
        switch (category) {
            case "trending":
                path = "/trending/all/week?api_key=" + API_KEY;
                break;
            case "netflixOriginals":
                path = "/discover/tv/?api_key=" + API_KEY + "&with_networks=213";
                break;
            case "topRated":
                path = "/movie/top_rated/?api_key=" + API_KEY;
                break;
            case "comedy":
                path = "/discover/tv/?api_key=" + API_KEY + "&with_genres=35";
                break;
            case "romances":
                path = "/discover/tv/?api_key=" + API_KEY + "&with_genres=10749";
                break;
            case "documentaries":
                path = "/discover/tv/?api_key=" + API_KEY + "&with_genres=99";
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        // Parâmetros adicionais para lingua do conteudo (pt-br) e idade do conteudo(maior ou menor de 18)
        String queryString = "&language=pt-BR" + "&include_adult=" + includeAdultContent;
        // URL final da solicitação
        URL url = new URL(API_URL + path + queryString);

        // Permite redirecionamentos, porque quando eu mando a solicitação ele me redireciona
        HttpURLConnection.setFollowRedirects(true);

        // Inicializa a conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Definindo como solicitação GET
        connection.setRequestMethod("GET");

        connection.connect();

        // Variavel pra guardar o codigo da resposta
        int responseCode = connection.getResponseCode();

        // Se a resposta indicar um redirecionamento, segue o redirecionamento e cria uma nova conexão
        // Solução do problema de redirecionamento
        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = connection.getHeaderField("Location");
            URL newUrl = new URL(location);
            connection = (HttpURLConnection) newUrl.openConnection();
            connection.connect();
        }

        // Verificando o código de resposta novamente
        responseCode = connection.getResponseCode();
        // Se o código não for 200 lança uma exceção
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            // Se for 200, converte a resposta para uma String
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonMovies = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonMovies.append(line);
            }
            reader.close();

            return jsonMovies.toString();
        }
    }
}
