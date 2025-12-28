package org.moviebooking.theatreservice.Repo;

import org.bson.types.ObjectId;
import org.moviebooking.theatreservice.entity.Theatre;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreServiceRepo extends MongoRepository<Theatre,ObjectId> {
    List<Theatre> findByLocationNear(GeoJsonPoint location, Distance distance);
}
