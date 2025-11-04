package es.iesjuanbosco.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "empleados")
@ToString(exclude = "departamento")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, unique = true)
    private Long id;
    @Column (name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column (name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    @Column (name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    // * Relación ManyToOne con Departamento.
     /* DUEÑO de la relación. @JoinColumn define la FK (departamento_id) en la tabla empleados.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    // Constructor que incluye el Departamento
    public Empleado(String nombre, String apellidos, LocalDate fechaNacimiento, Departamento departamento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.departamento = departamento;
    }


}