package com.github.myqandrade.personservice.service;

import com.github.myqandrade.personservice.dto.AddressDto;
import com.github.myqandrade.personservice.dto.PersonDto;
import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import com.github.myqandrade.personservice.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;

    public List<PersonDto> findAll(){
        List<PersonDto> validatedPerson = new ArrayList<>();
        for(Person person : personRepository.findAll()){
            PersonDto personDto = PersonDto.convert(person);
            if(validatePerson(personDto)) {
                validatedPerson.add(personDto);
            }
            if(validatedPerson.isEmpty()){
                return null;
            }
        }
        return validatedPerson;
    }

    public PersonDto findById(Integer id){
        Optional<Person> person = personRepository.findById(id);
        PersonDto convertedPersonDto = PersonDto.convert(person.get());
        if(validatePerson(convertedPersonDto)){
            return convertedPersonDto;
        }
        return null;
    }

    public Set<AddressDto> findPersonAddresses(Integer id){
        Optional<Person> person = personRepository.findById(id);
        PersonDto convertedPersonDto = PersonDto.convert(person.get());
        if(validatePerson(convertedPersonDto)){
            return convertedPersonDto.getAddresses();
        }
        return null;
    }

    public PersonDto save(PersonDto personDto){
        PersonDto p = null;
        if((validatePerson(personDto))) {
            Person person = Person.convert(personDto);
            personRepository.save(person);
            p = PersonDto.convert(person);
        }
        return p;
    }

    public AddressDto saveAddress(AddressDto addressDto, Integer id){
        Optional<Person> person = personRepository.findById(id);
        PersonDto personDto = PersonDto.convert(person.get());
        Set<AddressDto> addresses = personDto.getAddresses();
        if(validatePerson(personDto)) {
            for (AddressDto ad : addresses) {
                if (ad.getAddress().equals(addressDto.getAddress())) {
                    return null;
                }
            }
        }
        personDto.setAddress(addressDto);
        Person updatedPerson = Person.convert(personDto);
        personRepository.save(updatedPerson);

        return addressDto;
    }

    public PersonDto updatePerson(PersonDto updatedPersonDto, Integer id){
        Optional<Person> p = personRepository.findById(id);
        Set<Address> addresses = p.get().getAddresses();

        for(Address ad : addresses){
            updatedPersonDto.setAddress(AddressDto.convert(ad));
        }

        if(validatePerson(updatedPersonDto)){
            updatedPersonDto.setId(p.get().getId());
            Person person = Person.convert(updatedPersonDto);
            personRepository.save(person);

            return PersonDto.convert(person);
        }

        return null;
    }

    public void deletePerson(Integer id){
        Optional<Person> person = personRepository.findById(id);
        PersonDto personDto = PersonDto.convert(person.get());
        if(validatePerson(personDto)){
            Person deletedPerson = Person.convert(personDto);
            personRepository.delete(deletedPerson);
        }
    }

    public Boolean validatePerson(PersonDto personDto){
        if(Objects.isNull(personDto)){
            return false;
        }
        List<AddressDto> mainAddresses = new ArrayList<>();
        for(AddressDto ad : personDto.getAddresses()){
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
