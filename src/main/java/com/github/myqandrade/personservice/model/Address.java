package com.github.myqandrade.personservice.model;

import com.github.myqandrade.personservice.dto.AddressDto;
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
    @Column
    private Boolean isMain;

    public static Address convert(AddressDto addressDto){
        Address address = new Address();
        address.setAddress(addressDto.getAddress());
        address.setZipcode(addressDto.getZipcode());
        address.setCity(addressDto.getCity());
        address.setIsMain(addressDto.getIsMain());

        return address;
    }

}
