package vendingmachine;

public class CheetosDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "Cheetos".equalsIgnoreCase(snackName);
    }
}
