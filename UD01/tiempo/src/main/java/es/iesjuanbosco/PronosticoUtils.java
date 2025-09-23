package es.iesjuanbosco;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PronosticoUtils {
    public static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter HORA_FORMATO = DateTimeFormatter.ofPattern("HH:mm");

    public static void mostrarPronosticoPorDia(Pronostico pronostico, String diaBuscado) {
        boolean encontrado = false;
        for (Tiempo tiempo : pronostico.getPronostico().getListaTiempo()) {
            String dia = tiempo.getFechaIni().format(FECHA_FORMATO);
            if (dia.equals(diaBuscado)) {
                encontrado = true;
                mostrarPronostico(tiempo);
                System.out.println("------------------------------");

            }

        }

    }

    public static void mostrarPronostico( Tiempo tiempo) {
        // Día y hora
        String dia = tiempo.getFechaIni().format(FECHA_FORMATO);
        String desde = tiempo.getFechaIni().format(HORA_FORMATO);
        String hasta = tiempo.getFechaFin().format(HORA_FORMATO);
        System.out.println("Día: " + dia + " desde:" + desde + " hasta:" + hasta);

        // Temperatura
        if (tiempo.getTemperatura() != null) {
            System.out.println("Temperatura: " + tiempo.getTemperatura().toString());

        }

        // Viento
        if (tiempo.getDireccionViento() != null) {
            System.out.println("Viento: " + tiempo.getVelocidadViento().getMps() + " m/s, " + tiempo.getDireccionViento().getDescripcion());
        }

        // Precipitación
        if (tiempo.getPrecipitacion() != null) {
            Double prob = tiempo.getPrecipitacion().getProbabilidad();
            if (prob != null && prob > 0) {
                System.out.println("Probabilidad de precipitación: " + prob + "%");
            } else {
                System.out.println("Sin precipitación");
            }
        }
        // Simbología
        if (tiempo.getSimbolo() != null) {
            System.out.println("Estado del cielo: " + tiempo.getSimbolo().getName());
            System.out.println("Icono : http://openweathermap.org/img/wn/" + tiempo.getSimbolo().getVar() + "@2x.png");
        }
    }
    public static void mostrarPronostico5Dias(Pronostico pronostico) {
        LocalDate hoy = LocalDate.now();
        LocalDate plusDays5= hoy.plusDays(5);

        for (Tiempo tiempo : pronostico.getPronostico().getListaTiempo()) {
            LocalDate fecha = tiempo.getFechaIni().toLocalDate();
            if (fecha.isEqual(hoy) || (fecha.isBefore(plusDays5) && fecha.isAfter(hoy))) {

                mostrarPronostico(tiempo);
            }
            System.out.println("------------------------------");
        }
    }

    public static void mostrarPronosticoCompleto(Pronostico pronostico) {
        for (Tiempo tiempo : pronostico.getPronostico().getListaTiempo()) {
            mostrarPronostico(tiempo);
            System.out.println("------------------------------");
        }
    }

    public static int menu(){
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Mostrar pronóstico para un día específico");
            System.out.println("2. Mostrar pronóstico para los próximos 5 días");
            System.out.println("3. Mostrar pronostico completo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción (1-4): ");
        } while (!scanner.hasNextInt() || (opcion = scanner.nextInt()) < 1 || opcion > 4);
        return opcion;

    }
    public static String pedirDia(){
        Scanner scanner = new Scanner(System.in);
        String dia;
        System.out.print("Ingrese el día (dd/MM/yyyy): ");
        dia = scanner.nextLine();
        return dia;
    }

}
