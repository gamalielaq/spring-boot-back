package com.SpringBoot.Back.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.Back.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {
    // Nota: la  interface dao es para acceso a datos
}


