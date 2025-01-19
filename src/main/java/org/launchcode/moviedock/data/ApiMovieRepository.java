package org.launchcode.moviedock.data;

import org.launchcode.moviedock.models.ApiMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiMovieRepository extends CrudRepository<ApiMovie, Integer> {


}