package products;

import java.util.List;

public class Coffee extends BaseProduct {
    private List<Extra> extras;

    public enum Size {
        small, medium, large
    }

    public enum Extra {
        Milk, FoamedMilk, SpecialRoast
    }

    public Coffee(Size size, List<Extra> extras) {
        super("Coffee (" + size + ")", getPriceForSize(size));
        this.extras = extras;
    }


    public List<Extra> getExtras() {
        return extras;
    }

    private static double getPriceForSize(Size size) {
        return switch (size) {
            case small -> 2.50;
            case medium -> 3.00;
            case large -> 3.50;
        };
    }

    public static double getPriceForExtra(Extra extra) {
        return switch (extra) {
            case Milk -> 0.30;
            case FoamedMilk -> 0.50;
            case SpecialRoast -> 0.90;
        };
    }

    @Override
    public double getPrice() {
        double price = super.getPrice();
        for (Extra extra : extras) {
            price += getPriceForExtra(extra);
        }
        return price;
    }

    @Override
    public String getName() {
        if (extras == null || extras.isEmpty()) {
            return super.getName();
        }
        StringBuilder sb = new StringBuilder(super.getName());
        sb.append(" with extra ");
        sb.append(extras);
        return sb.toString();
    }
}

