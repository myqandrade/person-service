package com.github.myqandrade.personservice.model;

import com.github.myqandrade.personservice.dto.AddressDto;
import com.github.myqandrade.personservice.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String birthDate;
    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_address_fk", referencedColumnName = "id")
    private Set<Address> addresses = new HashSet<>();

    public void setAdress(Address address){
        addresses.add(address);
    }

    public static Person convert(PersonDto personDto){
        Person person = new Person();
        person.setId(personDto.getId());
        person.setName(personDto.getName());
        person.setBirthDate(personDto.getBirthDate());
        for(AddressDto addressDto : personDto.getAddresses()){
            Address convertedAddress = Address.convert(addressDto);
            person.setAdress(convertedAddress);
        }

        return person;
    }
}
