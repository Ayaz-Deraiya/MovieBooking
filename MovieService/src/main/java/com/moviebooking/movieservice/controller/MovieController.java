package com.moviebooking.movieservice.controller;

import com.moviebooking.movieservice.Service.MovieService;
import com.moviebooking.movieservice.entity.Movie;
import com.moviebooking.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    //methods of this service
    // 1. add Movie
    // 2. getAllMovies
    @Autowired
    private MovieService movieService;
    
    @PostMapping("/addMovie")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        return movieService.AddMovie(movie);
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return movieService.getAllMovies();
    }

}
