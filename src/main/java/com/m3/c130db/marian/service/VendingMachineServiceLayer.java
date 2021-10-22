package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;

import java.util.List;

public interface VendingMachineServiceLayer {

    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;

    boolean transaction(Item item) throws VendingMachinePersistenceException;

    List<Item> listItems() throws VendingMachinePersistenceException;

    Item addItem(String itemId, Item item) throws VendingMachinePersistenceException;

    Item getItem(String itemId) throws VendingMachinePersistenceException;


    Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException, VendingMachineQuantityValidationException;

    void editItems(String itemId, Item item) throws VendingMachinePersistenceException;

    String auditEntry(Item item);

}
