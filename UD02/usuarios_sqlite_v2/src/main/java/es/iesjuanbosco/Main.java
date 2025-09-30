package es.iesjuanbosco;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
            System.out.println("=== EJEMPLO JDBC CON SQLITE ===");
            // PASO 1: Definir la URL de conexión a la base de datos
            String url = "jdbc:sqlite:D:/Acceso a Datos/SQLiteDatabaseBrowserPortable/prueba.db";

            // PASO 2: Usar try-with-resources para que los recursos se cierren automáticamente
            // Esto es MUY IMPORTANTE: Connection, Statement y ResultSet deben cerrarse siempre
            // Con try-with-resources se cierran automáticamente al terminar el bloque try

            try (
                    // 2.1: Establecer la conexión con la base de datos
                    Connection conexion = DriverManager.getConnection(url);

                    // 2.2: Crear PreparedStatement para consultar usuarios
                    // IMPORTANTE: Usar PreparedStatement (con ?) en lugar de Statement
                    // Esto previene SQL Injection y mejora el rendimiento
                    PreparedStatement psUsuarios = conexion.prepareStatement(
                            "SELECT * FROM usuarios WHERE localidad LIKE ?"
                    );

                    // 2.3: Crear PreparedStatement para consultar teléfonos
                    // Lo preparamos FUERA del bucle para reutilizarlo
                    PreparedStatement psTelefonos = conexion.prepareStatement(
                            "SELECT * FROM telefonos WHERE cod = ?"
                    )
            ) {

                System.out.println("✓ Conexión establecida con éxito");

                // PASO 3: Establecer parámetros del PreparedStatement
                // El índice empieza en 1 (no en 0 como los arrays)
                psUsuarios.setString(1, "T%");

                // PASO 4: Ejecutar la consulta y obtener resultados
                try (ResultSet rsUsuarios = psUsuarios.executeQuery()) {

                    System.out.println("\n=== USUARIOS CON LOCALIDAD QUE EMPIEZA POR 'T' ===\n");

                    // PASO 5: Recorrer los resultados de usuarios
                    while (rsUsuarios.next()) {

                        // 5.1: Obtener datos del usuario actual
                        // Podemos usar el nombre de la columna o su índice (empieza en 1)
                        int cod = rsUsuarios.getInt("cod");
                        String nombre = rsUsuarios.getString("nombre");
                        String apellidos = rsUsuarios.getString("apellidos");
                        String direccion = rsUsuarios.getString("direccion");
                        String localidad = rsUsuarios.getString("localidad");

                        // 5.2: Mostrar datos del usuario
                        System.out.println("┌─────────────────────────────────────────");
                        System.out.printf("│ ID: %d%n", cod);
                        System.out.printf("│ Nombre: %s %s%n", nombre, apellidos);
                        System.out.printf("│ Dirección: %s, %s%n", direccion, localidad);

                        // PASO 6: Buscar teléfonos de este usuario
                        // IMPORTANTE: Reutilizamos el PreparedStatement creado fuera del bucle
                        psTelefonos.setInt(1, cod);

                        try (ResultSet rsTelefonos = psTelefonos.executeQuery()) {

                            System.out.println("│ Teléfonos:");
                            boolean tieneTelefonos = false;

                            // 6.1: Recorrer los teléfonos del usuario
                            while (rsTelefonos.next()) {
                                String telefono = rsTelefonos.getString("telefono");
                                System.out.printf("│   - %s%n", telefono);
                                tieneTelefonos = true;
                            }

                            // 6.2: Si no tiene teléfonos, indicarlo
                            if (!tieneTelefonos) {
                                System.out.println("│   (sin teléfonos registrados)");
                            }
                        }

                        System.out.println("└─────────────────────────────────────────\n");
                    }
                }

            } catch (SQLException e) {
                // PASO 7: Manejo de errores SQL
                System.err.println("\n❌ ERROR EN LA BASE DE DATOS:");

                // SQLite no usa códigos de error numéricos como Oracle
                // En su lugar, analiza el mensaje de error
                String mensaje = e.getMessage().toLowerCase();

                if (mensaje.contains("no such table")) {
                    System.err.println("   La tabla no existe en la base de datos");
                } else if (mensaje.contains("unable to open database")) {
                    System.err.println("   No se puede abrir la base de datos");
                    System.err.println("   Verifica que la ruta sea correcta");
                } else if (mensaje.contains("foreign key")) {
                    System.err.println("   Error de clave foránea");
                } else if (mensaje.contains("unique")) {
                    System.err.println("   Valor duplicado (violación de clave única)");
                } else {
                    System.err.println("   " + e.getMessage());
                }

                // Para depuración: muestra la traza completa del error
                e.printStackTrace();
            }

            System.out.println("✓ Recursos cerrados automáticamente (try-with-resources)");
        }
    }
