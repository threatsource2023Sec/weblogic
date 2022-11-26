package com.bea.core.jmspool.internal;

import java.util.HashMap;
import java.util.Iterator;
import weblogic.deployment.jms.JMSSessionPoolManager;

public class Activator {
   public void managedLifecycleInitialize() {
   }

   public void managedLifecycleDispose() throws Exception {
      JMSSessionPoolManager manager = JMSSessionPoolManager.getSessionPoolManager();
      HashMap pools = manager.getSessionPools();
      Iterator poolNames = pools.keySet().iterator();

      while(poolNames.hasNext()) {
         String name = (String)poolNames.next();
         manager.destroyPool(name, 0L, false);
      }

   }
}
