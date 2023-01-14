package com.github.myqandrade.personservice.controllers;

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
}
