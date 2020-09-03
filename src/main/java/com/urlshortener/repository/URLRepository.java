package com.urlshortener.repository;

import com.urlshortener.model.Url;
import org.springframework.data.repository.CrudRepository;

public interface URLRepository extends CrudRepository<Url,String> {

    Url findByLongUrl(String longUrl);
}
