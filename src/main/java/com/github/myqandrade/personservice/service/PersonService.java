package com.github.myqandrade.personservice.service;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.status(HttpStatus.OK
        ).body(personRepository.findAll());
    }

    public ResponseEntity<Person> findById(Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person.get());
    }

    public ResponseEntity findPersonAddresses(Integer id){
        Optional<Person> person = personRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(person.get().getAddresses());
    }

    public ResponseEntity<Person> save(Person person){
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(person));
    }

    public ResponseEntity saveAddress(Address address, Integer id){
        Optional<Person> person = personRepository.findById(id);
        Set<Address> addresses = person.get().getAddresses();
        for(Address ad : addresses){
            if(ad.getAddress().equals(address.getAddress())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Address already exists");
            }
        }
        person.get().setAdress(address);
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(person.get()));
    }

    public ResponseEntity<Person> update(Person updatedPerson, Integer id) {
        Optional<Person> person = personRepository.findById(id);
        Set<Address> personAddresses = person.get().getAddresses();
        for (Address address : personAddresses) {
            if (address.getAddressId().equals(updatedPerson.getMainAddress().getAddressId())) {
                updatedPerson.setMainAddress(address);
                updatedPerson.setId(person.get().getId());
                return ResponseEntity.ok().body(personRepository.save(updatedPerson));
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Person> updatePerson(Person updatedPerson, Integer id){
        Optional<Person> person = personRepository.findById(id);
        Set<Address> addresses = person.get().getAddresses();
        if(person.isPresent()){
            for(Address ad : addresses){
                updatedPerson.setAdress(ad);
            }
            updatedPerson.setId(person.get().getId());
            return ResponseEntity.ok().body(personRepository.save(updatedPerson));
        }

        return ResponseEntity.notFound().build();
    }
}
