package es.iesjuanbosco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Main {
    public static void main(String[] args) {
// Ruta de la base de datos en tu disco duro portable
                String rutaDB = "D:/Acceso a Datos/SQLiteDatabaseBrowserPortable/prueba.db";
                String url = "jdbc:sqlite:D:/AccesoDatos/SQLiteDatabaseBrowserPortable/prueba.db" + rutaDB;

                Connection conexion = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    // Conectar a la base de datos
                    conexion = DriverManager.getConnection(url);
                    System.out.println("Conexión establecida con éxito.");

                    // Crear un Statement
                    stmt = conexion.createStatement();

                    // Ejecutar consulta
                    String sql = "select * from usuarios where localidad='Madridejos'";
                    rs = stmt.executeQuery(sql);

                    // Mostrar resultados
                    System.out.println("Datos de usuarios:");
                    while (rs.next()) {
                        int cod = rs.getInt("cod");
                        String nombre = rs.getString("nombre");
                        String apellidos = rs.getString("apellidos");
                        String direccion = rs.getString("direccion");
                        String localidad = rs.getString("localidad");

                        System.out.println(cod + " | " + nombre + " | " + apellidos + " | " + direccion + " | " + localidad);
                    }

                } catch (SQLException e) {
                    System.out.println("Error al conectar o consultar la base de datos:");
                    e.printStackTrace();
                } finally {
                    // Cerrar recursos
                    try {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conexion != null) conexion.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }



