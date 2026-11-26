package vendingmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SnackTest {
    @Test
    void createsSnackWithNamePriceAndQuantity() {
        Snack snack = new Snack("Coke", 1.50, 3);
        assertEquals("Coke", snack.getName());
        assertEquals(1.50, snack.getPrice());
        assertEquals(3, snack.getQuantity());
        assertTrue(snack.isAvailable());
    }

    @Test
    void decrementQuantityReducesStock() {
        Snack snack = new Snack("Snickers", 1.00, 1);
        snack.decrementQuantity();
        assertEquals(0, snack.getQuantity());
        assertFalse(snack.isAvailable());
    }

    @Test
    void decrementQuantityThrowsWhenOutOfStock() {
        Snack snack = new Snack("Snickers", 1.00, 0);
        assertThrows(IllegalStateException.class, snack::decrementQuantity);
    }

    @Test
    void rejectsInvalidConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new Snack("", 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Snack("Coke", -1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Snack("Coke", 1.0, -1));
    }
}
