package vendingmachine;

public class IdleState implements StateOfVendingMachine {
    private final VendingMachine vendingMachine;

    public IdleState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void selectSnack(String snackName) {
        Snack snack = vendingMachine.getSnack(snackName);
        if (snack == null) {
            System.out.println("Snack not found: " + snackName);
            return;
        }
        vendingMachine.setSelectedSnack(snack);
        System.out.println("Selected " + snack.getName() + " ($" + String.format("%.2f", snack.getPrice()) + ")");
        vendingMachine.setState(vendingMachine.getWaitingForMoneyState());
        System.out.println("State -> Waiting for Money");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Select a snack before inserting money.");
    }

    @Override
    public void dispenseSnack() {
        System.out.println("Cannot dispense while idle. Select a snack first.");
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
