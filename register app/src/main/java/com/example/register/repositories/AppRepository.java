package com.example.register.repositories;

import com.example.register.model.App;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends CrudRepository<App, Long> {

    @Query("SELECT f.url FROM App f" )
    List<App> findAll();


}
