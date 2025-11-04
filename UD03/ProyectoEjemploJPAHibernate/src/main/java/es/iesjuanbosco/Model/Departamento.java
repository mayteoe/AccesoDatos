package es.iesjuanbosco.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departamentos")
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "localidad")
    private String localidad;

    /**
     * Relación OneToMany con Empleado.
     * mappedBy: "departamento" es el campo en la clase Empleado que contiene la FK.
     * cascade = CascadeType.ALL: Propaga operaciones.
     * orphanRemoval = true: Borra empleados si se quitan de esta lista.
     */
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados = new ArrayList<>();

    // Constructor sin ID ni lista de Empleados (para facilitar la creación inicial)
    public Departamento(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }

    // Método de utilidad para gestionar la bidireccionalidad
    public void addEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.setDepartamento(this);
    }


}