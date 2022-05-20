package com.SpringBoot.Back.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.SpringBoot.Back.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
    // Nota: la  interface dao es para acceso a datos
}


