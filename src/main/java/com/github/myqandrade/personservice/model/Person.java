package com.github.myqandrade.personservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String birthDate;
//    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "main_address_id", referencedColumnName = "addressId")
//    private Address mainAddress;
    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_address_fk", referencedColumnName = "id")
    private Set<Address> addresses;

    public void setAdress(Address address){
        addresses.add(address);
    }
}
