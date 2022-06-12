package com.SpringBoot.Back.auth;

import java.util.HashMap;
import java.util.Map;

import com.SpringBoot.Back.models.entity.Usuario;
import com.SpringBoot.Back.models.services.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InfoAdicionalToken implements TokenEnhancer  {


    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
        Usuario usuario = this.usuarioService.findByUserName(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("InfoAdicional", "Hola Quetal ".concat(authentication.getName()));
        info.put("nombreUsuario", usuario.getUsername());
        info.put("userId", usuario.getId());
        info.put("name", usuario.getNombre());
        info.put("lastName", usuario.getApellido());
        info.put("email", usuario.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
    
}
