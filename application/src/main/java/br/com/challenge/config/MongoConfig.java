package br.com.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"br.com.challenge.infrastracture.repository"})
public class MongoConfig {

}
