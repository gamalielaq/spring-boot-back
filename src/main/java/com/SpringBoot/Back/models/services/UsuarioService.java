package com.SpringBoot.Back.models.services;

import java.util.List;
import java.util.stream.Collectors;

import com.SpringBoot.Back.models.dao.IUsuarioDao;
import com.SpringBoot.Back.models.entity.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// UserDetailsService -> Interfaz de spring

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService   {

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    @Transactional(readOnly = true) 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioDao.findByUsername(username);

        if(usuario == null) {
            logger.error("Error en el login: Usuario no existe en el sistema");
            throw new UsernameNotFoundException("Error en el login: Usuario no existe en el sistema");
        }
        
        List<GrantedAuthority> authorities = usuario.getRoles()
            .stream()
            .map((rol) -> new SimpleGrantedAuthority(rol.getNombre()))
            .peek( (authority) -> logger.info("Role: "+ authority.getAuthority()))
            .collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUserName(String username) {
        return this.usuarioDao.findByUsername(username);
    }
    
}
