package org.launchcode.moviedock.data;

import org.launchcode.moviedock.models.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

    Optional<Profile> findByUsername(String username);

}
