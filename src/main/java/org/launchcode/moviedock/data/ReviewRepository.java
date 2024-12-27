package org.launchcode.moviedock.data;

import org.launchcode.moviedock.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

//    @Query("SELECT rev FROM Review rev WHERE rev.movie_id = :movieId AND rev.user_id = :userId")
//    Review findByUserIdAndMovieId(Integer movieId, Integer userId);

    @Query(value ="SELECT * FROM review WHERE movie_id = :movieId AND user_id = :userId",nativeQuery = true)
    Review findByUserIdAndMovieId(Integer movieId, Integer userId);

}
