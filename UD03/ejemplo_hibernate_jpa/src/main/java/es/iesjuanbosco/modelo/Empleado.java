package es.iesjuanbosco.modelo;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "empleados")
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="codigo", nullable = false, unique = true)
    private Long codigo;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="apellidos", nullable = false)
    private String apellidos;

    @Column(name="fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    public Empleado(String nombre, String apellidos, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }
}
