package weblogic.work;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.Iterator;
import weblogic.application.ApplicationAccessService;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBean;
import weblogic.j2ee.descriptor.wl.SizeParamsBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JoltConnectionPoolMBean;
import weblogic.management.configuration.MaxThreadsConstraintMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;

final class PoolBasedMaxThreadsConstraint extends MaxThreadsConstraint implements PropertyChangeListener, BeanUpdateListener, DescriptorUpdateListener {
   private static final DebugCategory debugMTC = Debug.getCategory("weblogic.maxthreadsconstraint");
   private static final String JOLT_POOL_TYPE = "JoltConnectionPoolConfig";
   private static final String CONNECTOR_POOL_TYPE = "ConnectorComponentConfig";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   PoolBasedMaxThreadsConstraint(String name, String poolName, PartitionMaxThreadsConstraint partitionMaxThreadsConstraint) throws ManagementException {
      super(name, partitionMaxThreadsConstraint);
      this.setCountUsingBean(poolName);
   }

   PoolBasedMaxThreadsConstraint(String name, String poolName, WeblogicApplicationBean appBean, PartitionMaxThreadsConstraint partitionMaxThreadsConstraint) throws DeploymentException {
      super(name, partitionMaxThreadsConstraint);
      JDBCConnectionPoolBean appscopedBean = this.getAppScopedBean(appBean, poolName);
      if (appscopedBean != null) {
         SizeParamsBean sizeParams = appscopedBean.getPoolParams().getSizeParams();
         this.setCount(sizeParams.getMaxCapacity());
         this.registerForUpdates((DescriptorBean)sizeParams);
      } else {
         this.setCountUsingBean(poolName);
      }

   }

