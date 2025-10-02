
// ============================================================================
// PASO 3: CREAR CLASE ConexionBD (Patrón Singleton opcional)
// ============================================================================
// Creamos una clase específica para gestionar conexiones
// Esta es la forma PROFESIONAL de hacerlo
// ============================================================================

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

/**
 * CLASE PARA GESTIONAR CONEXIONES A LA BASE DE DATOS
 *
 * VENTAJAS:
 * - Centraliza toda la configuración de BD
 * - Fácil cambiar entre diferentes bases de datos
 * - Puede incluir pool de conexiones en el futuro
 * - Reutilizable en todo el proyecto
 */
class ConexionBD {

    // Configuración para diferentes bases de datos
    private enum TipoBD {
        SQLITE, MYSQL, ORACLE
    }

    // Cambiar aquí para usar otra base de datos
    private static final TipoBD TIPO_BD_ACTUAL = TipoBD.SQLITE;

    // Configuración SQLite
    private static final String SQLITE_URL = "jdbc:sqlite:D:/Acceso a Datos/SQLiteDatabaseBrowserPortable/prueba.db";

    // Configuración MySQL
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/mibasedatos";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "password";

    // Configuración Oracle
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String ORACLE_USER = "system";
    private static final String ORACLE_PASSWORD = "oracle";

    /**
     * Obtiene una conexión a la base de datos configurada
     */
    public static Connection getConexion() throws SQLException {
        switch (TIPO_BD_ACTUAL) {
            case SQLITE:
                return DriverManager.getConnection(SQLITE_URL);

            case MYSQL:
                return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);

            case ORACLE:
                return DriverManager.getConnection(ORACLE_URL, ORACLE_USER, ORACLE_PASSWORD);

            default:
                throw new SQLException("Tipo de base de datos no soportado");
        }
    }

    /**
     * Cierra una conexión de forma segura
     */
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✓ Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene el nombre de la base de datos actual
     */
    public static String getNombreBD() {
        return TIPO_BD_ACTUAL.toString();
    }
}

public class Main_Paso3 {

    public static void main(String[] args) {
        System.out.println("=== PASO 3: CON CLASE ConexionBD ===");
        System.out.println("Base de datos activa: " + ConexionBD.getNombreBD() + "\n");

        // MEJORA: Usamos ConexionBD.getConexion() en lugar de crear la conexión directamente
        try (Connection conexion = ConexionBD.getConexion();
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

// MEJORAS LOGRADAS:
// ✅ Clase ConexionBD profesional
// ✅ Soporta múltiples bases de datos
// ✅ Cambio fácil: solo modificar TIPO_BD_ACTUAL
// ✅ Método para cerrar conexiones
// ✅ Código reutilizable en todo el proyecto

