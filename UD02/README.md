# RA2. Desarrolla aplicaciones que gestionan información almacenada en bases de datos relacionales identificando y utilizando mecanismos de conexión

## 1.- Instalación, creación y gestión de bases de datos embebidas (SQLite, HSQLDB, Derby, H2, …) y corporativas (Oracle, MySQL)

- **a)** Se han valorado las ventajas e inconvenientes de utilizar conectores. (5%)
- **b)** Se han utilizado gestores de bases de datos embebidos y en servidores. (5%)
- **c)** Se ha utilizado el conector idóneo en la aplicación y se ha establecido la conexión. (15%)

**T.E:** 25%

---

## 3.- Desarrollo de aplicaciones Java con acceso a BD relacionales a través de conectores

- **d)** Se ha definido la estructura de la base de datos. (10%)
- **e)** Se han desarrollado aplicaciones que modifican el contenido de la base de datos. (15%)
- **f)** Se han definido los objetos destinados a almacenar el resultado de las consultas. (15%)
- **g)** Se han desarrollado aplicaciones que efectúan consultas. (15%)
- **h)** Se han ejecutado procedimientos en la base de datos. (10%)
- **i)** Se han gestionado las transacciones. (10%)

**T.E:** 75%

# CONTENIDOS

## UD02. Manejo de conectores de acceso a base de datos relacionales

1. Introducción. Desfase objeto-relacional. Usos
2. Arquitectura. Funcionamiento. Conectores
3. Establecimiento de conexiones a bases de datos relacionales: Driver, URLs, Clases `Connection`, ...
   - **3.1.** BDR embebidas: SQLite, H2, Apache Derby, HSQLDB
   - **3.2.** BDR cliente/servidor: Oracle
   - **3.3.** MySQL
4. Operaciones con BDR desde Java
   - **4.1.** Consulta de metadatos: Clase `DatabaseMetaData`, Interfaz `ResultMetaData`
   - **4.2.** Ejecución de sentencias SQL: Interfaces `Statement`, `PreparedStatement`, `ResultSet`, ...
   - **4.3.** Ejecución de procedimientos: Interfaz `CallableStatement`
5. Gestión de errores
