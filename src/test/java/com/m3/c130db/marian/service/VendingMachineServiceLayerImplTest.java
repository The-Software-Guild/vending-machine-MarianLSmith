package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.VendingMachineAuditDao;
import com.m3.c130db.marian.dao.VendingMachineDao;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;
import com.m3.c130db.marian.ui.UserIO;
import com.m3.c130db.marian.ui.VendingMachineView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    private UserIO io;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, view, auditDao);
    }

    @Test
    public void testCreateValidItem() {
        Item item = new Item("T3");
        item.setName("Crisps");
        item.setCost(new BigDecimal("0.9"));
        item.setRemainingQuantity(2);

        try {
            service.updateQuantity(item, true);
        } catch (VendingMachineQuantityValidationException
                | VendingMachinePersistenceException e) {

            fail("Item was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Item testClone = new Item("T4");
        testClone.setName("Sweets");
        testClone.setCost(new BigDecimal("1"));
        testClone.setRemainingQuantity(15);

        assertEquals(1, service.listItems().size(),
                "Should only have one item.");
        assertTrue(service.listItems().contains(testClone),
                "The one student should be Ada.");
    }

}