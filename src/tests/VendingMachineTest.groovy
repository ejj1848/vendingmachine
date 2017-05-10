package tests

import spock.lang.Specification
import vending.Bucket
import vending.Coin
import vending.Item
import vending.VendingMachineFactory
import vending.VendingMachineImpl

/**
 * Created by Brandon.O'Donnell on 5/9/2017.
 */
class VendingMachineTest extends Specification{

    VendingMachineFactory vendingMachineFactory

    VendingMachineImpl vendingMachine

    def setup(){
        vendingMachineFactory = new VendingMachineFactory()
        vendingMachine = vendingMachineFactory.createVendingMachine()
    }

    def 'test implementation'() {
        expect:
        Coin.values().each { coin ->
            vendingMachine.getCashInventory().getQuantity(coin) == 5
        }
        Item.values().each { item ->
            vendingMachine.getItemInventory().getQuantity(item) == 5
        }

        vendingMachine.getTotalSales() == 0
        vendingMachine.getCurrentItem() == null
        vendingMachine.getCurrentBalance() == 0

    }

    def 'test insert coin'() {

        given:
            vendingMachine.insertCoin(coin)


        expect:
        vendingMachine.getCashInventory().getQuantity(coin) == 6
        vendingMachine.getCurrentBalance() == coin.getDenomination()

        where:
        coin << Coin.values()[0]
    }

    def 'test refund'() {

        given:
        Coin.values().each { coin ->
            vendingMachine.insertCoin(coin)
        }

        expect:
        vendingMachine.refund() == Coin.values().toList().reverse()

    }

    def 'test select item and get price'() {
        expect:
            vendingMachine.selectItemAndGetPrice(item) == item.getPrice()
            vendingMachine.getCurrentItem() == item
        where:
            item << Item.values()[0]
    }

    def 'test reset'() {
        given:
            Coin.values().each { coin ->
                vendingMachine.getCashInventory().deduct(coin)
            }
            Item.values().each { item ->
                vendingMachine.getItemInventory().deduct(item)
            }
            vendingMachine.setCurrentBalance(1000)
            vendingMachine.setCurrentItem(Item.values()[0])
            vendingMachine.setTotalSales(10000)

            vendingMachine.reset()

        expect:
        Coin.values().each { coin ->
            vendingMachine.getCashInventory().getQuantity(coin) == 5
        }
        Item.values().each { item ->
            vendingMachine.getItemInventory().getQuantity(item) == 5
        }
        vendingMachine.getCurrentBalance() == 0
        vendingMachine.getCurrentItem() == null
        vendingMachine.getTotalSales() == 0
    }

//    def 'test collect Item And Change'() {
//        given:
//        vendingMachine.insertCoin(Coin.QUARTER)
//        vendingMachine.insertCoin(Coin.QUARTER)
//        vendingMachine.selectItemAndGetPrice(Item.PEPSI)
//
//
//        expect:
//        vendingMachine.collectItemAndChange() == bucket
//
//        where:
////        List<Coin> coinList = new ArrayList<>()
////        coinList.add(Coin.DIME)
////        coinList.add(Coin.DIME.NICKLE)
////        Bucket bucket = new Bucket(Item.PEPSI, coinList)
//
//        bucket << [new Bucket<>(Item.PEPSI, coinList)]; List<Coin> coinList = new ArrayList<>();coinList.add(Coin.DIME);coinList.add(Coin.NICKLE)
//    }


}
