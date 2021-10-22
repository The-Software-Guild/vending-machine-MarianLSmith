package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.Change;
import com.m3.c130db.marian.dao.VendingMachineAuditDao;
import com.m3.c130db.marian.dao.VendingMachineDao;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;
import com.m3.c130db.marian.ui.VendingMachineView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {


    VendingMachineDao dao;
    VendingMachineView view;

    private VendingMachineAuditDao auditDao;


    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineView view, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.view = view;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> listItems() throws VendingMachinePersistenceException {
        return dao.listItems();
    }

    @Override
    public Item addItem(String itemId, Item item) throws VendingMachinePersistenceException {
        return dao.addItem(itemId, item);
    }

    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException {
        return dao.getItem(itemId);
    }


    @Override
    public Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException {
        return dao.updateQuantity(item, transactionComplete);

    }

    @Override
    public void editItems(String itemId, Item item) throws VendingMachinePersistenceException {
        dao.editItems(itemId, item);
    }

    public boolean transaction(Item item) {

        boolean transactionComplete;
        BigDecimal currentBalance = new BigDecimal("0");
        BigDecimal outstandingBalance = item.getCost().subtract(currentBalance);
        view.displayItemInfo(item);
//        try {
            do {
                view.displayTransactionInfo(currentBalance, outstandingBalance);
                BigDecimal additionalCash = new BigDecimal(view.getCash());
                currentBalance = currentBalance.add(additionalCash);
                outstandingBalance = item.getCost().subtract(currentBalance);
                transactionComplete = (outstandingBalance.compareTo(new BigDecimal("0")) <= 0);
            } while (!transactionComplete);
//        }
//        catch (Exception e){}


        if (transactionComplete) {
            BigDecimal customerChange = currentBalance.subtract(item.getCost());
            view.transactionComplete(item);
            view.giveChange((customerChange));

            String changeString = changeObj(
                    customerChange, Change.TENPOUND, Change.FIVEPOUND, Change.TWOPOUND,
                    Change.ONEPOUND, Change.FIFTYPENCE, Change.TWENTYPENCE, Change.TENPENCE,
                    Change.FIVEPENCE, Change.TWOPENCE, Change.ONEPENCE);
            view.displayChangeBreakdown(changeString);


            }
        return transactionComplete;
    }
    public String changeObj (BigDecimal customerChange,
                                 Change ... changeList) {
        String changeString = "";
        for (Change change : changeList) {
            BigDecimal nChange = customerChange.divide(change.getValue(), 0, RoundingMode.DOWN);
            customerChange = customerChange.remainder(change.getValue());
            changeString = changeString + "\n" + change.getCoinName() + " = " + nChange;
        }
        return changeString;
    }

    public String auditEntry(Item item){
       String entry = "Item " + item.getItemId() + " " + item.getName()
                + " Chosen. " + "Item cost: " + item.getCost() + ". Remaining Quantity: " + item.getRemainingQuantity();
       return entry;
    }
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry(entry);
    }

    }

