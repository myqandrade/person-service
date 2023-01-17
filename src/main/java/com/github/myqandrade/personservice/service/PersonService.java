package com.github.myqandrade.personservice.service;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll(){
        List<Person> validatedPerson = new ArrayList<>();
        for(Person person : personRepository.findAll()){
            if(validatePerson(person)) {
                validatedPerson.add(person);
            }
            if(validatedPerson.isEmpty()){
                return null;
            }
        }
        return validatedPerson;
    }

    public Person findById(Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(validatePerson(person.get())){
            return person.get();
        }
        return null;
    }

    public Set<Address> findPersonAddresses(Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(validatePerson(person.get())){
            return person.get().getAddresses();
        }
        return null;
    }

    public Person save(Person person){
        Person savedPerson = null;
        if((validatePerson(person))) {
            savedPerson = personRepository.save(person);
        }
        return savedPerson;
    }

    public Person saveAddress(Address address, Integer id){
        Optional<Person> person = personRepository.findById(id);
        Set<Address> addresses = person.get().getAddresses();
        if(validatePerson(person.get())) {
            for (Address ad : addresses) {
                if (ad.getAddress().equals(address.getAddress())) {
                    return null;
                }
            }
        }
        person.get().setAdress(address);
        return personRepository.save(person.get());
    }

    public Person updatePerson(Person updatedPerson, Integer id){
        Optional<Person> person = personRepository.findById(id);
        Set<Address> addresses = person.get().getAddresses();
        for (Address ad : addresses) {
            updatedPerson.setAdress(ad);
        }
        if (validatePerson(updatedPerson)) {
            updatedPerson.setId(person.get().getId());
            return personRepository.save(updatedPerson);
        }
        return null;
    }

    public void deletePerson(Integer id){
        Optional<Person> person = personRepository.findById(id);
        if(validatePerson(person.get())){
            personRepository.delete(person.get());
        }
    }

    public Boolean validatePerson(Person person){
        if(Objects.isNull(person)){
            return false;
        }
        List<Address> mainAddresses = new ArrayList<>();
        for(Address ad : person.getAddresses()){
            if(Objects.nonNull(ad.getIsMain()) && ad.getIsMain().equals(true)){
                mainAddresses.add(ad);
            }
        }
        if(mainAddresses.size() != 1){
            return false;
        }
        return true;
    }
}
