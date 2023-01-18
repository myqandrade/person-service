package com.github.myqandrade.personservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.myqandrade.personservice.dto.AddressDto;
import com.github.myqandrade.personservice.dto.PersonDto;
import com.github.myqandrade.personservice.mock.PersonRepositoryMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {

    private PersonRepositoryMock personRepositoryMock;
    private PersonService personService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initMock(){
        this.personRepositoryMock = new PersonRepositoryMock();
        this.personService = new PersonService(personRepositoryMock);
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldBeCreatedPerson() throws JsonProcessingException {
        String json = """
                {
                "name" : "Mike Andrade",
                "birthDate" : "16/11/1994",
                "addresses" : [
                    {
                        "address" : "Rua Xablau 35",
                        "zipcode" : "21849-095",
                        "city" : "Rio de Janeiro"
                    },
                    {
                        "address" : "Rua Xampola 95",
                        "zipcode" : "21475-373",
                        "city" : "Rio de Janeiro",
                        "isMain" : true
                    }
                ]
            }
                """;

        personService.save(objectMapper.readValue(json, PersonDto.class));

        Assertions.assertEquals("Mike Andrade", personRepositoryMock.findAll().get(0).getName());
        Assertions.assertEquals("Rua Xablau 35", personRepositoryMock.findAll().get(0).getAddresses().stream().findFirst().get().getAddress());
        Assertions.assertEquals("16/11/1994", personRepositoryMock.findAll().get(0).getBirthDate());
    }

    @Test
    public void shouldNotBeCreatedPersonBecauseTwoMainAddress() throws JsonProcessingException {
        String json = """
                {
                "name" : "Mike Andrade",
                "birthDate" : "16/11/1994",
                "addresses" : [
                    {
                        "address" : "Rua Xablau 35",
                        "zipcode" : "21849-095",
                        "city" : "Rio de Janeiro",
                        "isMain": true
                    },
                    {
                        "address" : "Rua Xampola 95",
                        "zipcode" : "21475-373",
                        "city" : "Rio de Janeiro",
                        "isMain" : true
                    }
                ]
            }
                """;

        personService.save(objectMapper.readValue(json, PersonDto.class));
        Assertions.assertTrue(personRepositoryMock.findAll().isEmpty());
    }

    @Test
    public void shouldBeUpdatedPerson() throws JsonProcessingException {

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setAddress("Rua Itamotinga 176");
        addressDto.setZipcode("21985-094");
        addressDto.setCity("Rio de Janeiro");
        addressDto.setIsMain(true);


        PersonDto personDto = new PersonDto();
        personDto.setId(1);
        personDto.setName("Mike Andrade");
        personDto.setAddress(addressDto);
        personDto.setBirthDate("16/11/1994");

        personService.save(personDto);

        PersonDto personDto2 = new PersonDto();
        personDto2.setId(1);
        personDto2.setName("Mariana Marinho");
        personDto2.setBirthDate("14/01/2003");

        personService.updatePerson(personDto2, 1);

        Assertions.assertEquals("Mariana Marinho", personRepositoryMock.findById(1).get().getName());
    }
}
