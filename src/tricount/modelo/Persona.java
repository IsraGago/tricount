package tricount.modelo;

import java.util.ArrayList;
import java.util.List;

public class Persona implements Comparable<Persona> {
    private String nombre;
    public List<Gasto> gastos = new ArrayList<>();

    // CONSTRUCTOR
    public Persona(String nombre, double dineroAportado) {
        if (dineroAportado < 0) {
            throw new IllegalArgumentException("ERROR: una persona no puede aportar dinero negativo");
        }
        this.nombre = nombre;
    }

    // MÉTODOS
    public void añadirGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public void mostrar() {
        System.out.println("Nombre: " + this.nombre);
        System.out.printf("Dinero aportado: %.2f%n", this.getDineroAportado());
    }

    public void mostarGastos() {
        // Arrays.sort(gastos);
        gastos.sort((g1, g2) -> (int) (g2.getImporte() - g1.getImporte()));
        if (gastos.size() > 0) {
            System.out.println(nombre);
            for (Gasto gasto : gastos) {
                System.out.println(gasto);
                System.out.println();
            }
        }
    }

    public Persona(String nombre) {
        this(nombre, 0);
    }

    // GETTERS
    public double getDineroAportado() {

        double dineroAportado = 0;
        for (Gasto g : gastos) {
            dineroAportado += g.getImporte();
        }
        return dineroAportado;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

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
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public int compareTo(Persona o) {
        return (int) (-this.getDineroAportado() - o.getDineroAportado());
    }

    public boolean eliminarGasto(Gasto g) {
        try {
            gastos.remove(g); // NO SE SI HACE FALTA EL TRY CATCH
        } catch (Exception e) {

        }
        return false;
    }

    public static String finalizar(List<Persona> personas) {
        double gastoTotal = 0;
        int numPersonas = personas.size();

        for (Persona persona : personas) {
            gastoTotal += persona.getDineroAportado();
        }

        double gastoPorPersona = gastoTotal / numPersonas;

        double[] balance = new double[numPersonas];

        // Calcula el balance de cada persona (positivo si aportó más, negativo si
        // debe).
        for (int i = 0; i < numPersonas; i++) {
            balance[i] = personas.get(i).getDineroAportado() - gastoPorPersona;
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("\nRESULTADO FINAL:\n");
        resultado.append(String.format("Gasto total: %.2f€%n", gastoTotal));
        resultado.append(String.format("Gasto por persona: %.2f€%n", gastoPorPersona));
        resultado.append("\nDeudas:\n");

        // Itera sobre los balances para ajustarlos.
        for (int i = 0; i < numPersonas; i++) {
            if (balance[i] < 0) { // Esta persona debe dinero.
                for (int j = 0; j < numPersonas; j++) {
                    if (balance[j] > 0) { // Esta persona debe recibir dinero.
                        double cantidad = Math.min(-balance[i], balance[j]);
                        resultado.append(String.format(
                                "%s: %.2f € -> %s%n",
                                personas.get(i).getNombre(),
                                cantidad,
                                personas.get(j).getNombre()));
                        balance[i] += cantidad;
                        balance[j] -= cantidad;

                        // Si balance[i] ya no debe nada, pasamos al siguiente.
                        if (balance[i] == 0)
                            break;
                    }
                }
            }
        }

        resultado.append("\n¡Actividad finalizada!\n");
        return resultado.toString();
    }

    public static double getDineroGastadoTotal(List<Persona> personas){
        double total = 0;
        for(Persona p : personas){
            for(Gasto g : p.gastos){
                total += g.getImporte();
            }
        }
        return total;
    }

}
