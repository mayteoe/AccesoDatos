package es.iesjuanbosco;

import java.util.List;

public class Curso {
    private String codigo;
    private String nombre;
    private int año;
    private List<Alumno> alumnos;

    public Curso() {
    }

    public Curso(String codigo, String nombre, int año, List<Alumno> alumnos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.año = año;
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", año=" + año +
                ", alumnos=" + alumnos +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
