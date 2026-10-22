package com.example.accessing_data_jpa;

import org.springframework.data.repository.CrudRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "addressbooks")
public interface AddressBookRepository extends CrudRepository<AddressBook, Integer> {

}
