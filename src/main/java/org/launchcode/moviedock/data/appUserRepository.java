package org.launchcode.moviedock.data;

import org.launchcode.moviedock.models.appUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface appUserRepository extends CrudRepository<appUser, Integer> {

    Optional<appUser> findByUsername(String username);

}
