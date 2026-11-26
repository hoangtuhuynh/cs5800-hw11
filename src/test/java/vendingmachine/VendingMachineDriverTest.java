package vendingmachine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineDriverTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void driverDemonstratesStateAndChainOfResponsibility() {
        VendingMachineDriver.main(new String[0]);
        String printed = output.toString();

        assertTrue(printed.contains("Vending Machine ready"));
        assertTrue(printed.contains("Purchase Coke with exact change"));
        assertTrue(printed.contains("Purchase last Snickers (quantity becomes 0)"));
        assertTrue(printed.contains("Attempt another Snickers while quantity is 0"));
        assertTrue(printed.contains("Snickers is out of stock"));
        assertTrue(printed.contains("CokeDispenseHandler handling request for Coke"));
        assertTrue(printed.contains("SnickersDispenseHandler handling request for Snickers"));
        assertTrue(printed.contains("Snickers ($1.00, qty=0)"));
        assertTrue(printed.contains("Final state: Idle"));
    }
}
