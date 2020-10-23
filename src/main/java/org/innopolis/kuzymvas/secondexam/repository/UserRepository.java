package org.innopolis.kuzymvas.secondexam.repository;

import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {

    Optional<UserDAO> findByName(String Name);
}
