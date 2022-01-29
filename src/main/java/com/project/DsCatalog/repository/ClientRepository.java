package com.project.DsCatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.DsCatalog.entity.Client;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long>{

}
