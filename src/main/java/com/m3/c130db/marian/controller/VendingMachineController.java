package com.m3.c130db.marian.controller;

import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;
import com.m3.c130db.marian.service.VendingMachineQuantityValidationException;
import com.m3.c130db.marian.service.VendingMachineServiceLayer;
import com.m3.c130db.marian.ui.VendingMachineView;

import java.util.List;


public class VendingMachineController {
    private final VendingMachineView view;
    private final VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws VendingMachinePersistenceException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    listItems();
                    break;
                case 2:
                    try {
                        purchaseItem();
                    } catch (VendingMachineQuantityValidationException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }


    private void listItems() {
        List<Item> itemList = null;
        try {
            itemList = service.listItems();
        } catch (VendingMachinePersistenceException e) {
            e.printStackTrace();
        }

        view.displayItemList(itemList);
    }

    private void purchaseItem() throws VendingMachinePersistenceException, VendingMachineQuantityValidationException {
        String itemId = view.getItemIdChoice();
        Item item = null;
        while (item == null) {
            try {
                item = service.getItem(itemId);
            } catch (Exception e) {
            }

            if (item == null) {
                view.displayIncorrectItemId();
                itemId = view.getItemIdChoice();
            }
        }
        boolean transactionComplete = service.transaction(item);
        service.updateQuantity(item, transactionComplete);
        service.editItems(itemId, item);
        String entry = service.auditEntry(item);
        service.writeAuditEntry(entry);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}

