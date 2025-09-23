package es.iesjuanbosco;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Alumno alumno1 = new Alumno("Manolo", "García", LocalDate.of(2025, 5, 15));
        Alumno alumno2 = new Alumno("Ana", "Quiralte", LocalDate.of(2002, 6, 22));
        Alumno alumno3 = new Alumno("Antonio", "Martínez", LocalDate.of(2000, 1, 13));
        Curso curso = new Curso("dam2", "Desarrollo de Aplicaciones", 2025, List.of(alumno1, alumno2, alumno3));
        System.out.println(alumno1.toString());

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        xmlMapper.writeValue(new File("curso.xml"), curso);
        System.out.println("Creado el fichero curso.xml");
        Curso cursoLeido = xmlMapper.readValue(new File("curso.xml"),Curso.class);
        System.out.println("curso:"+ cursoLeido.getNombre() + ", Código: " + cursoLeido.getCodigo() + ", año: " + cursoLeido.getAño()  );
        for (Alumno alumno: cursoLeido.getAlumnos()){
            System.out.println("Alumno: " + alumno.getNombre() + " " +
               alumno.getApellidos()+ ". fecha nacimiento: " + alumno.getFechaNacimiento() );
        }
    }
}