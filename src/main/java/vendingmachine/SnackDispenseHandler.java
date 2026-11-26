package vendingmachine;

public abstract class SnackDispenseHandler {
    private SnackDispenseHandler nextHandler;

    public SnackDispenseHandler setNext(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public SnackDispenseHandler getNextHandler() {
        return nextHandler;
    }

    public final boolean handle(VendingMachine machine, String snackName) {
        if (canHandle(snackName)) {
            System.out.println(getClass().getSimpleName() + " handling request for " + snackName);
            doDispense(machine, snackName);
            return true;
        }

        System.out.println(getClass().getSimpleName() + " passing " + snackName + " to next handler");
        if (nextHandler != null) {
            return nextHandler.handle(machine, snackName);
        }

        System.out.println("No handler found for snack: " + snackName);
        return false;
    }

    protected abstract boolean canHandle(String snackName);

    protected void doDispense(VendingMachine machine, String snackName) {
        Snack snack = machine.getSnack(snackName);
        snack.decrementQuantity();
        System.out.println("Dispensed " + snack.getName() + ". Remaining quantity: " + snack.getQuantity());
    }
}
