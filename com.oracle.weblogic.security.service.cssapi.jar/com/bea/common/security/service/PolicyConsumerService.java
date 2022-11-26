package com.bea.common.security.service;

import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.Resource;

public interface PolicyConsumerService {
   boolean isPolicyConsumerAvailable();

   PolicyCollectionHandler getPolicyCollectionHandler(String var1, String var2, String var3, Resource[] var4) throws ConsumptionException;

   public interface PolicyCollectionHandler {
      void setPolicy(Resource var1, String[] var2) throws ConsumptionException;

      void setUncheckedPolicy(Resource var1) throws ConsumptionException;

      void done() throws ConsumptionException;
   }
}
