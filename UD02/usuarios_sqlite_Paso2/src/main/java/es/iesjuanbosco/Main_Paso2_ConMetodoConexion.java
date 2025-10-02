package es.iesjuanbosco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Usuario {
    private int cod;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String localidad;
    private List<String> telefonos;

    public Usuario(int cod, String nombre, String apellidos, String direccion, String localidad) {
        this.cod = cod;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.localidad = localidad;
        this.telefonos = new ArrayList<>();
    }

    public int getCod() { return cod; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public String getLocalidad() { return localidad; }
    public List<String> getTelefonos() { return telefonos; }
    public void addTelefono(String telefono) { this.telefonos.add(telefono); }
}
public class Main_Paso2_ConMetodoConexion {

    // NUEVO: Método centralizado para obtener conexión
    // Cambiar de BD ahora es tan fácil como modificar ESTE método
    private static Connection obtenerConexion() throws SQLException {
        // Para SQLite:
        String url = "jdbc:sqlite:D:/Acceso a Datos/SQLiteDatabaseBrowserPortable/prueba.db";
        return DriverManager.getConnection(url);

        // Para cambiar a MySQL solo descomentar estas líneas:
        // String url = "jdbc:mysql://localhost:3306/mibasedatos";
        // String user = "root";
        // String password = "password";
        // return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        System.out.println("=== PASO 2: CON MÉTODO DE CONEXIÓN CENTRALIZADO ===\n");

        // MEJORA: Usamos el método en lugar de la URL directa
        try (Connection conexion = obtenerConexion();
             PreparedStatement psUsuarios = conexion.prepareStatement(
                     "SELECT * FROM usuarios WHERE localidad LIKE ?");
             PreparedStatement psTelefonos = conexion.prepareStatement(
                     "SELECT * FROM telefonos WHERE cod = ?")) {

            System.out.println("✓ Conexión establecida");
            psUsuarios.setString(1, "T%");

            try (ResultSet rsUsuarios = psUsuarios.executeQuery()) {
                while (rsUsuarios.next()) {
                    Usuario usuario = new Usuario(
                            rsUsuarios.getInt("cod"),
                            rsUsuarios.getString("nombre"),
                            rsUsuarios.getString("apellidos"),
                            rsUsuarios.getString("direccion"),
                            rsUsuarios.getString("localidad")
                    );

                    psTelefonos.setInt(1, usuario.getCod());
                    try (ResultSet rsTelefonos = psTelefonos.executeQuery()) {
                        while (rsTelefonos.next()) {
                            usuario.addTelefono(rsTelefonos.getString("telefono"));
                        }
                    }

                    mostrarUsuario(usuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }
    }

    private static void mostrarUsuario(Usuario usuario) {
        System.out.println("┌─────────────────────────────────────────");
        System.out.printf("│ ID: %d%n", usuario.getCod());
        System.out.printf("│ Nombre: %s %s%n", usuario.getNombre(), usuario.getApellidos());
        System.out.printf("│ Dirección: %s, %s%n", usuario.getDireccion(), usuario.getLocalidad());
        System.out.println("│ Teléfonos:");

        if (usuario.getTelefonos().isEmpty()) {
            System.out.println("│   (sin teléfonos)");
        } else {
            for (String telefono : usuario.getTelefonos()) {
                System.out.printf("│   - %s%n", telefono);
            }
        }
        System.out.println("└─────────────────────────────────────────\n");
    }
}
