import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import products.BaconRoll;
import products.Coffee;
import products.OrangeJuice;

import java.util.Arrays;
import java.util.List;

public class OrderTest {

    @Test
    public void testOrderWithNoFreeBeverages() {
        Coffee coffee = new Coffee(Coffee.Size.large, List.of());
        BaconRoll baconRoll = new BaconRoll();
        OrangeJuice juice = new OrangeJuice();

        Order order = new Order(Arrays.asList(coffee, baconRoll, juice));
        assertEquals(coffee.getPrice() + baconRoll.getPrice() + juice.getPrice(), order.calculateTotal(), 0.01);
    }

    @Test
    public void testOrderWithFreeBeverage() {
        Coffee coffee1 = new Coffee(Coffee.Size.large, List.of());
        Coffee coffee2 = new Coffee(Coffee.Size.small, List.of(Coffee.Extra.Milk));
        Coffee coffee3 = new Coffee(Coffee.Size.large, List.of(Coffee.Extra.Milk, Coffee.Extra.FoamedMilk));
        Coffee coffee4 = new Coffee(Coffee.Size.medium, List.of());
        Coffee coffee5 = new Coffee(Coffee.Size.small, List.of(Coffee.Extra.SpecialRoast)); //this coffee is free

        OrangeJuice orangeJuice = new OrangeJuice();
        Order order = new Order(Arrays.asList(coffee1, coffee2, coffee3, coffee4, coffee5, orangeJuice));

        double expectedTotal = coffee1.getPrice() + coffee2.getPrice() + coffee3.getPrice() + coffee4.getPrice() + orangeJuice.getPrice();
        assertEquals(expectedTotal, order.calculateTotal(), 0.01);
    }

    @Test
    public void testOrderWithFreeExtra() {
        Coffee coffee1 = new Coffee(Coffee.Size.large, List.of());
        Coffee coffee2 = new Coffee(Coffee.Size.small, List.of(Coffee.Extra.Milk)); //extra is free

        BaconRoll baconRoll = new BaconRoll();
        Order order = new Order(Arrays.asList(coffee1, coffee2, baconRoll));

        double expectedTotal = coffee1.getPrice() + coffee2.getPrice() + baconRoll.getPrice() - Coffee.getPriceForExtra(Coffee.Extra.Milk);
        assertEquals(expectedTotal, order.calculateTotal(), 0.01);
    }


    @Test
    public void testReceiptFormatting() {
        Coffee coffee = new Coffee(Coffee.Size.large, List.of(Coffee.Extra.Milk));
        BaconRoll baconRoll = new BaconRoll();
        Order order = new Order(Arrays.asList(coffee, baconRoll));

        String expectedReceipt = "Coffee (large) with extra [Milk]: 3.80 CHF\nBacon Roll: 4.50 CHF\n\nSub Total: 8.30 CHF\nDiscount: 0.30 CHF\nTotal   : 8.00 CHF";
        assertEquals(expectedReceipt, order.generateReceipt());
    }
}
