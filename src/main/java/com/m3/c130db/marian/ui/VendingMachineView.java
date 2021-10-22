package com.m3.c130db.marian.ui;

import com.m3.c130db.marian.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {

    private final UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("\n\nMain Menu");
        io.print("1. List Items");
        io.print("2. Purchase an Item");
        io.print("3. Exit");
        return io.readInt("Please select from the above choices.", 1, 3);
    }

//    public Item getNewItemInfo() {
//        String itemId = io.readString("Please enter the item id");
//        String itemName = io.readString("Please enter the item's name");
//        String itemCost = io.readString("Please enter the item's cost");
//        int itemQuantity = io.readInt("Please enter the item's quantity");
//
//        Item currentItem = new Item(itemId);
//        currentItem.setName(itemName);
//        currentItem.setCost(new BigDecimal(itemCost));
//        currentItem.setRemainingQuantity(itemQuantity);
//
//        return currentItem;
//    }

    public void displayItemList(List<Item> itemList) {
        io.print("=== Display All Items ===");
        for (Item currentItem : itemList) {
            if (currentItem.getRemainingQuantity() > 0) {
                String itemInfo = String.format("%s : %s : £%s ",
                        currentItem.getItemId(),
                        currentItem.getName(),
                        currentItem.getCost());
                io.print(itemInfo);
            }
        }
        io.readString("Please hit enter to continue.");
    }

    public String getItemIdChoice() {
        return io.readString("\nPlease enter item Id");
    }

    public String getCash() {
        return io.readString("Please insert your cash");
    }


    public void displayItemInfo(Item item) {
        if (item == null) {
            io.print("No such item.");
            io.print("");
        } else {
            io.print("\nitem id: " + item.getItemId() + " | item name: " + item.getName() + " | item cost: " + item.getCost());
        }
    }

    public void displayTransactionInfo(BigDecimal currentBalance, BigDecimal outstandingBalance) {
        io.print("Current balance: " + currentBalance.toString() + " | Outstanding balance: " + outstandingBalance.toString());
    }

    public void transactionComplete(Item item) {
        io.print("\n------ PURCHASE COMPLETE ------");
        io.print("Here's your snack: " + item.getName());
    }

    public void giveChange(BigDecimal currentBalance) {
        io.print("Please collect your change: £" + currentBalance.toString());
    }


    public void displayIncorrectItemId() {
        io.print("\nNo such item id, try again.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayChangeBreakdown(String changeString) {
        io.print(changeString);
    }

//    public void displayChangeBreakdown (BigDecimal nTenPounds,
//                                        BigDecimal nFivePounds,
//                                        BigDecimal nTwoPounds,
//                                        BigDecimal nOnePounds,
//                                        BigDecimal nFiftyPence,
//                                        BigDecimal nTwentyPence,
//                                        BigDecimal nTenPence,
//                                        BigDecimal nFivePence,
//                                        BigDecimal nTwoPence,
//                                        BigDecimal nOnePence) {
//        io.print("Your change breakdown is: " );
//        io.print("Ten Pounds = " + nTenPounds);
//        io.print("Five Pounds = " + nFivePounds);
//        io.print("Two Pounds = " + nTwoPounds);
//        io.print("One Pounds = " + nOnePounds);
//        io.print("Fifty Pence = " + nFiftyPence);
//        io.print("Twenty Pence = " + nTwentyPence);
//        io.print("Ten Pence = " + nTenPence);
//        io.print("Five Pence = " + nFivePence);
//        io.print("Two Pence = " + nTwoPence);
//        io.print("One Pence = " + nOnePence);
//}
}


