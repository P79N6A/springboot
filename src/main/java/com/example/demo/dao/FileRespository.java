package com.example.demo.dao;

import com.example.demo.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRespository extends MongoRepository<File,String> {

}
