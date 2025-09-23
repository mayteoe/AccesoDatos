package es.iesjuanbosco;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {


    @JsonProperty("id_alumno")
    private int id;
    private String nombre;
    private String apellidos;
    @JsonIgnore
    private double nota; //No se exporta
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;



}
