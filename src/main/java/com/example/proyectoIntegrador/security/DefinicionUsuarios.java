package com.example.proyectoIntegrador.security;

import com.example.proyectoIntegrador.entity.Usuario;
import com.example.proyectoIntegrador.entity.UsuarioRole;
import com.example.proyectoIntegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DefinicionUsuarios implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passCifrado= bCryptPasswordEncoder.encode("admin");
        String passCifrado2= bCryptPasswordEncoder.encode("user");
        Usuario usuario= new Usuario("Christian","admin",passCifrado,"christian@digitalhouse.com", UsuarioRole.ROLE_ADMIN);
        Usuario usuario2= new Usuario("Aylin","admin",passCifrado,"aylin@digitalhouse.com", UsuarioRole.ROLE_ADMIN);
        Usuario usuario3= new Usuario("Franco","user",passCifrado,"franco@digitalhouse.com", UsuarioRole.ROLE_USER);
        System.out.println("pass: "+passCifrado);
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);

    }
}
