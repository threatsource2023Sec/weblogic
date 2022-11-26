package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Iterator;
import weblogic.common.ResourceException;
import weblogic.jdbc.JDBCLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class MemoryPressureManager implements MemoryPressureListener {
   private static final AuthenticatedSubject KERNELID = getKernelID();
   private static MemoryPressureManager singleton = new MemoryPressureManager();
   static final String DEFAULT_STATEMENTCACHEWEIGHTS = "1=10,2=10,3=10,4=10,5=10,6=10,7=10,8=10,9=10,10=10";
   static final String DEFAULT_SHRINKTHRESHOLD = "8";
   private int shrinkThreshold;
   private GraduatedWeights cacheWeights;

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public static MemoryPressureManager getInstance() {
      return singleton;
   }

   private MemoryPressureManager() {
      String configCacheWeights = System.getProperty("weblogic.jdbc.cmm.statementCacheWeights", "1=10,2=10,3=10,4=10,5=10,6=10,7=10,8=10,9=10,10=10");
      this.cacheWeights = new GraduatedWeights(configCacheWeights);
      int t = Integer.parseInt(System.getProperty("weblogic.jdbc.cmm.shrinkThreshold", "8"));
      if (t >= 0 && t <= 10) {
         this.shrinkThreshold = t;
      } else {
         throw new IllegalArgumentException("Invalid value of " + t + " specified for system property weblogic.jdbc.cmm.shrinkThreshold.");
      }
   }

   GraduatedWeights getCacheWeights() {
      return this.cacheWeights;
   }

   void adjustStatementCacheSize(final int level) {
      try {
         SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               int weight = MemoryPressureManager.this.cacheWeights.getWeight(level);
               JDBCLogger.logSettingStatementCacheSizes(100 - weight, level);
               ConnectionPoolManager cpm = JDBCService.getConnectionPoolManager();
               Iterator it = ConnectionPoolManager.getConnectionPools();

               while(it.hasNext()) {
                  ConnectionPool cp = (ConnectionPool)it.next();
                  if (cp.getStateAsInt() != 100) {
                     Integer cacheSize = Integer.parseInt(cp.getConfig().getPoolProperties().getProperty("PSCacheSize"));
                     if (cacheSize == 0) {
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("Not altering statement cache size for pool " + cp.getName() + " (disabled)");
                        }
                     } else {
                        int weightedSize = MemoryPressureManager.this.cacheWeights.getWeightedSize(cacheSize, level);
                        JDBCLogger.logSetStatementCacheSize(cp.getName(), weightedSize, 100 - weight, cacheSize);
                        cp.setStatementCacheSize(weightedSize);
                     }
                  }
               }

               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         JDBCLogger.logErrorMessage(var3.getLocalizedMessage());
      }

   }

   void shrinkPools(int level) {
      final boolean shrink = this.shrinkThreshold > 0 && level >= this.shrinkThreshold;
      if (shrink) {
         JDBCLogger.logShrinkOnMemoryPressure(level, this.shrinkThreshold);
      }

      try {
         SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               ConnectionPoolManager cpm = JDBCService.getConnectionPoolManager();
               Iterator it = ConnectionPoolManager.getConnectionPools();

               while(true) {
                  ConnectionPool cp;
                  do {
                     if (!it.hasNext()) {
                        return null;
                     }

                     cp = (ConnectionPool)it.next();
                  } while(!shrink);

                  try {
                     cp.shrink();
                  } catch (ResourceException var5) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("Error attempting to shrink connection pool " + cp.getName() + ": " + var5.getLocalizedMessage());
                     }
                  }
               }
            }
         });
      } catch (PrivilegedActionException var4) {
         JDBCLogger.logErrorMessage(var4.getLocalizedMessage());
      }

   }

   public void handleCMMLevel(int newLevel) {
      WorkManager wm = WorkManagerFactory.getInstance().getDefault();
      wm.schedule(new MemoryPressureRunnable(newLevel));
   }

   class MemoryPressureRunnable implements Runnable {
      int level;

      public MemoryPressureRunnable(int level) {
         this.level = level;
      }

      public void run() {
         MemoryPressureManager.this.adjustStatementCacheSize(this.level);
         MemoryPressureManager.this.shrinkPools(this.level);
      }
   }

   static class GraduatedWeights {
      private int[] weights = new int[10];

      public GraduatedWeights(String weightList) {
         this.init(weightList);
      }

      public int[] getWeights() {
         return this.weights;
      }

      public int getWeight(int level) {
         if (level >= 0 && level <= 10) {
            int weight = 0;

            for(int i = 0; i < level; ++i) {
               weight += this.weights[i];
            }

            return weight;
         } else {
            throw new IllegalArgumentException("level " + level + " out of range (0-10)");
         }
      }

      public int getWeightedSize(int maxSize, int level) {
         int weight = this.getWeight(level);
         return (int)((100.0F - (float)weight) / 100.0F * (float)maxSize);
      }

      private void init(String weightList) {
         if (weightList != null && !weightList.equals("")) {
            String[] nameValues = weightList.split("[\\s,]");
            if (nameValues != null && nameValues.length != 0) {
               int totalWeight = 0;
               String[] var4 = nameValues;
               int var5 = nameValues.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String nameValue = var4[var6];
                  String[] nv = nameValue.split("=");
                  Integer level = Integer.parseInt(nv[0]);
                  if (level < 1 || level > 10) {
                     throw new IllegalArgumentException("level " + level + " out of range: " + weightList);
                  }

                  Integer weight = Integer.parseInt(nv[1]);
                  if (weight < 0) {
                     throw new IllegalArgumentException("level " + level + " weight can't be negative: " + weightList);
                  }

                  totalWeight += weight;
                  if (totalWeight > 100) {
                     throw new IllegalArgumentException("weight total exceeds 100: " + weightList);
                  }

                  this.weights[level - 1] = weight;
               }

            }
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof GraduatedWeights)) {
            return false;
         } else {
            GraduatedWeights that = (GraduatedWeights)o;
            return Arrays.equals(this.weights, that.weights);
         }
      }
   }
}
