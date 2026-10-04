/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   TAD RepositorioLecturas - VERSION CORREGIDA

   Este es el Tipo Abstracto de Dato del semestre: quien lo usa
   solo conoce las operaciones publicas de abajo. NO deberia
   saber que por dentro hay un arreglo.

   Ese es el contrato. Respetalo mientras lo completas.
   ========================================================= */  // <--- ¡AQUÍ ESTABA EL ERROR! Faltaba el */

public class RepositorioLecturas {

    private static final int CAPACIDAD_INICIAL = 10;

    private LecturaSensor[] lecturas;
    private int cantidad;

    public RepositorioLecturas() {
        this.lecturas = new LecturaSensor[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    // ---------- OPERACIONES DEL CONTRATO ----------

    /**
     * Agrega una lectura al final del repositorio.
     * @return true si se agrego, false si no habia espacio
     */
    public boolean agregar(LecturaSensor lectura) {
        if (lectura == null) return false; // Seguridad extra

        if (cantidad == lecturas.length) {
            redimensionar();
        }
        lecturas[cantidad] = lectura;
        cantidad++;
        return true;
    }

    /**
     * Devuelve la lectura que esta en la posicion indicada.
     */
    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return null;
        }
        return lecturas[posicion];
    }

    /**
     * Cantidad de lecturas almacenadas actualmente.
     */
    public int tamano() {
        return cantidad;
    }

    /**
     * Elimina la lectura de la posicion indicada.
     */
    public void eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        // Correr todo una posición a la izquierda
        for (int i = posicion; i < cantidad - 1; i++) {
            lecturas[i] = lecturas[i + 1];
        }
        // Anular la última posición ocupada (evita el "duplicado fantasma")
        lecturas[cantidad - 1] = null;
        cantidad--;
    }

    /**
     * Busca la primera lectura de una estacion.
     */
    public LecturaSensor buscarPorEstacion(String idSensor) {
        for (int i = 0; i < cantidad; i++) {
            if (lecturas[i] != null && lecturas[i].getIdSensor().equals(idSensor)) {
                return lecturas[i];
            }
        }
        return null;
    }

    /**
     * Reemplaza la lectura de una posicion por otra.
     */
    public void actualizar(int posicion, LecturaSensor nueva) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException(
                    "Posición inválida: " + posicion + " (rango 0.." + (cantidad - 1) + ")");
        }
        if (nueva == null) {
            throw new IllegalArgumentException("La lectura no puede ser null");
        }
        lecturas[posicion] = nueva;
    }

    /**
     * Duplica la capacidad interna del arreglo conservando el contenido.
     */
    private void redimensionar() {
        LecturaSensor[] nuevo = new LecturaSensor[lecturas.length * 2];
        for(int i = 0; i < cantidad; i++) {
            nuevo[i] = lecturas[i];
        }
        lecturas = nuevo;
    }

    /**
     * Promedio de PM2.5 de todas las lecturas almacenadas.
     * CORREGIDO: Se evita la división por cero si el repositorio está vacío.
     */
    public double promedioPm25() {
        if (cantidad == 0) {
            return 0.0; // <--- SOLUCIÓN AL TODO 4: Evita ArithmeticException
        }

        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            // Como eliminamos con cuidado, las posiciones < cantidad son válidas
            suma = suma + lecturas[i].getPm25();
        }
        return suma / cantidad;
    }
}