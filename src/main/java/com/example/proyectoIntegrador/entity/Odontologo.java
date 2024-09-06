package com.example.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String matricula;
    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Turno> turnos = new ArrayList<>();


    public Odontologo(String id) {
        this.id = Long.parseLong(id);
    }
}
