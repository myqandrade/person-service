package com.github.myqandrade.personservice.controllers;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Address>> findALl(){
        return ResponseEntity.status(HttpStatus.OK).body(addressRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressRepository.save(address));
    }
}
