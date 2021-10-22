package com.m3.c130db.marian;

import com.m3.c130db.marian.controller.VendingMachineController;
import com.m3.c130db.marian.dao.*;
import com.m3.c130db.marian.service.VendingMachineServiceLayer;
import com.m3.c130db.marian.service.VendingMachineServiceLayerImpl;
import com.m3.c130db.marian.ui.UserIO;
import com.m3.c130db.marian.ui.UserIOConsoleImpl;
import com.m3.c130db.marian.ui.VendingMachineView;

public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {
        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myView, myAuditDao);
        VendingMachineController controller = new VendingMachineController(myView, myService);
        controller.run();
    }
}
