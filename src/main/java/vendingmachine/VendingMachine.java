package vendingmachine;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class VendingMachine {
    private final Map<String, Snack> snacks = new LinkedHashMap<>();
    private final StateOfVendingMachine idleState;
    private final StateOfVendingMachine waitingForMoneyState;
    private final StateOfVendingMachine dispensingSnackState;
    private final SnackDispenseHandler snackDispenser;

    private StateOfVendingMachine currentState;
    private Snack selectedSnack;
    private double insertedMoney;

    public VendingMachine(SnackDispenseHandler snackDispenser) {
        if (snackDispenser == null) {
            throw new IllegalArgumentException("snackDispenser cannot be null");
        }
        this.snackDispenser = snackDispenser;
        this.idleState = new IdleState(this);
        this.waitingForMoneyState = new WaitingForMoneyState(this);
        this.dispensingSnackState = new DispensingSnackState(this);
        this.currentState = idleState;
    }

    public void addSnack(Snack snack) {
        if (snack == null) {
            throw new IllegalArgumentException("snack cannot be null");
        }
        snacks.put(snack.getName().toLowerCase(), snack);
    }

    public Snack getSnack(String name) {
        if (name == null) {
            return null;
        }
        return snacks.get(name.toLowerCase());
    }

    public Collection<Snack> getSnacks() {
        return snacks.values();
    }

    public void selectSnack(String snackName) {
        currentState.selectSnack(snackName);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount);
    }

    public void dispenseSnack() {
        currentState.dispenseSnack();
    }

    public StateOfVendingMachine getCurrentState() {
        return currentState;
    }

    public void setState(StateOfVendingMachine state) {
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }
        this.currentState = state;
    }

    public StateOfVendingMachine getIdleState() {
        return idleState;
    }

    public StateOfVendingMachine getWaitingForMoneyState() {
        return waitingForMoneyState;
    }

    public StateOfVendingMachine getDispensingSnackState() {
        return dispensingSnackState;
    }

    public SnackDispenseHandler getSnackDispenser() {
        return snackDispenser;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public void setSelectedSnack(Snack selectedSnack) {
        this.selectedSnack = selectedSnack;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void addInsertedMoney(double amount) {
        this.insertedMoney += amount;
    }

    public void resetInsertedMoney() {
        this.insertedMoney = 0;
    }

    public void resetTransaction() {
        this.selectedSnack = null;
        this.insertedMoney = 0;
    }

    public void printInventory() {
        System.out.println("--- Inventory ---");
        for (Snack snack : snacks.values()) {
            System.out.println(snack);
        }
    }
}
