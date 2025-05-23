package tricount.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una persona que puede registrar gastos y calcular su aportación total.
 * También permite calcular deudas entre varias personas según los gastos realizados.
 * 
 * @author GAI
 * @version 1.0
 */
public class Persona implements Comparable<Persona> {
    private String nombre;
    public List<Gasto> gastos = new ArrayList<>();

    /**
     * Crea una persona con un nombre y una aportación inicial.
     *
     * @param nombre nombre de la persona
     * @param dineroAportado cantidad inicial aportada (debe ser mayor o igual a 0)
     * @throws IllegalArgumentException si el dinero aportado es negativo
     */
    public Persona(String nombre, double dineroAportado) {
        if (dineroAportado < 0) {
            throw new IllegalArgumentException("ERROR: una persona no puede aportar dinero negativo");
        }
        this.nombre = nombre;
    }

    /**
     * Crea una persona con nombre y sin aportación inicial.
     *
     * @param nombre nombre de la persona
     */
    public Persona(String nombre) {
        this(nombre, 0);
    }

    /**
     * Añade un gasto a la lista de gastos de la persona.
     *
     * @param gasto el gasto a añadir
     */
    public void añadirGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    /**
     * Muestra por consola el nombre y el total de dinero aportado por la persona.
     */
    public void mostrar() {
        System.out.println("Nombre: " + this.nombre);
        System.out.printf("Dinero aportado: %.2f%n", this.getDineroAportado());
    }

    /**
     * Muestra por consola la lista de gastos ordenados por importe descendente.
     */
    public void mostarGastos() {
        gastos.sort((g1, g2) -> (int) (g2.getImporte() - g1.getImporte()));
        if (!gastos.isEmpty()) {
            System.out.println(nombre);
            for (Gasto gasto : gastos) {
                System.out.println(gasto);
                System.out.println();
            }
        }
    }

    /**
     * Devuelve el total de dinero aportado por esta persona (suma de sus gastos).
     *
     * @return el total del dinero aportado
     */
    public double getDineroAportado() {
        double dineroAportado = 0;
        for (Gasto g : gastos) {
            dineroAportado += g.getImporte();
        }
        return dineroAportado;
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Elimina un gasto de la lista de esta persona.
     *
     * @param g el gasto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarGasto(Gasto g) {
        try {
            return gastos.remove(g);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula y devuelve un resumen con el total gastado por todos,
     * el gasto medio por persona y un listado de deudas entre personas.
     *
     * @param personas lista de personas con sus respectivos gastos
     * @return resumen con las deudas calculadas
     */
    public static String finalizar(List<Persona> personas) {
        double gastoTotal = 0;
        int numPersonas = personas.size();

        for (Persona persona : personas) {
            gastoTotal += persona.getDineroAportado();
        }

        double gastoPorPersona = gastoTotal / numPersonas;
        double[] balance = new double[numPersonas];

        for (int i = 0; i < numPersonas; i++) {
            balance[i] = personas.get(i).getDineroAportado() - gastoPorPersona;
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("Gasto total: %.2f€%n", gastoTotal));
        resultado.append(String.format("Gasto por persona: %.2f€%n", gastoPorPersona));
        resultado.append("\nDeudas:\n");

        for (int i = 0; i < numPersonas; i++) {
            if (balance[i] < 0) {
                for (int j = 0; j < numPersonas; j++) {
                    if (balance[j] > 0) {
                        double cantidad = Math.min(-balance[i], balance[j]);
                        resultado.append(String.format(
                            "%s: %.2f € -> %s%n",
                            personas.get(i).getNombre(),
                            cantidad,
                            personas.get(j).getNombre()));
                        balance[i] += cantidad;
                        balance[j] -= cantidad;
                        if (balance[i] == 0)
                            break;
                    }
                }
            }
        }

        return resultado.toString();
    }

    /**
     * Calcula el total de dinero gastado por todas las personas.
     *
     * @param personas lista de personas
     * @return total de dinero aportado entre todas
     */
    public static double getDineroGastadoTotal(List<Persona> personas) {
        double total = 0;
        for (Persona p : personas) {
            for (Gasto g : p.gastos) {
                total += g.getImporte();
            }
        }
        return total;
    }

    /**
     * Compara esta persona con otra según el dinero aportado, en orden descendente.
     *
     * @param o otra persona a comparar
     * @return un valor negativo si esta persona ha aportado más, positivo si ha aportado menos, 0 si es igual
     */
    @Override
    public int compareTo(Persona o) {
        return (int) (-this.getDineroAportado() - o.getDineroAportado());
    }

    /**
     * Devuelve el hash code de la persona basado en su nombre.
     *
     * @return el hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    /**
     * Compara si dos personas son iguales basándose en su nombre.
     *
     * @param obj el objeto a comparar
     * @return true si los nombres son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        if (nombre == null) {
            return other.nombre == null;
        } else return nombre.equals(other.nombre);
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return el nombre como cadena
     */
    @Override
    public String toString() {
        return this.nombre;
    }
}
