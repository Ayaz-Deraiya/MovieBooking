package org.moviebooking.theatreservice.Controller;

import org.moviebooking.theatreservice.DTO.PointAndDistance;
import org.moviebooking.theatreservice.Service.TheatreService;
import org.moviebooking.theatreservice.entity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Theatre")
@CrossOrigin("*")
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping("/addTheatre")
    public ResponseEntity<?> addTheatre(@RequestBody Theatre theatre){
        return theatreService.addTheatre(theatre);
    }

    @GetMapping("/getAllTheatres")
    public ResponseEntity<?> getAllTheatres(){
        return theatreService.getAllTheatre();
    }

    @PostMapping("/getNearby")
    public ResponseEntity<?> getNearbyTheatre(@RequestBody PointAndDistance pointAndDistance){
        return theatreService.getTheatreInRange(pointAndDistance);
    }
}
