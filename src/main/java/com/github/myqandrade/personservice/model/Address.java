package com.github.myqandrade.personservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 9)
    private String zipcode;
    @Column(nullable = false)
    private String city;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
