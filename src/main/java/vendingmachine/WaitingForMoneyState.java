package vendingmachine;

public class WaitingForMoneyState implements StateOfVendingMachine {
    private final VendingMachine vendingMachine;

    public WaitingForMoneyState(VendingMachine vendingMachine) {
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
        vendingMachine.resetInsertedMoney();
        System.out.println("Changed selection to " + snack.getName()
                + " ($" + String.format("%.2f", snack.getPrice()) + "). Inserted money reset.");
    }

    @Override
    public void insertMoney(double amount) {
        if (amount <= 0) {
            System.out.println("Insert a positive amount of money.");
            return;
        }

        vendingMachine.addInsertedMoney(amount);
        Snack selected = vendingMachine.getSelectedSnack();
        System.out.println("Inserted $" + String.format("%.2f", amount)
                + ". Total inserted: $" + String.format("%.2f", vendingMachine.getInsertedMoney()));

        if (vendingMachine.getInsertedMoney() < selected.getPrice()) {
            double remaining = selected.getPrice() - vendingMachine.getInsertedMoney();
            System.out.println("Need $" + String.format("%.2f", remaining) + " more for " + selected.getName());
            return;
        }

        if (!selected.isAvailable()) {
            System.out.println(selected.getName() + " is out of stock. Returning $"
                    + String.format("%.2f", vendingMachine.getInsertedMoney()));
            vendingMachine.resetTransaction();
            vendingMachine.setState(vendingMachine.getIdleState());
            System.out.println("State -> Idle");
            return;
        }

        vendingMachine.setState(vendingMachine.getDispensingSnackState());
        System.out.println("State -> Dispensing Snack");
        vendingMachine.dispenseSnack();
    }

    @Override
    public void dispenseSnack() {
        System.out.println("Insert enough money before dispensing.");
    }

    @Override
    public String toString() {
        return "Waiting for Money";
    }
}
