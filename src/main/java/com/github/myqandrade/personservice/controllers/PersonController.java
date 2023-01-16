package com.github.myqandrade.personservice.controllers;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> findAll(){
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id){
        return personService.findById(id);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity findPersonAddresses(@PathVariable Integer id){
        return personService.findPersonAddresses(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        return personService.save(person);
    }

    @PostMapping("/newAddress/{id}")
    public ResponseEntity saveAddress(@RequestBody Address address, @PathVariable Integer id){
        return personService.saveAddress(address, id);
    }

    @PutMapping("/edit/{personId}/{addressId}")
    public ResponseEntity<Person> saveMainAddress(@RequestBody Person updatedPerson,
                                                  @PathVariable("personId") Integer personId,
                                                  @PathVariable("addressId") Integer addressId) {
        return personService.saveMainAddress(updatedPerson, personId, addressId);
    }

    @PutMapping("/editPerson/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson, @PathVariable Integer id){
        return personService.updatePerson(updatedPerson, id);
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return personService.delete(id);
    }
}
