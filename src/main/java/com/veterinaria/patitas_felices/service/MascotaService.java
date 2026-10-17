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

    public List<Mascota> listar(){
        return mascotaRepository.findAll();
    }

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

    public List<Mascota> buscarPorNombre(String nombre){
        return mascotaRepository.findByNombreContaining(nombre);
    }

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
                "La Contraseña es obligatoria"
        );
    }
    usuario.setIdMascota(null);
        return mascotaRepository.save(usuario);
    }

        public Mascota modificarMascota(Long id, Mascota datosNuevos){
    Mascota mascota = buscarPorId(id);
    if(datosNuevos.getNombreMascota() == null || datosNuevos.getNombreMascota().isBlank()){
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La Contraseña es obligatoria"
        );
    }
        mascota.setNombreMascota(datosNuevos.getNombreMascota());
        return mascotaRepository.save(mascota);
    }

public void eliminarMascota(Long id) {
    Mascota mascota = buscarPorId(id);
    mascotaRepository.delete(mascota);
    }
}
