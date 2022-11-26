package com.bea.core.security.managers.internal;

import com.bea.core.security.managers.ManagerFactory;
import com.bea.core.security.managers.ManagerService;

public class ManagerFactoryImpl extends ManagerFactory {
   public ManagerService getManagerService() {
      return new ManagerImpl();
   }
}
