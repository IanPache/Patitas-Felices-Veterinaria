package com.veterinaria.patitas_felices.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.veterinaria.patitas_felices.model.Usuario;
import com.veterinaria.patitas_felices.repository.UsuarioRepository;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

//---Lista los Usuarios---
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

//---Busca entre las usuarios creados por su id---
    public Usuario buscarPorId(Long id){
        Optional<Usuario> usuario =  usuarioRepository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Usuario no encontrado"
        );
    }

//---Busca entre las usuarios creados por su nombre---
    public List<Usuario> buscarPorNombre(String nombre){
        return usuarioRepository.findByNombreUsuarioContainingIgnoreCase(nombre);
    }

//---Crea usuarios---
    public Usuario crearUsuario(Usuario usuario){
    if(usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El Nombre es obligatorio"
        );
    }
        if(usuario.getContraUsuario() == null || usuario.getContraUsuario().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Contraseña es obligatoria"
        );
    }
    usuario.setIdUsuario(null);
        return usuarioRepository.save(usuario);
    }

//---Modifica el nombre de los usuarios---
        public Usuario modificarNombre(Long id, Usuario datosNuevos){
    Usuario usuario = buscarPorId(id);
    if(datosNuevos.getNombreUsuario() == null || datosNuevos.getNombreUsuario().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Contraseña es obligatoria"
        );
    }
        usuario.setNombreUsuario(datosNuevos.getNombreUsuario());
        return usuarioRepository.save(usuario);
    }

//---Elimina usuarios---
public void eliminarUsuario(Long id) {
    Usuario usuario = buscarPorId(id);
    usuarioRepository.delete(usuario);
    }
}