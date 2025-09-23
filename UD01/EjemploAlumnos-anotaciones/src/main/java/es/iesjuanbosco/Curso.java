package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
//Anotaciones para XML
@JacksonXmlRootElement(localName = "curso")
public class Curso {
    @JacksonXmlProperty(isAttribute = true)
    private String codigo;
    private String nombre;
    private int año;
    @JsonIgnore
    private String facultad;
    @JacksonXmlElementWrapper(localName = "alumnos")
    private List<Alumno> alumnos;

    public Curso() {
    }


    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public Curso(String codigo, String nombre, int año,String facultad, List<Alumno> alumnos) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.año = año;
        this.facultad=facultad;
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
