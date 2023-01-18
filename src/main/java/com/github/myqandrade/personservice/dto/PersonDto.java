package com.github.myqandrade.personservice.dto;

import com.github.myqandrade.personservice.model.Address;
import com.github.myqandrade.personservice.model.Person;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PersonDto {

    private Integer id;
    private String name;
    private String birthDate;
    private Set<AddressDto> addresses = new HashSet<>();

    public void setAddress(AddressDto addressDto){
        addresses.add(addressDto);
    }

    public static PersonDto convert(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setBirthDate(person.getBirthDate());
        for(Address address : person.getAddresses()){
            AddressDto convertedAddress = AddressDto.convert(address);
            personDto.setAddress(convertedAddress);
        }

        return personDto;
    }
}
