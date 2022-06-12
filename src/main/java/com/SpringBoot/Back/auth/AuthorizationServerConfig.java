package com.SpringBoot.Back.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


//Esta clase es la encargada de la configuración por el lado de OAuth2, es decir desde el proceso de login, crear el token validarlo todo lo relacionado a jwt

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    private BCryptPasswordEncoder  passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;


    //Medoto de conguracion para nuestras rutas de acceso 
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //tokenKeyAccess: tiene integrado por defecto la ruta --> http://localhost:8080/oauth/token
        security.tokenKeyAccess("permitAll()") // Ruta publica --> oauth/token(tokenKeyAccess: Metodo para generar el token cuando se autentica)
        .checkTokenAccess("isAuthenticated()"); // Endpoint que verifica el token y su firma (Solo acceden los clientes autenticados)
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //Configuración del cliente quien va a consumir nuestra aplicacion pude ser angular react, or mobile
        clients.inMemory().withClient("angularapp")
        .secret(passwordEncoder.encode("12345"))
        .scopes("read", "write") // alcance-- leer y escribir datos
        .authorizedGrantTypes("password", "refresh_token") // Tipo de autenticación es este caso con las contraseñas y usuarios de nuestro BD,  || refresh_token_ Token De acceso Renovado
        .accessTokenValiditySeconds(3600) //Tiempo de caducidad  3600 = 1 hora
        .refreshTokenValiditySeconds(3600); // Tiempo de validación

        /* 
            Nota:  authorizedGrantTypes
            password --> usuarios de nuestraba base de datos
            code --> el backend genera un redirecionamiento para generar un codigo de acceso para generar el token de acceso(example: Api Ansedia)
            Implicita --> Autenticacion mas debil usada para app publicas que no requieren mucha seguridad
        */
    }


    //Este metodo se encarga del proceso de authenticacion y validacion del token ( recibe el usuario y clave luego genera el token)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

        endpoints
        .authenticationManager(authenticationManager) // registramos el  authenticationManager que esta inyectado
        .tokenStore(tokenStore()) // tokenStore es opcional
        .accessTokenConverter(accessTokenConverter()) //registramos el accessTokenConverter -> Encargado al jwt --> almacena datos del usuario, roles y otra info que deseemos agregar(No agregar info sensible), 
        .tokenEnhancer(tokenEnhancerChain);
    }

    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
        return jwtAccessTokenConverter;
    }

}
