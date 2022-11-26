package weblogic.management.jmx;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.OperationsException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.RegistrationManager;
import weblogic.management.provider.Service;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.beaninfo.BeanInfoAccessFactory;
import weblogic.management.provider.beaninfo.BeanInfoRegistration;
import weblogic.management.provider.core.ManagementCoreService;
import weblogic.management.provider.core.RegistrationManagerBase;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.RuntimeMBeanHelper;

public class ManagementJMXRuntime implements RuntimeMBeanHelper, RegistrationHandler {
   private static ManagementJMXRuntime singleton;
   private MBeanServer mbeanServer;
   private RuntimeMBean defaultParentMBean;
   private RegistrationManager registrationManager;
   private WLSModelMBeanContext mbeanContext;

   public static synchronized void initialize(MBeanServer mbeanServer, RuntimeMBean defaultParentMBean) throws ManagementException {
      if (singleton == null) {
         singleton = new ManagementJMXRuntime(mbeanServer, defaultParentMBean);
      }

   }

   private ManagementJMXRuntime(MBeanServer mbeanServer, RuntimeMBean defaultParentMBean) throws ManagementException {
      this.mbeanServer = mbeanServer;
      this.defaultParentMBean = defaultParentMBean;
      BeanInfoAccess beanInfoAccess = ManagementCoreService.getBeanInfoAccess();
      if (beanInfoAccess == null) {
         beanInfoAccess = ManagementJMXRuntime.BEANINFOACCESSSINGLETON.instance;
         ManagementCoreService.initializeBeanInfo(beanInfoAccess);
         this.mbeanContext = new WLSModelMBeanContext(mbeanServer, new JMXRuntimeObjectNameManager(), (ObjectSecurityManager)null);
         this.registrationManager = new RegistrationManagerBase();
         this.registrationManager.addRegistrationHandler(this);
         RuntimeMBeanDelegate.setRuntimeMBeanHelper(this);
      }
   }

   public String getServerName() {
      return null;
   }

   public RuntimeMBean getDefaultParent() {
      return this.defaultParentMBean;
   }

   public RegistrationManager getRegistrationManager() {
      return this.registrationManager;
   }

   public boolean isParentRequired(RuntimeMBean mbean) {
      if (this.defaultParentMBean == null) {
         return false;
      } else {
         return mbean == null || !mbean.getClass().equals(this.defaultParentMBean.getClass());
      }
   }

   public boolean isParentRequired(String mbeanType) {
      if (this.defaultParentMBean == null) {
         return false;
      } else {
         return mbeanType == null || !mbeanType.equals(this.defaultParentMBean.getClass().getName());
      }
   }

   public void registeredCustom(ObjectName oname, Object custom) {
      try {
         if (this.mbeanContext.getNameManager().isClassMapped(custom.getClass())) {
            this.mbeanContext.getNameManager().registerObject(oname, custom);
            WLSModelMBeanFactory.registerWLSModelMBean(custom, oname, this.mbeanContext);
         } else {
            this.mbeanContext.getMBeanServer().registerMBean(custom, oname);
         }
      } catch (OperationsException var4) {
         JMXLogger.logRegistrationFailed(oname, var4);
      } catch (MBeanRegistrationException var5) {
         JMXLogger.logRegistrationFailed(oname, var5);
      }

   }

   public void unregisteredCustom(ObjectName oname) {
      Object custom = this.mbeanContext.getNameManager().lookupObject(oname);
      if (custom != null) {
         this.mbeanContext.unregister(custom);
      } else {
         try {
            this.mbeanContext.getMBeanServer().unregisterMBean(oname);
         } catch (InstanceNotFoundException var4) {
            JMXLogger.logUnregisterFailed(oname, var4);
         } catch (MBeanRegistrationException var5) {
            JMXLogger.logUnregisterFailed(oname, var5);
         }
      }

   }

   public void registered(RuntimeMBean runtime, DescriptorBean config) {
      WLSModelMBeanFactory.registerWLSModelMBean(runtime, this.mbeanContext);
   }

   public void unregistered(RuntimeMBean runtime) {
      this.unregisteredInternal(runtime);
   }

   public void registered(Service bean) {
      WLSModelMBeanFactory.registerWLSModelMBean(bean, this.mbeanContext);
   }

   public void unregistered(Service bean) {
      this.unregisteredInternal(bean);
   }

   private void unregisteredInternal(Object bean) {
      if (this.mbeanContext.getNameManager().isClassMapped(bean.getClass())) {
         ObjectName oname = this.mbeanContext.getNameManager().unregisterObjectInstance(bean);
         if (oname == null) {
            JMXLogger.logBeanUnregisterFailed(bean.toString());
         } else {
            try {
               this.mbeanContext.getMBeanServer().unregisterMBean(oname);
            } catch (InstanceNotFoundException var4) {
            } catch (MBeanRegistrationException var5) {
               JMXLogger.logUnregisterFailed(oname, var5);
            }

         }
      }
   }

   private static class BEANINFOACCESSSINGLETON {
      static BeanInfoAccess instance = BeanInfoAccessFactory.getBeanInfoAccess();

      static {
         BeanInfoRegistration beanInfoReg = BeanInfoAccessFactory.getBeanInfoRegistration();
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.runtime.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.discoverBeanInfoFactories(Thread.currentThread().getContextClassLoader());
      }
   }
}
