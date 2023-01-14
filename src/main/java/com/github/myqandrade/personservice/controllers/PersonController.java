package com.github.myqandrade.personservice.controllers;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.status(HttpStatus.OK
        ).body(personRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person.get());
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(person));
    }

    @PostMapping("/newAddress/{id}")
    public ResponseEntity saveAddress(@RequestBody Address address, @PathVariable Integer id){
        Optional<Person> person = personRepository.findById(id);
        List<Address> addresses = person.get().getAddresses();
        for(Address ad : addresses){
            if(ad.getAddress().equals(address.getAddress())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Address already exists");
            }
        }
        person.get().setAdress(address);
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(person.get()));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Person> update(@RequestBody Person updatedPerson, @PathVariable Integer id) {
        Optional<Person> person = personRepository.findById(id);
        List<Address> personAddresses = person.get().getAddresses();
        for (Address address : personAddresses) {
            if (address.getAddressId().equals(updatedPerson.getMainAddress().getAddressId())) {
                updatedPerson.setMainAddress(address);
                updatedPerson.setId(person.get().getId());
                return ResponseEntity.ok().body(personRepository.save(updatedPerson));
            }
        }
                return ResponseEntity.notFound().build();
    }
}
