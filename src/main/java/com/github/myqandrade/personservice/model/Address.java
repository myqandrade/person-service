package com.github.myqandrade.personservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue
    private Integer addressId;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 9)
    private String zipcode;
    @Column(nullable = false)
    private String city;

}
