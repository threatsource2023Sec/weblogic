package weblogic.connector.common;

import java.util.Iterator;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.utils.cmm.MemoryPressureListener;

public class ConnectorMemoryPressureListener implements MemoryPressureListener {
   private static final String DEFAULT_SHRINKTHRESHOLD = "8";
   private final int shrinkThreshold;
   private int latestLevel;
   private static final int[] ShrinkFrequencySeconds = new int[]{900, 900, 900, 900, 900, 900, 480, 240, 120, 60};

   public ConnectorMemoryPressureListener() {
      int t = Integer.parseInt(System.getProperty("weblogic.connector.cmm.shrinkThreshold", "8"));
      if (t >= 0 && t <= 10) {
         this.shrinkThreshold = t;
         this.latestLevel = 0;
      } else {
         String msgId = ConnectorLogger.logInvalidShrinkThreshold(t);
         throw new IllegalArgumentException(msgId);
      }
   }

   public synchronized void handleCMMLevel(int newLevel) {
      if (Debug.isPoolingEnabled()) {
         Debug.pooling("Received new memory pressure level " + newLevel);
      }

      if (this.shrinkThreshold == 0) {
         this.latestLevel = newLevel;
      } else {
         if (newLevel >= this.shrinkThreshold) {
            if (this.latestLevel != newLevel) {
               if (Debug.isPoolingEnabled()) {
                  Debug.pooling("Update shrink frequency for all connection pools due to memory pressure level becomes " + newLevel);
               }

               this.updateShrinkFrequency(newLevel);
            }
         } else if (this.latestLevel >= this.shrinkThreshold) {
            if (Debug.isPoolingEnabled()) {
               Debug.pooling("Recover shrink frequency configuration for all connection pools due to memory pressure level becomes " + newLevel);
            }

            this.recoverShrinkFrequency();
         }

         this.latestLevel = newLevel;
      }
   }

   private void updateShrinkFrequency(int newLevel) {
      Iterator var2 = RACollectionManager.getAllRAInstanceManagers().iterator();

      while(true) {
         RAInstanceManager ra;
         do {
            if (!var2.hasNext()) {
               return;
            }

            Object item = var2.next();
            ra = (RAInstanceManager)item;
         } while(!ra.isActivated() && !ra.isSuspended());

         Iterator var5 = ra.getRAOutboundManager().getAllConnectionPool().iterator();

         while(var5.hasNext()) {
            ConnectionPool pool = (ConnectionPool)var5.next();
            pool.setShrinkFrequencySeconds(getShrinkFrequencySeconds(newLevel));
            if (!pool.isShrinkingEnabled()) {
               pool.setShrinkEnabled(true);
            }
         }
      }
   }

   private void recoverShrinkFrequency() {
      Iterator var1 = RACollectionManager.getAllRAInstanceManagers().iterator();

      while(true) {
         RAInstanceManager ra;
         do {
            if (!var1.hasNext()) {
               return;
            }

            Object item = var1.next();
            ra = (RAInstanceManager)item;
         } while(!ra.isActivated() && !ra.isSuspended());

         Iterator var4 = ra.getRAOutboundManager().getAllConnectionPool().iterator();

         while(var4.hasNext()) {
            ConnectionPool pool = (ConnectionPool)var4.next();
            pool.recoverShrinkFrequency();
         }
      }
   }

   public int getShrinkThreshold() {
      return this.shrinkThreshold;
   }

   public static int getShrinkFrequencySeconds(int memoryPressureLevel) {
      return ShrinkFrequencySeconds[memoryPressureLevel - 1];
   }
}
