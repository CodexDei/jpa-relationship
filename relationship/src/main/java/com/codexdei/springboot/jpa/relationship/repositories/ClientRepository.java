package com.codexdei.springboot.jpa.relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codexdei.springboot.jpa.relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{

    @Query("select c from Client c join fetch c.addresses")
    Optional<Client> findOne(Long id);
}
