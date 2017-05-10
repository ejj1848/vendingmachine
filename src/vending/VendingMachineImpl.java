package vending;

import vending.exceptions.*;

import java.util.ArrayList;
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

    private void initialize() {
        for(Coin coin : Coin.values()) {
            cashInventory.put(coin, 5);
        }

        for(Item item : Item.values()){
            itemInventory.put(item, 5);
        }
    }


    @Override
    public long selectItemAndGetPrice(Item item) {
        if(itemInventory.hasItem(item)) {
            currentItem=item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Item Not in Stock");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getDenomination();
        cashInventory.add(coin);
    }


    //not sure what this is for...
    @Override
    public Bucket<Item, List<Coin>> collectItemAndChange() {

        Item item = collectItem();
        totalSales = totalSales + currentItem.getPrice();

        List<Coin> change = collectChange();

        return new Bucket<Item, List<Coin>>(item, change);
    }

    public Item collectItem() throws NotSufficientChangeException, NotPaidInFullException {

        if(isFullPaid()) {
            if(hasSufficientChange()) {
                itemInventory.deduct(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("The Vending Machine Does Not Have Sufficient Change for your Purchase." + "\n" +
                                                    "Please Pay Using Different Denomination(s) of Coins");
        }

        long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotPaidInFullException("Price Not Paid in Full. Remaining Balance: " + Long.toString(remainingBalance));
    }

    public List<Coin> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashInventory(change);
        if(currentBalance - changeAmount != 0) {
            throw new SomethingWentTerriblyWrongException("Something Went Terribly Wrong");
        } else {
            currentBalance = 0;
        }
        currentItem = null;

        return change;
    }

    @Override
    public List<Coin> refund() {

        //Same logic in CollectChange - Should be put in a different method...
        List<Coin> change = getChange(currentBalance);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;

        return change;
    }


    public boolean isFullPaid() {
        if(!(currentBalance < currentItem.getPrice())) {
            return true;
        } else {
            return false;
        }
    }

    public List getChange(long amount) throws NotSufficientChangeException {
        List<Coin> change = new ArrayList<Coin>();

        if(amount > 0){
            while(amount>0) {
                getChangeHelper(amount, Coin.QUARTER, change);
                getChangeHelper(amount, Coin.DIME, change);
                getChangeHelper(amount, Coin.NICKLE, change);
                getChangeHelper(amount, Coin.PENNY, change);
                if (amount > 0) {
                    throw new NotSufficientChangeException("Sorry. There is not sufficient change. Please Contact Obama.");
                }

            }
        }

        return change;
    }

    public void getChangeHelper(long amount, Coin coin, List<Coin> change){
        if(amount >= coin.getDenomination() && cashInventory.hasItem(coin)){
            change.add(coin);
            amount = amount - coin.getDenomination();
        }
    }

    @Override
    public void reset() {
        cashInventory = null;
        itemInventory = null;
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    @Override
    public void printStats() {
        System.out.println("Total Sales : " + Long.toString(totalSales));
        System.out.println("Current Item Inventory : " + itemInventory);
        System.out.println("Current Cash Inventory : " + cashInventory);
    }



    public boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
    }


    public boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try{
            getChange(amount);
        }catch(NotSufficientChangeException ex){
            hasChange = false;
        }
        return hasChange;
    }

    public void updateCashInventory(List<Coin> coins) {

    }

    @Override
    public long getTotalSales() {
        return totalSales;
    }

}
