package vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eric.Johnson on 5/9/2017.
 */
public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> itemInventory = new Inventory<Item>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl(){
        initialize();
    }

    // all of your code will go here
}
