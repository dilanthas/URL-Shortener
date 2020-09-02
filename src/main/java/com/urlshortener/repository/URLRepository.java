package com.urlshortener.repository;

import com.urlshortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLRepository extends MongoRepository<Url,String> {

}
