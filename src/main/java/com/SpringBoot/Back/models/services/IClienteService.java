package com.SpringBoot.Back.models.services;

import java.util.List;

import com.SpringBoot.Back.models.entity.Cliente;
import com.SpringBoot.Back.models.entity.Region;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {
    
    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente save(Cliente cliente);

    public void delete(Long id);

    public Cliente findById(Long id);

    public List<Region> findAllRegiones();
}
