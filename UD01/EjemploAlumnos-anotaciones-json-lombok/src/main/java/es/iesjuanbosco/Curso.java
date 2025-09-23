package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Anotaciones para XML
@JsonRootName(value = "curso")
public class Curso {
    @JsonProperty("cod")
    private String codigo;
    private String nombre;
    private int año;
    @JsonIgnore
    private String facultad;
    private List<Alumno> alumnos;

  }
