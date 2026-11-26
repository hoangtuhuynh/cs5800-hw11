package vendingmachine;

public class KitKatDispenseHandler extends SnackDispenseHandler {
    @Override
    protected boolean canHandle(String snackName) {
        return "KitKat".equalsIgnoreCase(snackName);
    }
}
