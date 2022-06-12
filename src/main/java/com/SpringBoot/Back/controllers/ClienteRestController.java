package com.SpringBoot.Back.controllers;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.SpringBoot.Back.models.entity.Cliente;
import com.SpringBoot.Back.models.entity.Region;
import com.SpringBoot.Back.models.services.IClienteService;
import com.SpringBoot.Back.models.services.IUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

   @Autowired
   private IUploadService uploadService;

   @GetMapping("/clientes")
   public ResponseEntity<?> index() {
      Map<String, Object> response = new HashMap<>();
      List<Cliente> cliente = this.clienteService.findAll();
      response.put("clientes", cliente);
      response.put("code", 0);
      response.put("message", null);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
   };

   @GetMapping("/clientes/page/{page}")
   public Page<Cliente> index(@PathVariable Integer page) {
      Pageable pageable = PageRequest.of(page, 4);  //4 registros por página
      return this.clienteService.findAll(pageable);
   }

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

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/clientes/{id}")
   public ResponseEntity<?> show(@PathVariable Long id) {
      Cliente cliente = null;
      Map<String, Object> response = new HashMap<>();
      try {
         cliente = this.clienteService.findById(id);
      } catch (DataAccessException e) {
         response.put("message", e.getMessage().concat(" Error al realizar la consulta en la base de datos"));
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      if (cliente != null) {
         return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
      }
      response.put("message",
            "El cliente con El ID: ".concat(id.toString().concat(" no existe en en la base de datos")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
   }

   @Secured("ROLE_ADMIN")
   @PostMapping("clientes")
   public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
      Cliente clienteNew = null;
      Map<String, Object> response = new HashMap<>();
      if (result.hasErrors()) {
         // Api stream: Pragramacion Funcional
         List<String> errors = result.getFieldErrors()
               .stream()
               .map((error) -> "El Campo '" + error.getField() + "' " + error.getDefaultMessage())
               .collect(Collectors.toList());

         response.put("errors", errors);
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
      }
      try {
         clienteNew = this.clienteService.save(cliente);
      } catch (Exception e) {
         response.put("message", "Error al realizar el insert en la base de datos");
         response.put("error", e.getMessage().concat(": "));
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("message", "El cliente a sido creado con exito");
      response.put("cliente", clienteNew);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

   }

   @Secured("ROLE_ADMIN")
   @PutMapping("clientes/{id}")
   public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
      Cliente clienteActual = this.clienteService.findById(id);
      Cliente clienteUpdate = null;
      Map<String, Object> response = new HashMap<>();

      if (result.hasErrors()) {
         List<String> errors = result.getFieldErrors()
               .stream()
               .map((error) -> "El Campo '" + error.getField() + "' " + error.getDefaultMessage())
               .collect(Collectors.toList());

         response.put("errors", errors);
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
      }

      if (clienteActual == null) {
         response.put("code", 1);
         response.put("message",
               "El cliente con el ID: ".concat(id.toString()).concat(" No existe en la base de datos"));
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }

      try {
         clienteActual.setApellido(cliente.getApellido());
         clienteActual.setNombre(cliente.getNombre());
         clienteActual.setEmail(cliente.getEmail());
         clienteActual.setRegion(cliente.getRegion());

         clienteUpdate = this.clienteService.save(clienteActual);
      } catch (Exception e) {
         response.put("message", "Error al actualizar el cliente");
         response.put("code", -1);
         response.put("Error", e.getMessage());
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("cliente", clienteUpdate);
      response.put("code", 0);
      response.put("message", "Cliente actualizado con éxito");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

   }

   @Secured("ROLE_ADMIN")
   @DeleteMapping("clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public ResponseEntity<?> delete(@PathVariable Long id) {
      Map<String, Object> response = new HashMap<>();
      try {
         Cliente cliente = this.clienteService.findById(id);
         String nombreFotoAnterior = cliente.getFoto(); 
         this.uploadService.delete(nombreFotoAnterior);
         this.clienteService.delete(id);
      } catch (Exception e) {
         response.put("message", "Error al elminar el cliente");
         response.put("code", -1);
         response.put("Error", e.getMessage());
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("message", "Cliente elminado con éxito");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
   }


   @Secured({"ROLE_ADMIN","ROLE_USER"})
   @PostMapping("clientes/upload")
   public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
      Map<String, Object> response = new HashMap<>();
      Cliente cliente = this.clienteService.findById(id);
      
      if(cliente == null){
         response.put("code", -1);
         response.put("message", "Cliente no encontrado");
         return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }

      if(!archivo.isEmpty()) {
         String nombreArchivo = null;
         try {
            nombreArchivo = this.uploadService.copiar(archivo);
         } catch (Exception e) {
            response.put("message", "Error al subir la imagen "+ nombreArchivo);
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }
         
         String nombreFotoAnterior = cliente.getFoto();
         this.uploadService.delete(nombreFotoAnterior);
         
         cliente.setFoto(nombreArchivo);
         this.clienteService.save(cliente);

         response.put("cliente", cliente);
         response.put("code", 0);
         response.put("message", "Imagen subida correctamente");
      }
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
   }

   @GetMapping("/uploads/img/{nombreFoto:.+}")
   public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
      Resource recurso = null;
      try {
         recurso = this.uploadService.cargaFoto(nombreFoto);
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
      HttpHeaders cabecera = new HttpHeaders();
      cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename()+"\""); // Cabecera para forzar la descarga
      return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
   }

   @Secured("ROLE_ADMIN")
   @GetMapping("/clientes/regiones")
   public List<Region> listarRegiones() {
         return this.clienteService.findAllRegiones();
   }
}
