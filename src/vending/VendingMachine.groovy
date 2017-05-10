package vending

/**
 * Created by Brandon.O'Donnell on 5/9/2017.
 */

public interface VendingMachine {
    long selectItemAndGetPrice(Item item)
    void insertCoin(Coin coin)
    List<Coin> refund()
    Bucket<Item, List<Coin>> collectItemAndChange()
    void reset()
}

