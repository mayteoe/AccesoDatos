
// ============================================================================
// PASO 4: DAO CON GESTIÓN DE CONEXIONES
// ============================================================================
// Combinamos el DAO con la gestión centralizada de conexiones
// ============================================================================

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

/**
 * Gestión centralizada de conexiones
 */
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

/**
 * Interfaz DAO
 */
interface IUsuarioDAO {
    List<Usuario> obtenerPorLocalidad(String localidad);
    Usuario obtenerPorCod(int cod);
    List<Usuario> obtenerTodos();
    boolean insertar(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(int cod);
}

/**
 * Implementación del DAO que usa ConexionBD
 */
class UsuarioDAOImpl implements IUsuarioDAO {

    @Override
    public List<Usuario> obtenerPorLocalidad(String localidad) {
        List<Usuario> usuarios = new ArrayList<>();

        // MEJORA: Usamos ConexionBD.getConexion() en lugar de crear la conexión directamente
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement psUsuarios = conn.prepareStatement(
                     "SELECT * FROM usuarios WHERE localidad LIKE ?");
             PreparedStatement psTelefonos = conn.prepareStatement(
                     "SELECT * FROM telefonos WHERE cod = ?")) {

            psUsuarios.setString(1, localidad + "%");

            try (ResultSet rs = psUsuarios.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = mapearUsuario(rs);
                    cargarTelefonos(usuario, psTelefonos);
                    usuarios.add(usuario);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario obtenerPorCod(int cod) {
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM usuarios WHERE cod = ?")) {

            ps.setInt(1, cod);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios")) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }

        return usuarios;
    }

    @Override
    public boolean insertar(Usuario usuario) {
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO usuarios (nombre, apellidos, direccion, localidad) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDireccion());
            ps.setString(4, usuario.getLocalidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE usuarios SET nombre=?, apellidos=?, direccion=?, localidad=? WHERE cod=?")) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDireccion());
            ps.setString(4, usuario.getLocalidad());
            ps.setInt(5, usuario.getCod());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int cod) {
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM usuarios WHERE cod = ?")) {

            ps.setInt(1, cod);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            return false;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("cod"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("direccion"),
                rs.getString("localidad")
        );
    }

    private void cargarTelefonos(Usuario usuario, PreparedStatement ps) throws SQLException {
        ps.setInt(1, usuario.getCod());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuario.addTelefono(rs.getString("telefono"));
            }
        }
    }
}

public class Main_Paso4 {

    public static void main(String[] args) {
        System.out.println("=== PASO 4: DAO CON GESTIÓN DE CONEXIONES ===");


        IUsuarioDAO usuarioDAO = new UsuarioDAOImpl();

        // El main es súper simple y no sabe NADA de conexiones
        List<Usuario> usuarios = usuarioDAO.obtenerPorLocalidad("T");

        System.out.println("Usuarios encontrados: " + usuarios.size() + "\n");

        for (Usuario usuario : usuarios) {
            mostrarUsuario(usuario);
        }

        System.out.println("\n📌 VENTAJA: Para cambiar de SQLite a MySQL,");
        System.out.println("   solo hay que modificar TIPO_BD_ACTUAL en ConexionBD");
        System.out.println("   ¡El resto del código NO cambia!");
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

// ============================================================================
// PATRÓN DAO COMPLETO Y PROFESIONAL ✅
// ============================================================================
//
// ESTRUCTURA FINAL:
// ├── Usuario.java (Modelo/POJO)
// ├── ConexionBD.java (Gestión centralizada de conexiones)
// ├── IUsuarioDAO.java (Interfaz - define QUÉ operaciones)
// ├── UsuarioDAOImpl.java (Implementación - define CÓMO)
// └── Main.java (Presentación - usa el DAO)
//
// VENTAJAS:
// ✅ Conexiones centralizadas en ConexionBD
// ✅ Cambio fácil entre SQLite, MySQL, Oracle
// ✅ Solo modificar una constante: TIPO_BD_ACTUAL
// ✅ DAO independiente de la base de datos específica
// ✅ Main súper simple sin conocimiento de JDBC
// ✅ Código profesional, mantenible y escalable
// ============================================================================