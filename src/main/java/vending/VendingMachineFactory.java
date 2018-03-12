package vending;

/**
 * Created by Eric.Johnson on 5/9/2017.
 */
public class VendingMachineFactory{
    public static VendingMachine createVendingMachine() { return new VendingMachineImpl(); }
}

