package vendingmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateOfVendingMachineTest {
    private VendingMachine machine;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(output));
        machine = new VendingMachine(VendingMachineDriver.buildSnackChain());
        machine.addSnack(new Snack("Coke", 1.50, 1));
        machine.addSnack(new Snack("Pepsi", 1.50, 1));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void idleRejectsInsertMoneyAndDispense() {
        machine.insertMoney(1.00);
        machine.dispenseSnack();
        String printed = output.toString();
        assertTrueContains(printed, "Select a snack before inserting money");
        assertTrueContains(printed, "Cannot dispense while idle");
        assertInstanceOf(IdleState.class, machine.getCurrentState());
    }

    @Test
    void waitingForMoneyAllowsChangingSelection() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.25);
        machine.selectSnack("Pepsi");
        assertEquals("Pepsi", machine.getSelectedSnack().getName());
        assertEquals(0.0, machine.getInsertedMoney());
        assertInstanceOf(WaitingForMoneyState.class, machine.getCurrentState());
    }

    @Test
    void dispensingReturnsChangeThenIdle() {
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);
        assertInstanceOf(IdleState.class, machine.getCurrentState());
        assertNull(machine.getSelectedSnack());
        assertTrueContains(output.toString(), "Returning change: $0.50");
    }

    private static void assertTrueContains(String haystack, String needle) {
        if (!haystack.contains(needle)) {
            throw new AssertionError("Expected output to contain: " + needle + "\nWas:\n" + haystack);
        }
    }
}
