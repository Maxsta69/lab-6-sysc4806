package com.example.accessing_data_jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "buddies")
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Integer> {
    List<BuddyInfo> findByName(String name);
}
