package com.veterinaria.patitas_felices.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.veterinaria.patitas_felices.model.Mascota;
import com.veterinaria.patitas_felices.repository.MascotaRepository;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository){
        this.mascotaRepository = mascotaRepository;
    }

//---Lista todas las mascotas creadas---
    public List<Mascota> listar(){
        return mascotaRepository.findAll();
    }

//---Busca entre las mascotas creadas por su id---
    public Mascota buscarPorId(Long id){
        Optional<Mascota> mascota =  mascotaRepository.findById(id);
        if(mascota.isPresent()){
            return mascota.get();
        }
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Mascota no encontrado"
        );
    }

//---Busca entre las mascotas creadas por su nombre---
    public List<Mascota> buscarPorNombre(String nombre){
        return mascotaRepository.findByNombreMascotaContainingIgnoreCase(nombre);
    }

//---Crea una mascota---
    public Mascota crearMascota(Mascota usuario){
    if(usuario.getNombreMascota() == null || usuario.getNombreMascota().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El Nombre es obligatorio"
        );
    }
        if(usuario.getEspecieMascota() == null || usuario.getEspecieMascota().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Especie es obligatoria"
        );
    }
    usuario.setIdMascota(null);
        return mascotaRepository.save(usuario);
    }

//---Modifica los datos de la mascota por el id---
        public Mascota modificarMascota(Long id, Mascota datosNuevos){
    Mascota mascota = buscarPorId(id);
    if(datosNuevos.getNombreMascota() == null || datosNuevos.getNombreMascota().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El Nombre es obligatorio"
        );
    }
        mascota.setNombreMascota(datosNuevos.getNombreMascota());
        mascota.setEspecieMascota(datosNuevos.getEspecieMascota());
        mascota.setGeneroMascota(datosNuevos.getGeneroMascota());
        mascota.setTamanioMascota(datosNuevos.getTamanioMascota());
        mascota.setEdadMascota(datosNuevos.getEdadMascota());
        mascota.setMascotaCastrada(datosNuevos.getMascotaCastrada());
        mascota.setMascotaVacunada(datosNuevos.getMascotaVacunada());
        return mascotaRepository.save(mascota);
    }

//---Elimina una mascota por id---
public void eliminarMascota(Long id) {
    Mascota mascota = buscarPorId(id);
    mascotaRepository.delete(mascota);
    }

//---Lista las Mascotas del Usuario---
    public List<Mascota> listarPorUsuario(Long idUsuario){
        return mascotaRepository.findByIdUsuario(idUsuario);
    }

//---Busca mascotas de un usuario por nombre---
    public List<Mascota> buscarPorNombreYUsuario(Long idUsuario, String nombre){
        return mascotaRepository.findByIdUsuarioAndNombreMascotaContainingIgnoreCase(idUsuario, nombre);
    }
}
