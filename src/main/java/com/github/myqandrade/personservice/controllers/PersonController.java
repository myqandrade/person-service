package com.github.myqandrade.personservice.controllers;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/listPerson")
    public ResponseEntity<List<Person>> findAll(){
        return personService.findAll();
    }

    @GetMapping("findPerson/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id){
        return personService.findById(id);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity findPersonAddresses(@PathVariable Integer id){
        return personService.findPersonAddresses(id);
    }

    @PostMapping("/savePerson")
    public ResponseEntity<Person> save(@RequestBody Person person){
         if(Objects.isNull(personService.save(person))){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
         return ResponseEntity.ok(person);
    }

    @PostMapping("/newAddress/{id}")
    public ResponseEntity saveAddress(@RequestBody Address address, @PathVariable Integer id){
        return personService.saveAddress(address, id);
    }

//    @PutMapping("/setMainAddress/{personId}/{addressId}")
//    public ResponseEntity<Person> saveMainAddress(@RequestBody Person updatedPerson,
//                                                  @PathVariable("personId") Integer personId,
//                                                  @PathVariable("addressId") Integer addressId) {
//        return personService.saveMainAddress(updatedPerson, personId, addressId);
//    }

    @PutMapping("/editPerson/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson, @PathVariable Integer id){
        return personService.updatePerson(updatedPerson, id);
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return personService.delete(id);
    }
}
