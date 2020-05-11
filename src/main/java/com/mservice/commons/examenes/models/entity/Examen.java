package com.mservice.commons.examenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "examenes")
public class Examen {

    public Examen(){
        this.lstPreguntas = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> lstPreguntas;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignatura;

    @Transient
    private boolean respondido;

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }


    public void setLstPreguntas(List<Pregunta> lstPreguntas){
        this.lstPreguntas.clear();
        lstPreguntas.forEach(this::addPregunta);
    }



    public void addPregunta(Pregunta pregunta){
        this.lstPreguntas.add(pregunta);
        pregunta.setExamen(this);
    }

    public void removePregunta(Pregunta pregunta){
        this.lstPreguntas.add(pregunta);
        pregunta.setExamen(null);
    }
}
