package com.moviebooking.movieservice.repository;

import com.moviebooking.movieservice.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguagesContaining(String language);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}

