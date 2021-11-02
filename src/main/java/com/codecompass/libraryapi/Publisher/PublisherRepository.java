package com.codecompass.libraryapi.Publisher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, Integer> {

    List<PublisherEntity> findByNameContaining(String name);

}
