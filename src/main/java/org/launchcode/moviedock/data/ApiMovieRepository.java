package org.launchcode.moviedock.data;

import org.launchcode.moviedock.models.ApiMovie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiMovieRepository extends CrudRepository<ApiMovie, Integer> {

    Optional<ApiMovie> findByApiID(String apiID);

    @Query(value ="SELECT apiid FROM api_movie ORDER BY view_count DESC LIMIT 5",nativeQuery = true)
    List<String> getTopMovies();

}