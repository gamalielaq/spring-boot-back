package com.SpringBoot.Back.models.services;

import java.util.List;

import com.SpringBoot.Back.models.entity.Cliente;

public interface IClienteService {
    
    public List<Cliente> findAll();

}
