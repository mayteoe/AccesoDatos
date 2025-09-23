package es.iesjuanbosco;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Alumno {


    @JsonProperty("id_alumno")
    private int id;
    private String nombre;
    private String apellidos;
    @JsonIgnore
    private double nota; //No se exporta


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;


    public Alumno() {
    }

    public Alumno(int id, String nombre, String apellidos, double nota, LocalDate fechaNacimiento) {
        this.id=id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nota=nota;
        this.fechaNacimiento=fechaNacimiento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getApellidos() {
        return apellidos;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nota=" + nota +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(nombre, alumno.nombre) && Objects.equals(apellidos, alumno.apellidos) && Objects.equals(nota, alumno.nota);
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }



    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}
