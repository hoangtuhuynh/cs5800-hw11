package vendingmachine;

public class VendingMachineDriver {
    public static void main(String[] args) {
        SnackDispenseHandler chain = buildSnackChain();
        VendingMachine machine = new VendingMachine(chain);

        machine.addSnack(new Snack("Coke", 1.50, 3));
        machine.addSnack(new Snack("Pepsi", 1.50, 3));
        machine.addSnack(new Snack("Cheetos", 1.25, 2));
        machine.addSnack(new Snack("Doritos", 1.25, 2));
        machine.addSnack(new Snack("KitKat", 1.00, 2));
        machine.addSnack(new Snack("Snickers", 1.00, 1));

        System.out.println("=== Vending Machine ready (State: " + machine.getCurrentState() + ") ===");
        machine.printInventory();

        System.out.println("\n=== Purchase Coke with exact change ===");
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        System.out.println("\n=== Purchase Pepsi with extra money (get change) ===");
        machine.selectSnack("Pepsi");
        machine.insertMoney(2.00);

        System.out.println("\n=== Purchase Cheetos with partial then remaining money ===");
        machine.selectSnack("Cheetos");
        machine.insertMoney(0.50);
        machine.insertMoney(0.75);

        System.out.println("\n=== Purchase Doritos ===");
        machine.selectSnack("Doritos");
        machine.insertMoney(1.25);

        System.out.println("\n=== Purchase KitKat ===");
        machine.selectSnack("KitKat");
        machine.insertMoney(1.00);

        System.out.println("\n=== Purchase last Snickers (quantity becomes 0) ===");
        machine.selectSnack("Snickers");
        machine.insertMoney(1.00);

        System.out.println("\n=== Attempt another Snickers while quantity is 0 ===");
        machine.selectSnack("Snickers");
        machine.insertMoney(1.00);

        System.out.println("\n=== Attempt insert money while idle ===");
        machine.insertMoney(1.00);

        System.out.println();
        machine.printInventory();
        System.out.println("Final state: " + machine.getCurrentState());
    }

    static SnackDispenseHandler buildSnackChain() {
        SnackDispenseHandler coke = new CokeDispenseHandler();
        SnackDispenseHandler pepsi = new PepsiDispenseHandler();
        SnackDispenseHandler cheetos = new CheetosDispenseHandler();
        SnackDispenseHandler doritos = new DoritosDispenseHandler();
        SnackDispenseHandler kitKat = new KitKatDispenseHandler();
        SnackDispenseHandler snickers = new SnickersDispenseHandler();

        coke.setNext(pepsi);
        pepsi.setNext(cheetos);
        cheetos.setNext(doritos);
        doritos.setNext(kitKat);
        kitKat.setNext(snickers);

        return coke;
    }
}
