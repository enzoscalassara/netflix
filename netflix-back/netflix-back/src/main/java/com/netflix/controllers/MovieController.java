package com.netflix.controllers;

import com.netflix.services.MovieService;
import com.netflix.services.ServiceFactory;
import java.io.IOException;

public class MovieController {
    // Serviço para buscar filmes
    private MovieService movieService;

    // Inicializa o serviço por meio da Factory
    public MovieController() {
        this.movieService = ServiceFactory.createMovieService();
    }

    // Método que busca os filmes de acordo com a categoria e o conteúdo adulto (se a idade do usuario é maior ou menor que 18
    public String getMovies(String path, boolean includeAdultContent) {
        String moviesJson;
        try {
            moviesJson = movieService.getMovies(path, includeAdultContent);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error movie controller";
        }
        return moviesJson;
    }
}
