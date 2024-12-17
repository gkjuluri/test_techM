import products.BaconRoll;
import products.BaseProduct;
import products.Coffee;
import products.OrangeJuice;

import java.util.List;


public class Order {
    private List<BaseProduct> products;
    private boolean hasSnack = false;
    private boolean hasBeverage = false;
    private double totalBeforeDiscount = 0.0;
    private double disCount = 0.0;

    public Order(List<BaseProduct> products) {
        this.products = products;
        checkForSnackAndBeverage();
    }

    private void checkForSnackAndBeverage() {
        for (BaseProduct product : products) {
            if (product instanceof BaconRoll) {
                hasSnack = true;
            }else if(product instanceof Coffee || product instanceof OrangeJuice){
                hasBeverage = true;
            }
        }
    }

    public double calculateTotal() {
        totalBeforeDiscount = 0.0;
        disCount = 0.0;
        int beverageCount = 0;
        boolean extraApplied = false;
        for (BaseProduct product : products) {
            totalBeforeDiscount += product.getPrice();

            // Apply the "every 5th beverage free" rule
            if (product instanceof Coffee) {
                beverageCount++;
                if (beverageCount % 5 == 0) {
                    disCount += product.getPrice();  // Subtract the price of the 5th beverage
                }else if(!extraApplied && hasSnack && hasBeverage && !((Coffee) product).getExtras().isEmpty()){
                    extraApplied = true;
                    disCount += Coffee.getPriceForExtra(((Coffee) product).getExtras().getFirst());
                }
            }
        }
        return totalBeforeDiscount - disCount;
    }


    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        for (BaseProduct product : products) {
            receipt.append(product.getName())
                    .append(": ")
                    .append(String.format("%.2f", product.getPrice()))
                    .append(" CHF\n");
        }
        double finalTotal = calculateTotal();
        receipt.append("\nSub Total: ")
                .append(String.format("%.2f", totalBeforeDiscount))
                .append(" CHF");

        receipt.append("\nDiscount: ")
                .append(String.format("%.2f", disCount))
                .append(" CHF");

        receipt.append("\nTotal   : ")
                .append(String.format("%.2f", finalTotal))
                .append(" CHF");

        System.out.println(receipt.toString());
        return receipt.toString();
    }
}
