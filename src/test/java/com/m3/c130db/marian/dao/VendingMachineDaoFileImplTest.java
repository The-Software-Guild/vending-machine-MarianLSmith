package com.m3.c130db.marian.dao;

import com.m3.c130db.marian.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testinventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetStudent() throws Exception {
        String itemId = "T1";
        Item item = new Item(itemId);
        item.setName("Walkers");
        item.setCost(new BigDecimal("0.72"));
        item.setRemainingQuantity(18);

        testDao.addItem(itemId, item);

        Item retrievedItem = testDao.getItem(itemId);

        assertEquals(item.getItemId(),
                retrievedItem.getItemId(),
                "Checking item id.");
        assertEquals(item.getName(),
                retrievedItem.getName(),
                "Checking item name.");
        assertEquals(item.getCost(),
                retrievedItem.getCost(),
                "Checking item's cost.");
        assertEquals(item.getCost(),
                retrievedItem.getRemainingQuantity(),
                "Checking item quantity.");
    }

    @Test
    public void AddGetAllItems() throws Exception {
        Item item1 = new Item("T1");
        item1.setName("Walkers");
        item1.setCost(new BigDecimal("0.72"));
        item1.setRemainingQuantity(18);

        Item item2 = new Item("T2");
        item2.setName("Monster Munch");
        item2.setCost(new BigDecimal("0.85"));
        item2.setRemainingQuantity(10);

        testDao.addItem(item1.getItemId(), item1);
        testDao.addItem(item1.getItemId(), item2);

        List<Item> allItems = testDao.listItems();

        assertNotNull(allItems, "The list of items must not null");
        assertEquals(2, allItems.size(), "List of items should have 2 students.");

        assertTrue(testDao.listItems().contains(item1),
                "The list of students should include Walkers.");
        assertTrue(testDao.listItems().contains(item2),
                "The list of students should include Monster Munch.");
    }

    @Test
    public void testRemainingQuantity() throws Exception {

        Item item1 = new Item("T1");
        item1.setName("Walkers");
        item1.setCost(new BigDecimal("0.72"));
        item1.setRemainingQuantity(18);

        Item item2 = new Item("T2");
        item2.setName("Monster Munch");
        item2.setCost(new BigDecimal("0.85"));
        item2.setRemainingQuantity(10);

        // Add both to the DAO
        testDao.addItem(item1.getItemId(), item1);
        testDao.addItem(item1.getItemId(), item2);

        List<Item> allItems = testDao.listItems();
        assertNotNull(allItems, "All items list should be not null.");

        int beforePurchase1 = testDao.getItem("T1").getRemainingQuantity();
        int beforePurchase2 = testDao.getItem("T2").getRemainingQuantity();

        int afterPurchase1 = testDao.updateQuantity(testDao.getItem("T1"), true).getRemainingQuantity();
        int afterPurchase2 = testDao.updateQuantity(testDao.getItem("T2"), true).getRemainingQuantity();

        assertEquals(2, allItems.size(), "Item list should only have 2 items.");

        assertEquals(beforePurchase1, 18, "Check remaining quantity should be 18");
        assertEquals(beforePurchase2, 10, "Check remaining quantity should be 10");

        assertEquals(beforePurchase1, afterPurchase1 + 1, "Before and after purchase should be equal");
        assertEquals(beforePurchase2, afterPurchase2 + 1, "Before and after purchase should be equal");

        assertEquals(afterPurchase1, 17, "Check remaining quantity should be 17");
        assertEquals(afterPurchase2, 9, "Check remaining quantity should be 9");

        assertTrue(allItems.contains(item1), "Item list should include Walkers");
        assertTrue(allItems.contains(item2), "Item list should include Monster Munch");

    }
}