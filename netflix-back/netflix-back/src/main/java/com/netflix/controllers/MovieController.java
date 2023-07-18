package com.netflix.controllers;

import com.netflix.services.MovieService;
import com.netflix.services.ServiceFactory;
import java.io.IOException;

public class MovieController {
    private MovieService movieService;

    public MovieController() {
        this.movieService = ServiceFactory.createMovieService();
    }

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
