package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.jdbc.JDBCLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCPartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JDBCPartition {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static Map jdbcPartitions = new HashMap();
   private String name;
   private Map dsRTMBeans = new HashMap();
   private Map mdsRTMBeans = new HashMap();
   private PartitionRuntimeMBean partitionRuntime;
   private JDBCPartitionRuntimeMBean jdbcPartitionRuntime;

   JDBCPartition(String name) {
      this.name = name;
   }

   JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans() {
      return (JDBCDataSourceRuntimeMBean[])this.dsRTMBeans.values().toArray(new JDBCDataSourceRuntimeMBean[this.dsRTMBeans.size()]);
   }

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name) {
      return (JDBCDataSourceRuntimeMBean)this.dsRTMBeans.get(name);
   }

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      String qname = JDBCUtil.getDecoratedName(name, appName, moduleName, componentName);
      return (JDBCDataSourceRuntimeMBean)this.dsRTMBeans.get(qname);
   }

   public JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans() {
      return (JDBCMultiDataSourceRuntimeMBean[])this.mdsRTMBeans.values().toArray(new JDBCMultiDataSourceRuntimeMBean[this.mdsRTMBeans.size()]);
   }

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name) {
      return (JDBCMultiDataSourceRuntimeMBean)this.mdsRTMBeans.get(name);
   }

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      String qname = JDBCUtil.getDecoratedName(name, appName, moduleName, componentName);
      return (JDBCMultiDataSourceRuntimeMBean)this.mdsRTMBeans.get(qname);
   }

   void addDataSourceRuntime(String qname, JDBCDataSourceRuntimeMBean dsRTMBean) throws ManagementException {
      this.createJDBCPartitionRuntimeIfNecessary(dsRTMBean);
      this.dsRTMBeans.put(qname, dsRTMBean);
   }

   JDBCDataSourceRuntimeMBean removeDataSourceRuntime(String qname) throws ManagementException {
      return (JDBCDataSourceRuntimeMBean)this.removeRuntime(this.dsRTMBeans, qname);
   }

   void addMultiDataSourceRuntime(String qname, JDBCMultiDataSourceRuntimeMBean mdsRTMBean) throws ManagementException {
      this.createJDBCPartitionRuntimeIfNecessary(mdsRTMBean);
      this.mdsRTMBeans.put(qname, mdsRTMBean);
   }

   JDBCMultiDataSourceRuntimeMBean removeMultiDataSourceRuntime(String qname) throws ManagementException {
      return (JDBCMultiDataSourceRuntimeMBean)this.removeRuntime(this.mdsRTMBeans, qname);
   }

   boolean isEmpty() {
      return this.dsRTMBeans.isEmpty() && this.mdsRTMBeans.isEmpty();
   }

   public JDBCPartitionRuntimeMBean getJDBCPartitionRuntime() {
      return this.jdbcPartitionRuntime;
   }

   public void cleanup() throws ManagementException {
      if (this.jdbcPartitionRuntime != null) {
         RuntimeMBeanHelper.getHelper().unregister((RuntimeMBean)this.jdbcPartitionRuntime);
      }

      this.jdbcPartitionRuntime = null;
      if (this.partitionRuntime != null) {
         RuntimeMBeanHelper.getHelper().unregister((RuntimeMBean)this.partitionRuntime);
      }

      this.partitionRuntime = null;
   }

   private synchronized RuntimeMBean removeRuntime(Map mbeans, String qname) throws ManagementException {
      List toUnregister = new ArrayList();
      RuntimeMBean runtimeMBean = null;
      boolean unsetJDBCPartitionRuntime = false;
      synchronized(this) {
         runtimeMBean = (RuntimeMBean)mbeans.remove(qname);
         if (runtimeMBean != null) {
            toUnregister.add(runtimeMBean);
         }

         if (this.isEmpty()) {
            toUnregister.add(this.jdbcPartitionRuntime);
            unsetJDBCPartitionRuntime = true;
            this.jdbcPartitionRuntime = null;
            remove(this.name);
         }
      }

      if (toUnregister.size() > 0) {
         RuntimeMBeanHelper.getHelper().unregister((Collection)toUnregister);
      }

      if (unsetJDBCPartitionRuntime && this.partitionRuntime != null) {
         this.partitionRuntime.setJDBCPartitionRuntime((JDBCPartitionRuntimeMBean)null);
      }

      return runtimeMBean;
   }

   private synchronized void createJDBCPartitionRuntimeIfNecessary() throws ManagementException {
      if (this.jdbcPartitionRuntime == null) {
         if (this.partitionRuntime == null) {
            ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
            this.partitionRuntime = serverRuntime.lookupPartitionRuntime(this.name);
            if (this.partitionRuntime == null) {
               JDBCLogger.logErrorMessage("unable to look up PartitionRuntimeMBean for partition " + this.name + " while attempting to initialize JDBCPartitionRuntime");
               return;
            }
         }

         this.jdbcPartitionRuntime = RuntimeMBeanHelper.getHelper().createJDBCPartitionRuntimeMBean(this.partitionRuntime.getName(), this.partitionRuntime, this);
         this.partitionRuntime.setJDBCPartitionRuntime(this.jdbcPartitionRuntime);
      }

   }

   private synchronized void createJDBCPartitionRuntimeIfNecessary(RuntimeMBean runtimeMBean) throws ManagementException {
      if (this.jdbcPartitionRuntime == null && this.partitionRuntime == null) {
         WebLogicMBean parent = runtimeMBean.getParent();
         if (parent instanceof PartitionRuntimeMBean) {
            this.partitionRuntime = (PartitionRuntimeMBean)parent;
            this.jdbcPartitionRuntime = RuntimeMBeanHelper.getHelper().createJDBCPartitionRuntimeMBean(this.partitionRuntime.getName(), this.partitionRuntime, this);
            this.partitionRuntime.setJDBCPartitionRuntime(this.jdbcPartitionRuntime);
         } else {
            JDBCLogger.logErrorMessage("Partition runtime does not exist when creating runtime MBean for datasource " + runtimeMBean.getName() + ".  Not creating JDBCPartitionRuntimeMBean for partition " + this.name);
         }
      }

   }

   public String toString() {
      return "JDBCPartition: name=" + this.name + ", partitionRuntime=" + this.partitionRuntime + ", jdbcPartitionRuntime=" + this.jdbcPartitionRuntime;
   }

   static JDBCPartition getOrCreate(String partitionName) throws ManagementException {
      JDBCPartition runtime = null;
      synchronized(jdbcPartitions) {
         runtime = (JDBCPartition)jdbcPartitions.get(partitionName);
         if (runtime == null) {
            runtime = new JDBCPartition(partitionName);
            jdbcPartitions.put(partitionName, runtime);
            if (KernelStatus.isServer()) {
               runtime.createJDBCPartitionRuntimeIfNecessary();
            }
         }

         return runtime;
      }
   }

   static JDBCPartition get(String partitionName) {
      synchronized(jdbcPartitions) {
         return (JDBCPartition)jdbcPartitions.get(partitionName);
      }
   }

   static JDBCPartition remove(String partitionName) throws ManagementException {
      JDBCPartition jdbcPartition = null;
      synchronized(jdbcPartitions) {
         jdbcPartition = (JDBCPartition)jdbcPartitions.remove(partitionName);
         return jdbcPartition;
      }
   }

   static void deletePartitions() throws ManagementException {
      synchronized(jdbcPartitions) {
         Iterator var1 = jdbcPartitions.keySet().iterator();

         while(var1.hasNext()) {
            String partitionName = (String)var1.next();
            JDBCPartition jdbcPartition = remove(partitionName);
            jdbcPartition.cleanup();
         }

      }
   }
}
