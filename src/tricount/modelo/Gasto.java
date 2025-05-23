package tricount.modelo;

/**
 * Representa un gasto con un asunto y un importe positivo.
 * <p>
 * Esta clase permite crear, modificar y comparar gastos,
 * asegurando que los valores sean válidos mediante excepciones.
 * </p>
 * 
 * @author GAI
 * @version 1.0
 */
public class Gasto implements Comparable<Gasto> {
    private String asunto;
    private double importe;

    /**
     * Crea un nuevo gasto con el asunto e importe indicados.
     *
     * @param asunto el motivo del gasto, no puede estar vacío
     * @param importe el valor del gasto, debe ser positivo
     * @throws IllegalArgumentException si el asunto está vacío o el importe es negativo
     */
    public Gasto(String asunto, double importe) {
        if (asunto.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El gasto debe tener un asunto.");
        }
        this.importe = this.importeValido(importe);
        this.asunto = asunto;
    }

    /**
     * Devuelve una representación en forma de cadena del gasto.
     *
     * @return una cadena con el asunto y el importe formateado con dos decimales
     */
    @Override
    public String toString() {
        return "Asunto: " + asunto + "\n" + "importe: " + String.format("%.2f", importe);
    }

    /**
     * Establece un nuevo importe para el gasto.
     *
     * @param gasto el nuevo importe, debe ser positivo
     * @throws IllegalArgumentException si el importe es negativo
     */
    public void setImporte(double gasto) {
        this.importe = this.importeValido(gasto);
    }

    /**
     * Establece un nuevo asunto para el gasto.
     *
     * @param asunto el nuevo asunto, no puede estar vacío
     * @throws IllegalArgumentException si el asunto está vacío
     */
    public void setAsunto(String asunto) {
        if (asunto.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El gasto debe tener un asunto.");
        }
        this.asunto = asunto;
    }

    /**
     * Devuelve el asunto del gasto.
     *
     * @return el asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Devuelve el importe del gasto.
     *
     * @return el importe
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Compara este gasto con otro según su importe, en orden descendente.
     *
     * @param o el otro gasto a comparar
     * @return un valor negativo si este gasto tiene mayor importe, positivo si es menor, 0 si son iguales
     */
    @Override
    public int compareTo(Gasto o) {
        return (int) (-this.importe - o.importe);
    }

    /**
     * Calcula el código hash de este gasto.
     *
     * @return el código hash basado en el asunto y el importe
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
        result = prime * result + Double.hashCode(importe);
        return result;
    }

    /**
     * Compara dos gastos por igualdad de asunto e importe.
     *
     * @param obj el objeto a comparar
     * @return true si tienen el mismo asunto e importe, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gasto other = (Gasto) obj;
        if (asunto == null) {
            if (other.asunto != null)
                return false;
        } else if (!asunto.equals(other.asunto))
            return false;
        if (Double.doubleToLongBits(importe) != Double.doubleToLongBits(other.importe))
            return false;
        return true;
    }

    /**
     * Verifica que el importe sea válido.
     *
     * @param importe el importe a validar
     * @return el importe si es válido
     * @throws IllegalArgumentException si el importe es negativo
     */
    private double importeValido(double importe) {
        if (importe < 0) {
            throw new IllegalArgumentException("ERROR: El importe no puede ser 0 o negativo.");
        }
        return importe;
    }
}
