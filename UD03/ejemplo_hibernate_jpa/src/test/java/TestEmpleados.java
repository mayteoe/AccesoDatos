import es.iesjuanbosco.modelo.Empleado;
import jakarta.persistence.Persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TestEmpleados {

    private static EntityManagerFactory emf=
            Persistence.createEntityManagerFactory("Persistencia");

    public static void main(String[] args) {
        /*Crear el gestor de persitencia*/

        EntityManager manager= emf.createEntityManager();

        /*Podemos usar el gestor de persistencia*/
        /*Para listar, crear, borrar, modificar...*/

       // List<Empleado> empleados = manager.createQuery("FROM Empleado").getResultList();


        /*Para insertar empleados*/

        Empleado e=new Empleado("Ana","Gomez",new java.util.Date(1990,5,15));
        Empleado e2=new Empleado("Luis","Martinez",new java.util.Date(1985,3,22));

        //1.Iniciar transacción
        manager.getTransaction().begin();

        //2.Persistir el objeto .Almacena el objeto en la base de datos
        manager.persist(e);
        manager.persist(e2);

        //3.Confirmar la transacción
        manager.getTransaction().commit();



        //4.Cerrar el gestor de persistencia
        manager.close();

        //Imprimir los empleados

        imprimirTodos();

        //Intentamos cambiar el nombre del empleado fuera del gestor de persistencia
        manager= emf.createEntityManager();
        manager.getTransaction().begin();

        e.setNombre("Luisa");
       manager.merge(e); //Actualiza el objeto en la base de datos
        manager.getTransaction().commit();
        manager.close();
        imprimirTodos();
      //  System.out.println("En esta base de datos hay"+ empleados.size()+" empleados");
    }

    private static void imprimirTodos() {
        EntityManager manager= emf.createEntityManager();
        List <Empleado> empleados = manager.createQuery("FROM Empleado").getResultList();
        for (Empleado empleado : empleados) {
            System.out.println(empleado);
        }

        System.out.println("hoy hay " + empleados.size() + " empleados en el sistema");
    }
}
