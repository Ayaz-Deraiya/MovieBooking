package com.moviebooking.movieservice.Service;

import com.moviebooking.movieservice.entity.Movie;
import com.moviebooking.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class MovieService {
    //methods to be implemented
    //1.Add Movie
    //2.getMovies
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public ResponseEntity<?> AddMovie(Movie movie){
        movieRepository.save(movie);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = movieRepository.findAll();
        return ResponseEntity.ok(movies);
    }

}
