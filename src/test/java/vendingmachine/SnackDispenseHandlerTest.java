package vendingmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnackDispenseHandlerTest {
    private VendingMachine machine;
    private SnackDispenseHandler chain;

    @BeforeEach
    void setUp() {
        chain = VendingMachineDriver.buildSnackChain();
        machine = new VendingMachine(chain);
        machine.addSnack(new Snack("Coke", 1.50, 2));
        machine.addSnack(new Snack("Pepsi", 1.50, 2));
        machine.addSnack(new Snack("Cheetos", 1.25, 2));
        machine.addSnack(new Snack("Doritos", 1.25, 2));
        machine.addSnack(new Snack("KitKat", 1.00, 2));
        machine.addSnack(new Snack("Snickers", 1.00, 2));
    }

    @Test
    void chainIsWiredInRequiredOrder() {
        assertTrue(chain instanceof CokeDispenseHandler);
        assertTrue(chain.getNextHandler() instanceof PepsiDispenseHandler);
        assertTrue(chain.getNextHandler().getNextHandler() instanceof CheetosDispenseHandler);
        assertTrue(chain.getNextHandler().getNextHandler().getNextHandler() instanceof DoritosDispenseHandler);
        assertTrue(chain.getNextHandler().getNextHandler().getNextHandler().getNextHandler()
                instanceof KitKatDispenseHandler);
        assertTrue(chain.getNextHandler().getNextHandler().getNextHandler().getNextHandler().getNextHandler()
                instanceof SnickersDispenseHandler);
    }

    @Test
    void cokeHandlerDispensesCoke() {
        assertTrue(chain.handle(machine, "Coke"));
        assertEquals(1, machine.getSnack("Coke").getQuantity());
    }

    @Test
    void snickersRequestWalksFullChain() {
        assertTrue(chain.handle(machine, "Snickers"));
        assertEquals(1, machine.getSnack("Snickers").getQuantity());
    }

    @Test
    void unknownSnackReturnsFalse() {
        assertFalse(chain.handle(machine, "Water"));
    }
}
