package vendingmachine;

public class PepsiDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "Pepsi".equalsIgnoreCase(snackName);
    }
}
