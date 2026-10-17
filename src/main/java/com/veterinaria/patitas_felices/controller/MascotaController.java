package com.veterinaria.patitas_felices.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.veterinaria.patitas_felices.model.Mascota;
import com.veterinaria.patitas_felices.service.MascotaService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/mascotas")
public class MascotaController {
        private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> listar() {
        return mascotaService.listar();
    }

    @GetMapping("/{id}")
    public Mascota buscarPorId(@PathVariable Long id) {
        return mascotaService.buscarPorId(id);
    }

    @PostMapping
    public Mascota crearMascota(@RequestBody Mascota mascota) {
        return mascotaService.crearMascota(mascota);
    }

    @PutMapping("/{id}")
    public Mascota modificarMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
        return mascotaService.modificarMascota(id, mascota);
    }

    @DeleteMapping("/{id}")
    public String eliminarMascota(@PathVariable Long id){
        mascotaService.eliminarMascota(id);
        return "Mascota eliminda correctamente";
    }

}
