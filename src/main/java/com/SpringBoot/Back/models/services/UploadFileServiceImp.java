package com.SpringBoot.Back.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service // representa que es una clase de servicio(Logica de negocio), además es un componente de spring o beans de spring
public class UploadFileServiceImp implements IUploadService {
    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImp.class);

    private final static String DIRECTORIO_UPLOAD = "uploads";

    @Override
    public Resource cargaFoto(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = this.getPath(nombreFoto);
        Resource recurso = null;
        recurso = new UrlResource(rutaArchivo.toUri());

        if(!recurso.exists() && recurso.isReadable()) {
           rutaArchivo = Paths.get("src/main/resource/static/images").resolve("no-user.png").toAbsolutePath();
           recurso = new UrlResource(rutaArchivo.toUri());
           log.error("Error no se pudo cargar la imgen"+ nombreFoto);
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nomArchivo = UUID.randomUUID().toString() + "_"+ archivo.getOriginalFilename().replace(" ", "");
        Path rutaArchivo = this.getPath(nomArchivo);
        log.info(rutaArchivo.toString());
        Files.copy(archivo.getInputStream(), rutaArchivo);
        return nomArchivo;
    }

    @Override
    public boolean delete(String nombreFoto) {
      if(nombreFoto != null && nombreFoto.length() > 0) {
         Path  nutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
         File archivoFotoAnterior = nutaFotoAnterior.toFile();
         if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
            archivoFotoAnterior.delete();
            return true;
         }
        }
        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return  Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }    
}