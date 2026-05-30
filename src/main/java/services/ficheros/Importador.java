package services.ficheros;

import excepciones.Excepciones;
import excepciones.YaImportado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Map;

public class Importador {

    // Mapa que define las columnas de cada tabla de la base de datos
    private static final Map<String, String[]> COLUMNAS = Map.of(
            "Banda", new String[]{
                "codigo", "nombre", "anios_actuacion", "lugar_origen", "genero"
            },
            "Musico", new String[]{
                "codigo", "codigo_banda", "nombre", "fecha_nacimiento",
                "lugar_residencia", "nacionalidad", "instrumento"
            },
            "Album", new String[]{
                "codigo", "autor", "codigo_banda", "codigo_musico",
                "titulo", "anio_publicacion", "tipo", "duracion"
            },
            "Cancion", new String[]{
                "codigo", "codigo_album", "posicion",
                "titulo", "compositor", "duracion"
            }
    );

    /**
     * Importa datos a una tabla de MySQL usando batch insert. Realiza
     * validaciones, limpieza de datos y control de duplicados.
     */
    public static void importar_MySQL(String tabla, String[][] datos)
            throws Excepciones {

        if (!COLUMNAS.containsKey(tabla)) {
            throw new Excepciones("Tabla no soportada: " + tabla);
        }

        // 1. VALIDACIÓN BÁSICA
        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar");
        }

        //  2. VALIDACIÓN REAL (ESTO ES LO QUE TE FALTABA)
        boolean hayDatosReales = false;

        for (String[] fila : datos) {
            if (fila != null
                    && fila.length > 0
                    && fila[0] != null
                    && !fila[0].trim().isEmpty()) {
                hayDatosReales = true;
                break;
            }
        }

        if (!hayDatosReales) {
            throw new Excepciones("El archivo no contiene datos válidos");
        }

        String[] cols = COLUMNAS.get(tabla);

        String columnasSQL = String.join(",", cols);

        String placeholders = String.join(",",
                java.util.Collections.nCopies(cols.length, "?"));

        String sql = "INSERT INTO " + tabla
                + " (" + columnasSQL + ") VALUES (" + placeholders + ")";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            List<String> duplicados = new java.util.ArrayList<>();

            int filaNum = 0;

            for (String[] fila : datos) {

                filaNum++;

                //  MEJORADO
                if (fila == null
                        || fila.length == 0
                        || fila[0] == null
                        || fila[0].trim().isEmpty()) {
                    continue;
                }

                int codigo;

                try {
                    codigo = Integer.parseInt(fila[0].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Código inválido en fila " + filaNum);
                    continue;
                }

                //  DUPLICADOS
                try (PreparedStatement psCheck = con.prepareStatement(
                        "SELECT 1 FROM " + tabla + " WHERE codigo = ?")) {

                    psCheck.setInt(1, codigo);

                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next()) {
                            duplicados.add("Fila " + filaNum + " código " + codigo);
                            continue;
                        }
                    }
                }

                // INSERT
                for (int i = 0; i < cols.length; i++) {

                    if (i < fila.length && fila[i] != null
                            && !fila[i].equalsIgnoreCase("null")
                            && !fila[i].trim().isEmpty()) {

                        ps.setObject(i + 1, fila[i].trim());

                    } else {
                        ps.setNull(i + 1, Types.VARCHAR);
                    }
                }

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            System.out.println("Importado a MySQL: " + tabla);

            if (!duplicados.isEmpty()) {
                throw new YaImportado("Duplicados:\n" + String.join("\n", duplicados));
            }

        } catch (Exception e) {
            throw new Excepciones("Error MySQL: " + e.getMessage());
        }
    }
}
