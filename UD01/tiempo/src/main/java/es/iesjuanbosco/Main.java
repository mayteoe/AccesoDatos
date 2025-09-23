package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.iesjuanbosco.PronosticoUtils;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Main {
    public static void main(String[] args) {

        try {
            // Crear el XmlMapper y registrar el módulo para fechas
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Leer el XML
            Pronostico pronostico = xmlMapper.readValue(new File("forecast.xml"), Pronostico.class);

            if (pronostico.getPronostico() != null) {
                int opcion;
                String dia;
               do {
                   opcion=PronosticoUtils.menu();
                   switch(opcion)
                   {
                   case 1: dia=PronosticoUtils.pedirDia();PronosticoUtils.mostrarPronosticoPorDia(pronostico,dia); break;
                   case 2:PronosticoUtils.mostrarPronostico5Dias(pronostico); break;
                   case 3: PronosticoUtils.mostrarPronosticoCompleto(pronostico); break;

                   }

               }while(opcion<4);

            } else {
                System.out.println("No se encontraron datos de forecast en el XML.");
            }

        } catch (IOException e) {
            System.err.println("Error leyendo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}