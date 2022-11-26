package com.bea.common.security.service;

import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.Resource;

public interface RoleConsumerService {
   boolean isRoleConsumerAvailable();

   RoleCollectionHandler getRoleCollectionHandler(String var1, String var2, String var3, Resource[] var4) throws ConsumptionException;

   public interface RoleCollectionHandler {
      void setRole(Resource var1, String var2, String[] var3) throws ConsumptionException;

      void done() throws ConsumptionException;
   }
}
