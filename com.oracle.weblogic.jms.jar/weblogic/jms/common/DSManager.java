package weblogic.jms.common;

import java.util.HashMap;
import weblogic.jms.JMSService;

public final class DSManager {
   private static HashMap dsManagers = new HashMap();
   private final HashMap dsMap = new HashMap();

   private DSManager() {
   }

   public static synchronized DSManager manager() {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      DSManager dsManager = (DSManager)dsManagers.get(partitionName);
      if (dsManager == null) {
         dsManager = new DSManager();
         dsManagers.put(partitionName, dsManager);
      }

      return dsManager;
   }

   public static synchronized void removeDSManager() {
      dsManagers.remove(JMSService.getSafePartitionNameFromThread());
   }

   public synchronized void add(DurableSubscription newDS) {
      String dsName = newDS.getName();
      this.dsMap.put(dsName, newDS);
   }

   public synchronized DurableSubscription lookup(String dsName) {
      return (DurableSubscription)this.dsMap.get(dsName);
   }

   public synchronized void remove(DurableSubscription oldDS) {
      String dsName = oldDS.getName();
      this.dsMap.remove(dsName);
   }
}
