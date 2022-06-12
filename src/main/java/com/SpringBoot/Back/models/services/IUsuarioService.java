package com.SpringBoot.Back.models.services;

import com.SpringBoot.Back.models.entity.Usuario;


public interface IUsuarioService {

    public Usuario findByUserName(String username);
}
