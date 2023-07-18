package com.netflix.services;

public class ServiceFactory {
    public static UserService createUserService() {
        return UserService.getInstance();
    }

    public static MovieService createMovieService() {
        return MovieService.getInstance();
    }
}
