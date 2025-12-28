package org.moviebooking.theatreservice.Service;

import org.moviebooking.theatreservice.DTO.PointAndDistance;
import org.moviebooking.theatreservice.Repo.TheatreServiceRepo;
import org.moviebooking.theatreservice.entity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {
    @Autowired
    private TheatreServiceRepo theatreServiceRepo;
    // methods to be implemented
    //1. add Theatre
    public ResponseEntity<?> addTheatre(Theatre theatre){
        try {
            theatreServiceRepo.save(theatre);
            return ResponseEntity.status(HttpStatus.OK).body(theatre);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()+" encountered manually");
        }
    }
    //2. getAllTheatre
    public ResponseEntity<?> getAllTheatre(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(theatreServiceRepo.findAll());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()+" encountered manually");
        }
    }
    //3. giveTheatre in specific range
    public ResponseEntity<?> getTheatreInRange(PointAndDistance pointAndDistance){
        try{
            List<Theatre> theatres=theatreServiceRepo.findByLocationNear(pointAndDistance.getPoint(),new Distance(pointAndDistance.getDistance(), Metrics.KILOMETERS));
            return ResponseEntity.status(HttpStatus.OK).body(theatres);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()+" encountered manually");
        }
    }
}
