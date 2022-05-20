package com.SpringBoot.Back.controllers;

import java.util.List;

import com.SpringBoot.Back.models.entity.Cliente;
import com.SpringBoot.Back.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" }) // Cors configurado
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    /*
    * Recuerda: Que en spring cuando se declara un beans con su tipo generico ya
    * sea una interfaz o clase abstracta(IClienteService), va a buscar el primer candidato, una clase concreta que implemente esta interfaz y la va a inyectar,
    * en este ejemplo se estaría inyectando la clase "ClienteServiceImpl"
    * si tiene mas de una se tendria que utilizar un qualifier
    */
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    };
}
