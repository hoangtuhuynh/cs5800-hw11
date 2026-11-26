package vendingmachine;

public class SnickersDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "Snickers".equalsIgnoreCase(snackName);
    }
}
