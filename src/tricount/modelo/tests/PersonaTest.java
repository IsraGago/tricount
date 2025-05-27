package tricount.modelo.tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tricount.modelo.Gasto;
import tricount.modelo.Persona;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    private Persona persona;
    private Gasto gasto1;
    private Gasto gasto2;

    @BeforeEach
    void setUp() {
        // Inicializa los datos antes de cada prueba
        persona = new Persona("Alice");
        gasto1 = new Gasto("Comida", 20.0);
        gasto2 = new Gasto("Transporte", 15.0);

        // Agrega algunos gastos a la persona
        persona.gastos = new ArrayList<>();
        persona.gastos.add(gasto1);
        persona.gastos.add(gasto2);
    }

    @Test
    void testGetDineroAportado() {
        // Verifica que el total del dinero aportado sea correcto
        double total = persona.getDineroAportado();
        assertEquals(35.0, total, 0.01);
    }

    @Test
    void testGetNombre() {
        // Verifica que el nombre de la persona sea correcto
        assertEquals("Alice", persona.getNombre());
    }

    @Test
    void testEliminarGastoExistente() {
        // Verifica que se pueda eliminar un gasto existente
        boolean eliminado = persona.eliminarGasto(gasto1);
        assertTrue(eliminado);
        assertEquals(1, persona.gastos.size());
        assertFalse(persona.gastos.contains(gasto1));
    }

    @Test
    void testEliminarGastoInexistente() {
        // Verifica que no se pueda eliminar un gasto que no existe
        Gasto gastoInexistente = new Gasto("Cine", 10.0);
        boolean eliminado = persona.eliminarGasto(gastoInexistente);
        assertFalse(eliminado);
        assertEquals(2, persona.gastos.size());
    }

    @Test
    void testEliminarGastoConExcepcion() {
        // Verifica que el método manejará correctamente una excepción
        persona.gastos = null; // Fuerza una excepción al intentar eliminar
        boolean eliminado = persona.eliminarGasto(gasto1);
        assertFalse(eliminado);
    }
}