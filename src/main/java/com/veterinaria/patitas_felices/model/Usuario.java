package com.veterinaria.patitas_felices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombreUsuario;
    private String contraUsuario;
    private String emailUsuario;

    public long getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraUsuario(){
        return contraUsuario;
    }

    public void setContraUsuario(String contraUsuario){
        this.contraUsuario = contraUsuario;
    }

    public String getEmailUsuario(){
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario){
        this.emailUsuario = emailUsuario;
    }
}
