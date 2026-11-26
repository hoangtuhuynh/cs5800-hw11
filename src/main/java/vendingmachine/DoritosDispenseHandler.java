package vendingmachine;

public class DoritosDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "Doritos".equalsIgnoreCase(snackName);
    }
}
