package com.veterinaria.patitas_felices.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.veterinaria.patitas_felices.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long>{
    List<Mascota> findByNombreContaining(String nombreMascota);
}
