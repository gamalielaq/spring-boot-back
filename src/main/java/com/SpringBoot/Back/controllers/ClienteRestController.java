package com.SpringBoot.Back.controllers;

import java.util.List;

import com.SpringBoot.Back.models.entity.Cliente;
import com.SpringBoot.Back.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" }) // Cors configurado
@RestController
@RequestMapping("/api")
public class ClienteRestController {

   /*
    * Recuerda: Que en spring cuando se declara un beans con su tipo generico ya
    * sea una interfaz o clase abstracta(IClienteService), va a buscar el primer
    * candidato, una clase concreta que implemente esta interfaz y la va a
    * inyectar,
    * en este ejemplo se estaría inyectando la clase "ClienteServiceImpl"
    * si tiene mas de una se tendria que utilizar un qualifier
    */
   @Autowired
   private IClienteService clienteService;

   @GetMapping("/clientes")
   public List<Cliente> index() {
      return this.clienteService.findAll();
   };

   /*
    * ¿Porque se usa la interfaz ICLienteService y no ClienteServiceImpl?
    * 
    * 
    * Hola son buenas prácticas ya que permite desacoplar (eliminar la
    * dependencia) de una implementación concreta en el controlador.
    * 
    * Esto permite en el día de mañana una mejor mantención, por ejemplo si
    * necesitamos cambiar la implementación por otra clase concreta, supongamos el
    * caso que estamos trabajando con JDBC y queremos emigrar a Hibernate, sólo
    * necesitamos cambiar la implementación, la forma en que se realizan las
    * consultas y operaciones, pero los nombres de métodos son los mismos (sólo
    * cambia la forma en que se hacen), si usamos la forma concreta con el operador
    * new tendríamos que modificar en todas las clases en que se esté utilizando,
    * supongamos que tengamos más de 20 clases que la usa, la mantención se
    * transforma en algo poco práctico y tedioso, mientras si usamos interfaz (y
    * spring) simplemente creamos una nueva clase que implemente la interfaz con
    * los mismos métodos y realizamos las nuevas implementaciones con los cambios,
    * luego en las clases que la usan(clases cliente) inyectamos o obtenemos el
    * bean pero haciendo referencia al tipo de dato de la interfaz (tipo genérico),
    * por lo tanto en las clases que la usan, no sabe si la implementación es con
    * JDBC o con Hibernate y tampoco le interesa, lo importante son los métodos de
    * la interfaz, lo que hacen y no el cómo lo hacen, protocolo de comportamiento
    * y no el cómo y con qué se implementan estos métodos.
    * 
    * Por esto es importante usar interfaces en las clase de servicio, y de
    * repositorio (clases que acceden a los datos o DAO), en general son buenas
    * prácticas para tener un mejor código, reutilizable y sea los más simple
    * posible de mantener (extensible).
    * 
    * Resumiendo nos da un diseño de qué deben hacer nuestras clases concretas, es
    * un protocolo o contrato de implementación! así como un contrato de trabajo,
    * si cumple el contrato, entonces puede ejecutar las tareas y trabajos de dicho
    * contrato!
    * 
    * Diseños y buenas prácticas, además nos permite desacoplar el código concreto
    * de nuestras clases y hacerlo más genérico, de esa forma nuestra programación
    * es más flexible y extensible a cambios del futuro y no depende ni se acopla a
    * una sola implementación, ventajas son muchas más, pero bueno, al final buenas
    * prácticas y ingeniería de software, poo etc.
    * 
    * saludos!
    * 
    * Referecnia: Video 38 del curso
    * 
    */

   @GetMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.OK)
   public Cliente show(@PathVariable Long id) {
      return this.clienteService.findById(id);
   }

   @PostMapping("clientes")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente create(@RequestBody Cliente cliente) {
      return this.clienteService.save(cliente);
   }

   @PutMapping("clientes/{id}")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
      Cliente clienteActual = this.clienteService.findById(id);
      clienteActual.setApellido(cliente.getApellido());
      clienteActual.setNombre(cliente.getNombre());
      clienteActual.setEmail(cliente.getEmail());
      return this.clienteService.save(clienteActual);
   }

   @DeleteMapping("clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable Long id) {
      this.clienteService.delete(id);
   }

}
