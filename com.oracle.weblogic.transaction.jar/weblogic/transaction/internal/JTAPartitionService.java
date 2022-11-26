package weblogic.transaction.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.configuration.JTAPartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JTAPartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.transaction.TransactionHelper;

public class JTAPartitionService {
   private static final boolean isPartitionLifecycleOldmodel = false;
   public static volatile JTAPartitionService singleton = null;
   private ServerTransactionManagerImpl tm;
   public String GLOBAL_PARTITION_NAME = "DOMAIN";
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final RuntimeAccess runtimeAccess;
   PartitionPropertyListener partitionPropertyListener;
   PartitionAddDelPropertyListener partitionAddDelPropertyListener;
   JTAMBean jtaMBean;
   private Map partitionManagers;
   private static final boolean DEBUG = false;

   public JTAPartitionService() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.partitionManagers = new ConcurrentHashMap();
   }

   public JTAPartitionService(TransactionManagerImpl atm) {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.partitionManagers = new ConcurrentHashMap();
      this.tm = (ServerTransactionManagerImpl)atm;
      this.jtaMBean = this.runtimeAccess.getDomain().getJTA();
   }

   public static JTAPartitionService getOrCreateJTAPartitionService() {
      if (singleton == null) {
         Class var0 = JTAPartitionService.class;
         synchronized(JTAPartitionService.class) {
            if (singleton == null) {
               TransactionManagerImpl transactionManagerImpl = TransactionManagerImpl.getTM();
               if (transactionManagerImpl instanceof ClientTransactionManagerImpl) {
                  singleton = new JTAPartitionService();
               } else {
                  singleton = new JTAPartitionService(transactionManagerImpl);
               }
            }
         }
      }

      return singleton;
   }

   public void partitionStart(ServerTransactionManagerImpl atm) throws ServiceFailureException {
      this.tm = atm;
      PartitionRuntimeMBean[] partitions = this.runtimeAccess.getServerRuntime().getPartitionRuntimes();
      if (partitions != null) {
         PartitionRuntimeMBean[] var3 = partitions;
         int var4 = partitions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PartitionRuntimeMBean partitionRuntime = var3[var5];
            JTAPartitionMBean jtaPartition = this.runtimeAccess.getDomain().lookupPartition(partitionRuntime.getName()).getJTAPartition();
            JTAPartitionRuntimeMBean var8 = (JTAPartitionRuntimeMBean)this.initializeJTAPartitionManager(jtaPartition, partitionRuntime);
         }
      }

      this.partitionAddDelPropertyListener = new PartitionAddDelPropertyListener();
      this.runtimeAccess.getServerRuntime().addPropertyChangeListener(this.partitionAddDelPropertyListener);
   }

   public synchronized JTAPartitionRuntime findOrCreateJTAPartitionRuntime(String partitionName, PartitionRuntimeMBean partitionRuntime) {
      JTAPartitionRuntime pr = (JTAPartitionRuntime)this.partitionManagers.get(partitionName);
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionService.findOrCreateJTAPartitionRuntime: " + partitionName + " JTAPartitionRuntime:" + pr);
      }

      if (pr == null) {
         try {
            pr = new JTAPartitionRuntimeImpl("JTAPartitionRuntime", partitionRuntime, this.tm);
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionService.findOrCreateJTAPartitionRuntime: " + partitionName + " JTAPartitionRuntime:" + pr.hashCode() + " getJTAPartitionRuntime:" + partitionRuntime.getJTAPartitionRuntime().hashCode() + " partitionRuntime:" + partitionRuntime.hashCode());
            }

            this.tm.setJTAPartitionRuntime(partitionName, (JTAPartitionRuntime)pr);
            this.partitionManagers.put(partitionName, pr);
         } catch (ManagementException var5) {
            throw new RuntimeException(var5);
         }
      }

      return (JTAPartitionRuntime)pr;
   }

   public JTAPartitionRuntime findJTAPartitionRuntime(String partitionName) {
      JTAPartitionRuntime pr = (JTAPartitionRuntime)this.partitionManagers.get(partitionName);
      if (pr == null) {
         throw new RuntimeException("JTAPartition for the Partition " + partitionName + " not found");
      } else {
         return pr;
      }
   }

   JTAPartitionRuntime initializeJTAPartitionManager(JTAPartitionMBean jtaPartition, PartitionRuntimeMBean partitionRuntime) {
      if (jtaPartition == null) {
         return null;
      } else {
         String name = jtaPartition.getName();
         JTAPartitionRuntime pr = this.findOrCreateJTAPartitionRuntime(name, partitionRuntime);
         pr.setTimeoutSeconds(jtaPartition.getTimeoutSeconds());
         this.tm.setDeterminers(this.tm.getDeterminersForDomainAndAllPartitions());
         this.partitionPropertyListener = new PartitionPropertyListener();
         jtaPartition.addPropertyChangeListener(this.partitionPropertyListener);
         this.jtaMBean.addPropertyChangeListener(this.partitionPropertyListener);
         return pr;
      }
   }

   void deinitializeJTAPartitionManager(String pname) {
      String[] noDeterminers = new String[0];
      this.tm.setPartitionDeterminers(pname, noDeterminers);
      this.tm.removePartitionRuntime(pname);
      JTAPartitionRuntime jpr = (JTAPartitionRuntime)this.partitionManagers.get(pname);
      if (jpr != null) {
         jpr.reset();
      }

      this.partitionManagers.remove(pname);
      this.tm.setDeterminers(this.tm.getDeterminersForDomainAndAllPartitions());
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionService partition deinitialize partitionName: " + pname);
      }

   }

   public String getPartitionName() {
      return this.getPartitionName(false);
   }

   public String getPartitionName(boolean isAdmin) {
      if (!isAdmin && this.partitionManagers.size() == 0) {
         return null;
      } else {
         ComponentInvocationContext cic = this.getCurrentComponentInvocationContext(isAdmin);
         if (cic == null) {
            return null;
         } else {
            String partitionName = cic.getPartitionName();
            String partitionId = cic.getPartitionId();
            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getPartitionName partitionName: " + partitionName);
            }

            if (partitionName == null) {
               return null;
            } else {
               return partitionName.equals(this.GLOBAL_PARTITION_NAME) ? null : partitionName;
            }
         }
      }
   }

   public int getTimeoutPartition() {
      int timeoutSeconds = -1;
      String partitionName = null;
      partitionName = this.getPartitionName();
      if (partitionName != null) {
         JTAPartitionRuntime prt = this.findJTAPartitionRuntime(partitionName);
         timeoutSeconds = prt.getTimeoutSeconds();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getTimeoutPartition partitionName=" + partitionName + " timeoutSeconds=" + timeoutSeconds);
      }

      return timeoutSeconds;
   }

   public int getTimeoutPartition(String partitionName) {
      int timeoutSeconds = true;
      if (partitionName != null) {
         JTAPartitionRuntime prt = this.findJTAPartitionRuntime(partitionName);
         int timeoutSeconds = prt.getTimeoutSeconds();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getTimeoutPartition partitionName=" + partitionName + " timeoutSeconds=" + timeoutSeconds);
         }

         return timeoutSeconds;
      } else {
         return -1;
      }
   }

   public String[] getDeterminersPartition() {
      String[] determiners = null;
      String partitionName = null;
      partitionName = this.getPartitionName();
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getDeterminers partitionName: " + partitionName);
      }

      if (partitionName != null) {
         JTAPartitionRuntime prt = this.findJTAPartitionRuntime(partitionName);
         determiners = prt.getDeterminers();
      }

      if (TxDebug.JTA2PC.isDebugEnabled()) {
         String dtmrs = "";
         if (determiners != null) {
            String[] var4 = determiners;
            int var5 = determiners.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String s = var4[var6];
               dtmrs = dtmrs == null ? s : dtmrs + " " + s;
            }
         }

         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getDeterminerPartition partitionName=" + partitionName + " determiners=" + dtmrs);
      }

      return determiners;
   }

   public String[] getDeterminersPartition(String partitionName) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getDeterminerPartition partitionName: " + partitionName);
      }

      if (partitionName != null) {
         JTAPartitionRuntime prt = this.findJTAPartitionRuntime(partitionName);
         return prt.getDeterminers();
      } else {
         return null;
      }
   }

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      if (this.partitionManagers.size() == 0) {
         return null;
      } else {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getCurrentComponentInvocationContext cic: " + cic);
         }

         return cic;
      }
   }

   public ComponentInvocationContext getCurrentComponentInvocationContext(boolean isAdmin) {
      if (!isAdmin && this.partitionManagers.size() == 0) {
         return null;
      } else {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "JTAPartitionService.getCurrentComponentInvocationContext cic: " + cic + " isAdmin:" + isAdmin);
         }

         return cic;
      }
   }

   final class PartitionAddDelPropertyListener implements PropertyChangeListener {
      private PartitionAddDelPropertyListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
      }

      // $FF: synthetic method
      PartitionAddDelPropertyListener(Object x1) {
         this();
      }
   }

   final class PartitionPropertyListener implements PropertyChangeListener {
      private PartitionPropertyListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         String propertyName = evt.getPropertyName();
         Object source = evt.getSource();
         Integer valx;
         if (source != null && source instanceof JTAPartitionMBean) {
            JTAPartitionMBean jtaPartition;
            String name;
            if ("TimeoutSeconds".equals(propertyName)) {
               valx = (Integer)evt.getNewValue();
               jtaPartition = (JTAPartitionMBean)source;
               name = jtaPartition.getName();
               ((JTAPartitionRuntime)JTAPartitionService.this.partitionManagers.get(name)).setTimeoutSeconds(valx);
            } else if ("Determiners".equals(propertyName)) {
               String[] val = (String[])((String[])evt.getNewValue());
               jtaPartition = (JTAPartitionMBean)source;
               name = jtaPartition.getName();
               ((JTAPartitionRuntime)JTAPartitionService.this.partitionManagers.get(name)).setDeterminers(val);
               JTAPartitionService.this.tm.setPartitionDeterminers(name, val);
               JTAPartitionService.this.tm.setDeterminers(JTAPartitionService.this.tm.getDeterminersForDomainAndAllPartitions());
            }
         } else if (source != null && source instanceof JTAMBean && "TimeoutSeconds".equals(propertyName)) {
            valx = (Integer)evt.getNewValue();
            Iterator it = JTAPartitionService.this.partitionManagers.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry mapEntry = (Map.Entry)it.next();
               String partitionName = (String)mapEntry.getKey();
               JTAPartitionRuntime jtaPartitionRuntime = (JTAPartitionRuntime)mapEntry.getValue();
               JTAPartitionMBean jtaPartitionx = JTAPartitionService.this.runtimeAccess.getDomain().lookupPartition(partitionName).getJTAPartition();
               int partitionTimeout = jtaPartitionx.getTimeoutSeconds();
               if (valx == partitionTimeout) {
                  jtaPartitionRuntime.setTimeoutSeconds(valx);
               }
            }
         }

      }

      // $FF: synthetic method
      PartitionPropertyListener(Object x1) {
         this();
      }
   }
}
