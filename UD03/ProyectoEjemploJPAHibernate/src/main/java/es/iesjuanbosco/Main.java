package es.iesjuanbosco;

import es.iesjuanbosco.model.Departamento;
import es.iesjuanbosco.model.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.time.LocalDate;

public class Main {
    private static EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("Persistencia");

    public static void main(String[] args) {

    /*Operaciones CRUD básicas con JPA:

    1. Crear (Create):
       - Crear una nueva instancia de una entidad.
 */
        EntityManager manager = emf.createEntityManager();
        manager.getTransaction().begin();

        // Creamos 2 Departamentos
        Departamento d1 = new Departamento("Ventas", "Madrid");
        Departamento d2 = new Departamento("Marketing", "Barcelona");

        // Persistimos los departamentos para obtener sus IDs
        manager.persist(d1);
        manager.persist(d2);

        // Creamos 4 Empleados y los asignamos a los departamentos (uso del constructor modificado)
        Empleado e1 = new Empleado("Juan", "García", LocalDate.of(2002, 5, 15), d1); // Ventas
        Empleado e2 = new Empleado("María", "López", LocalDate.of(2004, 8, 20), d1); // Ventas
        Empleado e3 = new Empleado("Carlos", "Ruiz", LocalDate.of(2000, 1, 10), d2); // Marketing
        Empleado e4 = new Empleado("Ana", "Sanz", LocalDate.of(1995, 11, 25), d2);   // Marketing

        // Añadimos los empleados a la lista del departamento (para mantener la bidireccionalidad)
        d1.addEmpleado(e1);
        d1.addEmpleado(e2);
        d2.addEmpleado(e3);
        d2.addEmpleado(e4);

        // Persistimos los empleados para guardarlos en la base de datos
        manager.persist(e1);
        manager.persist(e2);
        manager.persist(e3);
        manager.persist(e4);

        manager.getTransaction().commit();
        System.out.println("Se crearon 2 Departamentos y 4 Empleados.");
        manager.close();
        imprimirDepartamentosTodos();

/*
    2. Leer (Read):
         - Usar EntityManager.find() para obtener una entidad por su ID.*/
         EntityManager Manager = emf.createEntityManager();
         e1= Manager.find(Empleado.class, 1);
         System.out.println("Leer empleado con id 1");
         imprimeEmpleado(e1);

    /*3. Actualizar (Update):
            - Obtener la entidad a actualizar (usando find o una consulta).
            - Modificar los atributos de la entidad.
            - Usar EntityManager.merge() para guardar los cambios.*/
        EntityManager updateManager = emf.createEntityManager();
        e1 = updateManager.find(Empleado.class, 1);
        e1.setNombre("Lucas");
        updateManager.getTransaction().begin();
        updateManager.merge(e1);
        updateManager.getTransaction().commit();
        updateManager.close();
        imprimeEmpleado(e1);

        /*
    4. Borrar (Delete):
            - Obtener la entidad a borrar.
            - Usar EntityManager.remove() para eliminarla de la base de datos.*/

        manager = emf.createEntityManager();
        e1 = manager.find(Empleado.class, 1);
        manager.getTransaction().begin();
        manager.remove(e1);
        manager.getTransaction().commit();
        manager.close();
        System.out.println("Empleado con ID 1 eliminado.");
        imprimirEmpleadosTodos();

    }

    private static void imprimirEmpleadosTodos() {
        EntityManager manager = emf.createEntityManager();
        List<Empleado> empleados = manager.createQuery("FROM Empleado").getResultList();
        for (Empleado empleado : empleados) {
            System.out.println("Empleado ID: " + empleado.getId() +
                    ", Nombre: " + empleado.getNombre() +
                    " " + empleado.getApellidos() +
                    ", Nacimiento: " + empleado.getFechaNacimiento() +
                    ", Departamento: " + empleado.getDepartamento().getNombre());

        }

        System.out.println("hoy hay " + empleados.size() + " empleados en el sistema");
    }

    private static void imprimeEmpleado(Empleado e){
        System.out.println("Empleado ID: " + e.getId() +
                ", Nombre: " + e.getNombre() +
                " " + e.getApellidos() +
                ", Nacimiento: " + e.getFechaNacimiento() +
                ", Departamento: " + e.getDepartamento().getNombre());
    }
    private static void imprimirDepartamentosTodos() {
        EntityManager manager = emf.createEntityManager();
        List<Departamento> departamentos = manager.createQuery("FROM Departamento").getResultList();
        for (Departamento departamento : departamentos) {
            System.out.println("\n[DEPARTAMENTO] ID: " + departamento.getId() +
                    ", Nombre: " + departamento.getNombre() +
                    ", Localidad: " + departamento.getLocalidad());
            // LEER EMPLEADOS ASOCIADOS (navegación por la relación)
            // Al acceder a d.getEmpleados(), Hibernate carga la lista automáticamente
            // (ya sea EAGER o LAZY en este punto de acceso dentro del EntityManager).

            if (departamento.getEmpleados().isEmpty()) {
                System.out.println("   -> (No tiene empleados asociados en la BD)");
            } else {
                System.out.println("   --- EMPLEADOS ASOCIADOS (" + departamento.getEmpleados().size() + ") ---");
                for (Empleado e : departamento.getEmpleados()) {
                    // Imprime la información del Empleado
                    System.out.println("      ID: " + e.getId() +
                            " | Nombre: " + e.getNombre() +
                            " " + e.getApellidos() +
                            " | Nacimiento: " + e.getFechaNacimiento());
                }
            }
        }
    }
}


