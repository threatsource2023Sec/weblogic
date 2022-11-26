package org.apache.openjpa.ee;

import java.util.LinkedList;
import java.util.List;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;

public class AutomaticManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime, Configurable {
   private static final String[] JNDI_LOCS = new String[]{"javax.transaction.TransactionManager", "java:/TransactionManager", "java:/DefaultDomain/TransactionManager", "java:comp/pm/TransactionManager", "java:comp/TransactionManager", "java:pm/TransactionManager"};
   private static final String[] METHODS = new String[]{"com.arjuna.jta.JTA_TransactionManager.transactionManager", "com.bluestone.jta.SaTransactionManagerFactory.SaGetTransactionManager", "org.openejb.OpenEJB.getTransactionManager", "com.sun.jts.jta.TransactionManagerImpl.getTransactionManagerImpl", "com.inprise.visitransact.jta.TransactionManagerImpl.getTransactionManagerImpl"};
   private static final ManagedRuntime REGISTRY;
   private static final WLSManagedRuntime WLS;
   private static final SunOneManagedRuntime SUNONE;
   private static final WASManagedRuntime WAS;
   private static Localizer _loc = Localizer.forPackage(AutomaticManagedRuntime.class);
   private Configuration _conf = null;
   private ManagedRuntime _runtime = null;

   public TransactionManager getTransactionManager() throws Exception {
      if (this._runtime != null) {
         return this._runtime.getTransactionManager();
      } else {
         List errors = new LinkedList();
         TransactionManager tm = null;
         if (REGISTRY != null) {
            try {
               tm = REGISTRY.getTransactionManager();
            } catch (Throwable var12) {
               errors.add(var12);
            }

            if (tm != null) {
               this._runtime = REGISTRY;
               return tm;
            }
         }

         if (WLS != null) {
            try {
               tm = WLS.getTransactionManager();
            } catch (Throwable var11) {
               errors.add(var11);
            }

            if (tm != null) {
               this._runtime = WLS;
               return tm;
            }
         }

         if (WAS != null) {
            try {
               WAS.setConfiguration(this._conf);
               WAS.startConfiguration();
               WAS.endConfiguration();
               tm = WAS.getTransactionManager();
            } catch (Throwable var10) {
               errors.add(var10);
            }

            if (tm != null) {
               this._runtime = WAS;
               return tm;
            }
         }

         JNDIManagedRuntime jmr = new JNDIManagedRuntime();

         for(int i = 0; i < JNDI_LOCS.length; ++i) {
            jmr.setTransactionManagerName(JNDI_LOCS[i]);

            try {
               tm = jmr.getTransactionManager();
            } catch (Throwable var9) {
               errors.add(var9);
            }

            if (tm != null) {
               this._runtime = jmr;
               return tm;
            }
         }

         InvocationManagedRuntime imr = new InvocationManagedRuntime();

         for(int i = 0; i < METHODS.length; ++i) {
            imr.setConfiguration(this._conf);
            imr.setTransactionManagerMethod(METHODS[i]);

            try {
               tm = imr.getTransactionManager();
            } catch (Throwable var8) {
               errors.add(var8);
            }

            if (tm != null) {
               this._runtime = imr;
               return tm;
            }
         }

         if (SUNONE != null) {
            try {
               tm = SUNONE.getTransactionManager();
            } catch (Throwable var7) {
               errors.add(var7);
            }

            if (tm != null) {
               this._runtime = SUNONE;
               return tm;
            }
         }

         Throwable[] t = (Throwable[])((Throwable[])errors.toArray(new Throwable[errors.size()]));
         throw (new InvalidStateException(_loc.get("tm-not-found"))).setFatal(true).setNestedThrowables(t);
      }
   }

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      if (this._runtime == null) {
         this.getTransactionManager();
      }

      if (this._runtime != null) {
         this._runtime.setRollbackOnly(cause);
      }

   }

   public Throwable getRollbackCause() throws Exception {
      if (this._runtime == null) {
         this.getTransactionManager();
      }

      return this._runtime != null ? this._runtime.getRollbackCause() : null;
   }

   public Object getTransactionKey() throws Exception, SystemException {
      if (this._runtime == null) {
         this.getTransactionManager();
      }

      return this._runtime != null ? this._runtime.getTransactionKey() : null;
   }

   static {
      ManagedRuntime mr = null;
      mr = null;

      try {
         mr = (ManagedRuntime)Class.forName("org.apache.openjpa.ee.RegistryManagedRuntime").newInstance();
      } catch (Throwable var5) {
      }

      REGISTRY = mr;
      ManagedRuntime mr = null;

      try {
         mr = new WLSManagedRuntime();
      } catch (Throwable var4) {
      }

      WLS = (WLSManagedRuntime)mr;
      ManagedRuntime mr = null;

      try {
         mr = new SunOneManagedRuntime();
      } catch (Throwable var3) {
      }

      SUNONE = (SunOneManagedRuntime)mr;
      ManagedRuntime mr = null;

      try {
         mr = new WASManagedRuntime();
      } catch (Throwable var2) {
      }

      WAS = (WASManagedRuntime)mr;
   }
}
