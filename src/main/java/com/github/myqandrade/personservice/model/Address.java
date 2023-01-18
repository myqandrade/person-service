package com.github.myqandrade.personservice.model;

import com.github.myqandrade.personservice.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        address.setAddressId(addressDto.getAddressId());
        address.setAddress(addressDto.getAddress());
        address.setZipcode(addressDto.getZipcode());
        address.setCity(addressDto.getCity());
        address.setIsMain(addressDto.getIsMain());

        return address;
    }

}
