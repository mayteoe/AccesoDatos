package es.iesjuanbosco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class EmployeeJacksonJson {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Alumno alumno1 = new Alumno(1,"Manolo", "García", 8,LocalDate.of(2000,1,16));
        Alumno alumno2 = new Alumno(2,"Ana", "Quiralte", 7,LocalDate.of(2002,4,18));
        Alumno alumno3 = new Alumno(3,"Antonio", "Pérez", 4,LocalDate.of(2003,5,6));
        Curso curso = new Curso("dam2", "Desarrollo de Aplicaciones", 2025,"IES Juan Bosco" ,List.of(alumno1, alumno2, alumno3));

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); //Registrar el módulo para las fechas
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); //formato legible de las fechas
            objectMapper.writeValue(new File("curso.json"), curso);

            System.out.println("Creado el fichero curso.json");

            Curso cursoLeido = objectMapper.readValue(new File("curso.json"),Curso.class);
            System.out.println("curso:"+ cursoLeido.getNombre() +
                    ", Código: " + cursoLeido.getCodigo() + ", año: " + cursoLeido.getAño()  );
            for (Alumno alumno: cursoLeido.getAlumnos()){
                System.out.println("Alumno: " + alumno.getNombre() + " " +
                    alumno.getApellidos()+ ". fecha nacimiento: " + alumno.getFechaNacimiento() );
            }
        } catch (IOException e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}