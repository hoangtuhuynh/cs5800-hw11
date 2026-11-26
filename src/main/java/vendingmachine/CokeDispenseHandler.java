package vendingmachine;

public class CokeDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "Coke".equalsIgnoreCase(snackName);
    }
}
