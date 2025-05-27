package tricount.modelo.tests;

import org.junit.jupiter.api.Test;

import tricount.modelo.Gasto;

import static org.junit.jupiter.api.Assertions.*;

class GastoTest {

    @Test
    void testConstructorValido() {
        // Prueba un caso válido
        Gasto gasto = new Gasto("Comida", 50.0);
        assertEquals("Comida", gasto.getAsunto());
        assertEquals(50.0, gasto.getImporte());
    }

    @Test
    void testConstructorImporteNegativo() {
        // Prueba un caso inválido con importe negativo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gasto("Transporte", -10.0);
        });
        assertEquals("ERROR: El importe no puede ser 0 o negativo.", exception.getMessage());
    }

    @Test
    void testConstructorImporteCero() {
        // Prueba un caso inválido con importe igual a 0
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gasto("Cine", 0.0);
        });
        assertEquals("ERROR: El importe no puede ser 0 o negativo.", exception.getMessage());
    }

    @Test
    void testEquals() {
        // Prueba el método equals
        Gasto gasto1 = new Gasto("Comida", 20.0);
        Gasto gasto2 = new Gasto("Comida", 20.0);
        Gasto gasto3 = new Gasto("Transporte", 15.0);

        assertEquals(gasto1, gasto2); // Deben ser iguales
        assertNotEquals(gasto1, gasto3); // No deben ser iguales
    }

    @Test
    void testHashCode() {
        // Prueba el método hashCode
        Gasto gasto1 = new Gasto("Comida", 20.0);
        Gasto gasto2 = new Gasto("Comida", 20.0);
        Gasto gasto3 = new Gasto("Transporte", 15.0);

        assertEquals(gasto1.hashCode(), gasto2.hashCode()); // Deben tener el mismo hashCode
        assertNotEquals(gasto1.hashCode(), gasto3.hashCode()); // No deben tener el mismo hashCode
    }
}