   private JDBCConnectionPoolBean getAppScopedBean(WeblogicApplicationBean appBean, String poolBeanName) {
      debug("looking up data source '" + poolBeanName + ", within " + appBean);
      if (appBean != null && poolBeanName != null) {
         JDBCConnectionPoolBean[] poolbeans = appBean.getJDBCConnectionPools();
         if (poolbeans != null) {
            for(int i = 0; i < poolbeans.length; ++i) {
               debug("got app scoped pool - " + poolbeans[i].getDataSourceJNDIName());
               if (poolBeanName.equalsIgnoreCase(poolbeans[i].getDataSourceJNDIName())) {
                  debug("found a match for app scoped pool - " + poolBeanName);
                  return poolbeans[i];
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void setCountUsingBean(String poolName) throws DeploymentException {
      JDBCSystemResourceMBean sysResource = this.getJDBCSystemResource(poolName);
      if (sysResource != null) {
         this.setCount(sysResource.getJDBCResource().getJDBCConnectionPoolParams().getMaxCapacity());
         sysResource.getResource().getDescriptor().addUpdateListener(this);
      } else {
         JDBCConnectionPoolParamsBean jdbcBean = this.getJDBCConnectionPoolParams(poolName);
         if (jdbcBean != null) {
            this.setCount(jdbcBean.getMaxCapacity());
            this.registerForUpdates((DescriptorBean)jdbcBean);
         } else {
            ConfigurationMBean poolMBean = this.getPoolMBean("ConnectorComponentConfig", poolName);
            if (poolMBean == null) {
               poolMBean = this.getPoolMBean("JoltConnectionPoolConfig", poolName);
            }

            if (poolMBean == null) {
               throw new DeploymentException("Unable to lookup pool '" + poolName + "'. Please check if the pool name is valid and points to a valid data source");
            } else {
               poolMBean.addPropertyChangeListener(this);
               if (poolMBean instanceof JoltConnectionPoolMBean) {
                  this.setCount(((JoltConnectionPoolMBean)poolMBean).getMaximumPoolSize());
               } else if (poolMBean instanceof ConnectorComponentMBean) {
                  this.setCount(((ConnectorComponentMBean)poolMBean).getMaxCapacity());
               }

            }
         }
      }
   }

   private JDBCSystemResourceMBean getJDBCSystemResource(String name) {
      debug("looking up connection pool - " + name);
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (debugMTC.isEnabled()) {
         dumpAllJDBCResources(domain);
      }

      JDBCSystemResourceMBean bean = domain.lookupJDBCSystemResource(name);
      debug("found JDBCSystemResource with name " + name + " - " + bean);
      return bean;
   }

   private JDBCConnectionPoolParamsBean getJDBCConnectionPoolParams(String name) {
      JDBCDataSourceBean bean = getJDBCDataSourceBean(name);
      debug("found jdbc connection pool using JDBCUtil - " + bean + " for name --- " + name);
      return bean != null ? bean.getJDBCConnectionPoolParams() : null;
   }

   private static JDBCDataSourceBean getJDBCDataSourceBean(String poolName) {
      JDBCModuleService moduleService = (JDBCModuleService)LocatorUtilities.getService(JDBCModuleService.class);
      JDBCDataSourceBean[] beans = null;
      beans = moduleService.getJDBCDataSourceBean(poolName);
      return beans != null ? beans[0] : null;
   }

   private ConfigurationMBean getPoolMBean(String type, String name) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      String poolType = type.intern();
      if (poolType == "JoltConnectionPoolConfig") {
         debug("looking up JOLT POOL with name - " + name);
         return domain.lookupJoltConnectionPool(name);
      } else if (poolType == "ConnectorComponentConfig") {
         ApplicationAccessService aas = (ApplicationAccessService)LocatorUtilities.getService(ApplicationAccessService.class);
         String appName = aas.getCurrentApplicationName();
         if (appName == null) {
            return null;
         } else {
            debug("looking up CONNECTOR POOL with name " + name + " in application " + appName);
            ApplicationMBean appBean = domain.lookupApplication(appName);
            return appBean == null ? null : appBean.lookupConnectorComponent(name);
         }
      } else {
         return null;
      }
   }

   private void registerForUpdates(DescriptorBean bean) {
      bean.addBeanUpdateListener(this);
   }

   public void prepareUpdate(DescriptorUpdateEvent event) {
   }

   public void activateUpdate(DescriptorUpdateEvent event) {
      DescriptorDiff diff = event.getDiff();
      Iterator it = diff.iterator();

      while(it.hasNext()) {
         BeanUpdateEvent ev = (BeanUpdateEvent)it.next();
         if (ev.getSource() instanceof JDBCConnectionPoolParamsBean) {
            this.setCount(((JDBCConnectionPoolParamsBean)ev.getSource()).getMaxCapacity());
            debug("Dynamic update of PoolBasedMaxThreadsConstraint " + this.getName() + " to count " + this.getCount());
            break;
         }

         if (ev.getSource() instanceof SizeParamsBean) {
            this.setCount(((SizeParamsBean)ev.getSource()).getMaxCapacity());
            debug("Dynamic update of PoolBasedMaxThreadsConstraint " + this.getName() + " to count " + this.getCount());
            break;
         }
      }

   }

   public void rollbackUpdate(DescriptorUpdateEvent event) {
   }

   public final void prepareUpdate(BeanUpdateEvent event) {
   }

   public final void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof MaxThreadsConstraintMBean) {
         this.setCountInternal(((MaxThreadsConstraintMBean)bean).getCount());
      } else if (bean instanceof MaxThreadsConstraintBean) {
         this.setCountInternal(((MaxThreadsConstraintBean)bean).getCount());
      }

   }

   public final void rollbackUpdate(BeanUpdateEvent event) {
   }

   public void propertyChange(PropertyChangeEvent event) {
      if (event != null) {
         String attName = event.getPropertyName();
         if (attName.equals("MaxCapacity") || attName.equals("MaximumPoolSize")) {
            int state = (Integer)event.getNewValue();
            this.setCount(state);
         }
      }
   }

   private static void dumpAllJDBCResources(DomainMBean domain) {
      if (debugMTC.isEnabled()) {
         JDBCSystemResourceMBean[] beans = domain.getJDBCSystemResources();
         if (beans != null && beans.length != 0) {
            for(int count = 0; count < beans.length; ++count) {
               debug("found JDBCSystemResource - " + beans[count].getName());
            }
         } else {
            debug("There are no JDBCSystemResources in the configuration !");
         }
      }

   }

   private static void debug(String str) {
      if (debugMTC.isEnabled()) {
         WorkManagerLogger.logDebug("<WM_MaxTC>" + str);
      }

   }
}
