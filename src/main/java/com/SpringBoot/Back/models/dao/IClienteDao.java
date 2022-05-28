package com.SpringBoot.Back.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.SpringBoot.Back.models.entity.Cliente;
import com.SpringBoot.Back.models.entity.Region;

public interface IClienteDao extends JpaRepository<Cliente, Long> {
    // Nota: la  interface dao es para acceso a datos

    @Query("from Region")
    public List<Region> findAllRegiones();
}


