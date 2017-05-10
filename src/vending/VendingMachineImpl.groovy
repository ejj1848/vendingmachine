package vending

/**
 * Created by Brandon.O'Donnell on 5/9/2017.
 */
public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory = new Inventory<Coin>()
    private Inventory<Item> itemInventory = new Inventory<Item>()
    private long totalSales
    private Item currentItem
    private long currentBalance

    public VendingMachineImpl(){
        initialize()
    }

    // all of your code will go here

    public void initialize() {
        Coin.values().each{ coin ->
            cashInventory.put(coin,5)
        }

        Item.values().each { item ->
            itemInventory.put(item, 5)
        }

        totalSales = 0
        currentItem = null
        currentBalance = 0

    }


    @Override
    public long selectItemAndGetPrice(Item item) {
        currentItem = item
        item.getPrice()
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance += coin.getDenomination()
        cashInventory.add(coin)
    }

    @Override
    public List<Coin> refund() {
        List<Coin> coinList = new ArrayList<>()
        List<Coin> eachCoinType = (Coin.values().toList().sort{it.getDenomination()}).reverse()



        eachCoinType.each{ coin ->
            while(currentBalance >= coin.getDenomination() && cashInventory.getQuantity(coin) > 0) {
                currentBalance -= coin.getDenomination()
                cashInventory.deduct(coin)
                coinList.add(coin)
            }
        }

        coinList
    }

    @Override
    public Bucket<Item, List<Coin>> collectItemAndChange() {
        List<Coin> coinList = new ArrayList<>();


        if(itemInventory.getQuantity(currentItem) > 0) {
            totalSales += currentItem.getPrice()
            currentBalance -= currentItem.getPrice()
            new Bucket(currentItem, refund())
        }
        else {
            new Bucket(null, refund())
        }
    }

    @Override
    public void reset() {
        initialize()
    }

    Inventory<Coin> getCashInventory() {
        return cashInventory
    }

    void setCashInventory(Inventory<Coin> cashInventory) {
        this.cashInventory = cashInventory
    }

    Inventory<Item> getItemInventory() {
        return itemInventory
    }

    void setItemInventory(Inventory<Item> itemInventory) {
        this.itemInventory = itemInventory
    }

    long getTotalSales() {
        return totalSales
    }

    void setTotalSales(long totalSales) {
        this.totalSales = totalSales
    }

    Item getCurrentItem() {
        return currentItem
    }

    void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem
    }

    long getCurrentBalance() {
        return currentBalance
    }

    void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance
    }
}
