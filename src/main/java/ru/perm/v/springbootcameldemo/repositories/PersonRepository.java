package ru.perm.v.springbootcameldemo.repositories;

import ru.perm.v.springbootcameldemo.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {

    public List<Person> findByFirstName(String firstName);
    public List<Person> findByLastName(String lastName);
    public List<Person> findAll();
    public void deleteById(String id);
}
