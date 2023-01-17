package com.github.myqandrade.personservice.dto;

import com.github.myqandrade.personservice.model.Address;
import lombok.Data;
@Data
public class AddressDto {

    private String address;
    private String zipcode;
    private String city;
    private Boolean isMain;

    public static AddressDto convert(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress(address.getAddress());
        addressDto.setZipcode(address.getZipcode());
        addressDto.setCity(address.getCity());
        addressDto.setIsMain(address.getIsMain());

        return addressDto;
    }
}
