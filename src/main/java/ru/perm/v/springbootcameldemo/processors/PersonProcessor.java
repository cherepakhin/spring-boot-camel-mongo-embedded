package ru.perm.v.springbootcameldemo.processors;

import ru.perm.v.springbootcameldemo.models.Person;
import ru.perm.v.springbootcameldemo.models.Response;
import ru.perm.v.springbootcameldemo.repositories.PersonRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static ru.perm.v.springbootcameldemo.utils.Utility.createResponse;


@Component
public class PersonProcessor implements Processor {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void process(Exchange exchange) throws Exception {

        String firstName = (String) exchange.getIn().getHeader("firstName");
        String lastName = (String) exchange.getIn().getHeader("lastName");

        List<Person> people = null;

        if(StringUtils.isNotBlank(firstName)) {
            people =  personRepository.findByFirstName(firstName);
        } else if(StringUtils.isNotBlank(lastName)) {
            people = personRepository.findByLastName(lastName);
        } else {
            people = personRepository.findAll();
        }

        exchange.getIn().setBody(people);
    }

    public Response<Person> insertPerson(Exchange exchange) {
        Person person = personRepository.insert(exchange.getIn().getBody(Person.class));
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "201");
        return createResponse(person, "Successful creation", "201");
    }

    public Person getPerson(@Header("id") String id) {
        return personRepository.findOne(id);
    }

    public List<Person> getPeopleByFirstName(@Header("firstName") String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    public List<Person> getPeopleByLastName(@Header("lastName") String lastName) {
        return personRepository.findByLastName(lastName);
    }

    public void deletePerson(@Header("id") String id) {
        personRepository.deleteById(id);
    }
}
