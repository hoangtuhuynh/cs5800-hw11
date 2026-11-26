package vendingmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineTest {
    private VendingMachine machine;

    @BeforeEach
    void setUp() {
        machine = new VendingMachine(VendingMachineDriver.buildSnackChain());
        machine.addSnack(new Snack("Coke", 1.50, 2));
        machine.addSnack(new Snack("Pepsi", 1.50, 1));
        machine.addSnack(new Snack("Snickers", 1.00, 1));
    }

    @Test
    void startsInIdleState() {
        assertInstanceOf(IdleState.class, machine.getCurrentState());
    }

    @Test
    void selectSnackMovesToWaitingForMoney() {
        machine.selectSnack("Coke");
        assertInstanceOf(WaitingForMoneyState.class, machine.getCurrentState());
        assertEquals("Coke", machine.getSelectedSnack().getName());
    }

    @Test
    void successfulPurchaseDispensesAndReturnsToIdle() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);
        assertInstanceOf(IdleState.class, machine.getCurrentState());
        assertEquals(1, machine.getSnack("Coke").getQuantity());
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney());
    }

    @Test
    void insufficientMoneyStaysWaitingForMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);
        assertInstanceOf(WaitingForMoneyState.class, machine.getCurrentState());
        assertEquals(0.50, machine.getInsertedMoney());
        assertEquals(2, machine.getSnack("Coke").getQuantity());
    }

    @Test
    void outOfStockReturnsMoneyAndGoesIdle() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.00);
        assertEquals(0, machine.getSnack("Snickers").getQuantity());

        machine.selectSnack("Snickers");
        machine.insertMoney(1.00);
        assertInstanceOf(IdleState.class, machine.getCurrentState());
        assertEquals(0, machine.getSnack("Snickers").getQuantity());
        assertNull(machine.getSelectedSnack());
    }

    @Test
    void rejectsNullDispenser() {
        assertThrows(IllegalArgumentException.class, () -> new VendingMachine(null));
    }
}
