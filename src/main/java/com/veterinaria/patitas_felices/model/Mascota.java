package com.veterinaria.patitas_felices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Mascotas")
public class Mascota {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idMascota;
    
    private long idUsuario;
    private String nombreMascota;
    private String especieMascota;
    private String generoMascota;
    private String tamanioMascota;
    private int edadMascota;
    private boolean mascotaCastrada;
    private boolean mascotaVacunada;

    public Long getIdMascota(){
        return idMascota;
    }

    public void setIdMascota(Long idMascota){
        this.idMascota = idMascota;
    }

    public String getNombreMascota(){
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota){
        this.nombreMascota = nombreMascota;
    }

    public String getEspecieMascota(){
        return especieMascota;
    }

    public void setEspecieMascota(String especieMascota){
        this.especieMascota = especieMascota;
    }

    public String getGeneroMascota(){
        return generoMascota;
    }

    public void setGeneroMascota(String generoMascota){
        this.generoMascota = generoMascota;
    }

    public String getTamanioMascota(){
        return tamanioMascota;
    }

    public void setTamanioMascota(String tamanioMascota){
        this.tamanioMascota = tamanioMascota;
    }

    public int getEdadMascota(){
        return edadMascota;
    }

    public void setEdadMascota(int edadMascota){
        this.edadMascota = edadMascota;
    }

    public  boolean getMascotaCastrada(){
        return mascotaCastrada;
    }

    public void setMascotaCastrada(boolean mascotaCastrada){
        this.mascotaCastrada = mascotaCastrada;
    }

        public  boolean getMascotaVacunada(){
        return mascotaVacunada;
    }

    public void setMascotaVacunada(boolean mascotaVacunada){
        this.mascotaVacunada = mascotaVacunada;
    }
}
