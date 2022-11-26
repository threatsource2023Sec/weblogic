package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.sql.DataSource;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCPartitionRuntimeMBean;
import weblogic.management.runtime.JDBCProxyDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCUCPDataSourceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class RuntimeMBeanHelperImpl extends RuntimeMBeanHelper {
   private static final AuthenticatedSubject KERNELID = getKernelID();

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   RuntimeMBean getParent(JDBCDataSourceBean dsBean, String appName) {
      return this.getParent(dsBean, appName, false);
   }

   RuntimeMBean getParent(JDBCDataSourceBean dsBean, String appName, boolean sharedPool) {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNELID);
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      if (partitionName != null && !sharedPool) {
         RuntimeMBean partition = runtime.getServerRuntime().lookupPartitionRuntime(partitionName);
         if (partition instanceof PartitionRuntimeMBean) {
            return ((PartitionRuntimeMBean)partition).getJDBCPartitionRuntime();
         }
      }

      return runtime.getServerRuntime().lookupApplicationRuntime(appName);
   }

   JDBCDataSourceRuntimeMBean createDataSourceRuntimeMBean(final JDBCConnectionPool pool, final ResourcePoolGroup group, final String qname, final JDBCDataSourceBean dsBean, String appName, String sharedPoolName) throws ManagementException {
      final RuntimeMBean parent = this.getParent(dsBean, appName, sharedPoolName != null);
      final RuntimeMBean restParent = this.getRestParent(dsBean, appName);
      DataSourceRuntimeMBeanImpl mbean = null;
      if (sharedPoolName != null) {
         ComponentInvocationContext globalCIC = ComponentInvocationContextManager.getInstance(KERNELID).createComponentInvocationContext("DOMAIN");

         try {
            mbean = (DataSourceRuntimeMBeanImpl)ComponentInvocationContextManager.runAs(KERNELID, globalCIC, new Callable() {
               public DataSourceRuntimeMBeanImpl call() throws Exception {
                  return new DataSourceRuntimeMBeanImpl(pool, group, qname, parent, restParent, (DescriptorBean)dsBean);
               }
            });
         } catch (ExecutionException var12) {
            throw new ManagementException(var12);
         }
      } else {
         mbean = new DataSourceRuntimeMBeanImpl(pool, group, qname, parent, restParent, (DescriptorBean)dsBean);
      }

      return mbean;
   }

   JDBCOracleDataSourceRuntimeMBean createHADataSourceRuntimeMBean(final JDBCConnectionPool pool, final ResourcePoolGroup group, final String instanceGroupCategory, final String qname, final JDBCDataSourceBean dsBean, String appName, String sharedPoolName) throws ManagementException {
      final RuntimeMBean parent = this.getParent(dsBean, appName, sharedPoolName != null);
      final RuntimeMBean restParent = this.getRestParent(dsBean, appName);
      OracleDataSourceRuntimeImpl mbean = null;
      if (sharedPoolName != null) {
         ComponentInvocationContext globalCIC = ComponentInvocationContextManager.getInstance(KERNELID).createComponentInvocationContext("DOMAIN");

         try {
            mbean = (OracleDataSourceRuntimeImpl)ComponentInvocationContextManager.runAs(KERNELID, globalCIC, new Callable() {
               public OracleDataSourceRuntimeImpl call() throws Exception {
                  return new OracleDataSourceRuntimeImpl(pool, group, instanceGroupCategory, qname, parent, restParent, (DescriptorBean)dsBean);
               }
            });
         } catch (ExecutionException var13) {
            throw new ManagementException(var13);
         }
      } else {
         mbean = new OracleDataSourceRuntimeImpl(pool, group, instanceGroupCategory, qname, parent, restParent, (DescriptorBean)dsBean);
      }

      return mbean;
   }

   JDBCMultiDataSourceRuntimeMBean createMultiDataSourceRuntimeMBean(MultiPool multiPool, String qname, JDBCDataSourceBean dsBean, String appName) throws ManagementException {
      RuntimeMBean parent = this.getParent(dsBean, appName);
      RuntimeMBean restParent = this.getRestParent(dsBean, appName);
      MultiDataSourceRuntimeMBeanImpl mbean = new MultiDataSourceRuntimeMBeanImpl(multiPool, qname, parent, restParent, (DescriptorBean)dsBean);
      return mbean;
   }

   JDBCProxyDataSourceRuntimeMBean createProxyDataSourceRuntimeMBean(DataSource proxyds, String qname, JDBCDataSourceBean dsBean, String appName) throws ManagementException {
      RuntimeMBean parent = this.getParent(dsBean, appName);
      RuntimeMBean restParent = this.getRestParent(dsBean, appName);
      ProxyDataSourceRuntimeImpl mbean = new ProxyDataSourceRuntimeImpl(proxyds, qname, parent, restParent, (DescriptorBean)dsBean);
      return mbean;
   }

   JDBCUCPDataSourceRuntimeMBean createUCPDataSourceRuntimeMBean(DataSource ucpds, String qname, JDBCDataSourceBean dsBean, String appName) throws ManagementException {
      RuntimeMBean parent = this.getParent(dsBean, appName);
      RuntimeMBean restParent = this.getRestParent(dsBean, appName);
      UCPDataSourceRuntimeImpl mbean = new UCPDataSourceRuntimeImpl(ucpds, qname, parent, restParent, (DescriptorBean)dsBean);
      return mbean;
   }

   void unregister(RuntimeMBean runtimeMBean) throws ManagementException {
      if (runtimeMBean != null) {
         try {
            if (!(runtimeMBean instanceof RuntimeMBeanDelegate)) {
               throw new ManagementException("unable to unregister mbean " + runtimeMBean + " because it is not a RuntimeMBeanDelegate");
            } else {
               final RuntimeMBeanDelegate rtmbd = (RuntimeMBeanDelegate)runtimeMBean;
               SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     rtmbd.unregister();
                     return null;
                  }
               });
            }
         } catch (PrivilegedActionException var3) {
            throw new ManagementException(var3.toString());
         }
      }
   }

   JDBCPartitionRuntimeMBean createJDBCPartitionRuntimeMBean(String name, PartitionRuntimeMBean parent, JDBCPartition jdbcPartition) throws ManagementException {
      return new JDBCPartitionRuntimeImpl(name, parent, jdbcPartition);
   }

   JDBCDriverRuntimeMBean createDriverRuntimeMBean(String name) throws ManagementException {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNELID);
      RuntimeMBean restParent = runtime.getServerRuntime().getJDBCServiceRuntime();
      DriverRuntimeMBeanImpl driverRTMBean = new DriverRuntimeMBeanImpl(name, restParent);
      return driverRTMBean;
   }

   String getDriverRuntimeMBeanName(String driverName) {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNELID);
      String mbeanName = runtime.getDomainName() + "_" + runtime.getServerName() + "_" + driverName;
      return mbeanName;
   }

   RuntimeMBean getRestParent(JDBCDataSourceBean dsBean, String appName) {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      if (partitionName != null) {
         return null;
      } else {
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNELID);
         RuntimeMBean appRuntime = runtime.getServerRuntime().lookupApplicationRuntime(appName);
         return (RuntimeMBean)(appRuntime != null ? appRuntime : runtime.getServerRuntime().getJDBCServiceRuntime());
      }
   }
}
