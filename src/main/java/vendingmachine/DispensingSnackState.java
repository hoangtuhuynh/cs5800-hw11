package vendingmachine;

public class DispensingSnackState implements StateOfVendingMachine {
    private final VendingMachine vendingMachine;

    public DispensingSnackState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void selectSnack(String snackName) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void dispenseSnack() {
        Snack selected = vendingMachine.getSelectedSnack();
        if (selected == null) {
            System.out.println("No snack selected.");
            vendingMachine.resetTransaction();
            vendingMachine.setState(vendingMachine.getIdleState());
            return;
        }

        boolean dispensed = vendingMachine.getSnackDispenser().handle(vendingMachine, selected.getName());
        if (dispensed) {
            double change = vendingMachine.getInsertedMoney() - selected.getPrice();
            if (change > 0) {
                System.out.println("Returning change: $" + String.format("%.2f", change));
            }
        } else {
            System.out.println("Could not dispense " + selected.getName() + ". Returning $"
                    + String.format("%.2f", vendingMachine.getInsertedMoney()));
        }

        vendingMachine.resetTransaction();
        vendingMachine.setState(vendingMachine.getIdleState());
        System.out.println("State -> Idle");
    }

    @Override
    public String toString() {
        return "Dispensing Snack";
    }
}
