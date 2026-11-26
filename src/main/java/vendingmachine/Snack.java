package vendingmachine;

public class Snack {
    private final String name;
    private final double price;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void decrementQuantity() {
        if (quantity <= 0) {
            throw new IllegalStateException(name + " is out of stock");
        }
        quantity--;
    }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ", qty=" + quantity + ")";
    }
}
