package com.github.myqandrade.personservice.controllers;

import com.github.myqandrade.personservice.dto.AddressDto;
import com.github.myqandrade.personservice.dto.PersonDto;
import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.service.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/listPerson")
    public ResponseEntity<List<PersonDto>> findAll(){
        if(Objects.isNull(personService.findAll())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("findPerson/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Integer id){
        if(Objects.isNull(personService.findById(id))){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Set<AddressDto>> findPersonAddresses(@PathVariable Integer id){
        if(Objects.isNull(personService.findPersonAddresses(id))){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personService.findPersonAddresses(id));
    }

    @PostMapping("/savePerson")
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto){
         if(Objects.isNull(personService.save(personDto))){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
         return ResponseEntity.ok(personDto);
    }

    @PostMapping("/newAddress/{id}")
    public ResponseEntity<AddressDto> saveAddress(@RequestBody AddressDto addressDto, @PathVariable Integer id){
        if(Objects.isNull(personService.saveAddress(addressDto, id))){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personService.saveAddress(addressDto, id));
    }

    @PutMapping("/editPerson/{id}")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto updatedPersonDto, @PathVariable Integer id){
        if(Objects.isNull(personService.updatePerson(updatedPersonDto, id))){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(updatedPersonDto);
    }

    @DeleteMapping("/deletePerson/{id}")
    public void deletePerson(@PathVariable Integer id){
        personService.deletePerson(id);
    }
}
