package com.netflix.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieService {
    private static final String API_KEY = "f9cd2450f48051acd8e97a0403b5f2ce";
    private static final String API_URL = "https://api.themoviedb.org/3";

    public String getMovies(String category, boolean includeAdultContent) throws IOException {
        String path;
        switch (category) {
            case "trending":
                path = "/trending/all/week";
                break;
            case "netflixOriginals":
                path = "/discover/tv?with_networks=213";
                break;
            case "topRated":
                path = "/movie/top_rated";
                break;
            case "comedy":
                path = "/discover/tv?&with_genres=35";
                break;
            case "romances":
                path = "/discover/tv?&with_genres=10749";
                break;
            case "documentaries":
                path = "/discover/tv/&with_genres=99";
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        String queryString = "?api_key=" + API_KEY + "&language=pt-BR" + "&include_adult=" + includeAdultContent;
        URL url = new URL(API_URL + path + queryString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
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
