package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.mbean.typing.MBeanTypeUtil;
import com.bea.adaptive.mbean.typing.RegistrationEventHandler;
import com.bea.adaptive.mbean.typing.InstanceRegistrationEvent.EventType;
import java.util.concurrent.Executor;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.Service;
import weblogic.management.runtime.RuntimeMBean;

class BeanTreeRegistrationHandler extends RegistrationEventHandler implements RegistrationHandler {
   static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvesterTreeBeanPlugin");
   public static final String CATEGORY_NAME = "WLS-MBean";

   public BeanTreeRegistrationHandler(String name, MBeanTypeUtil.RegHandler regHandler, String[] patterns, Executor exec) {
      super(name, regHandler, patterns, exec);
   }

   public void registered(RuntimeMBean runtimeMBean, DescriptorBean descBean) {
      this.enqueueAdd(runtimeMBean);
   }

   public void registered(Service bean) {
      this.enqueueAdd(bean);
   }

   public void registeredCustom(ObjectName arg0, Object arg1) {
   }

   public void unregistered(RuntimeMBean mbean) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(this.getMessagePrefix() + "Processing unregistration for " + mbean.getName());
      }

      this.enqueueDelete(mbean);
   }

   public void unregistered(Service bean) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(this.getMessagePrefix() + "Processing unregistration for " + bean.getName());
      }

      this.enqueueDelete(bean);
   }

   public void unregisteredCustom(ObjectName arg0) {
   }

   private void enqueueAdd(Object bean) {
      try {
         ObjectName instName = TreeBeanHarvestableDataProviderHelper.getObjectNameForInstance(bean);
         this.queueEvent(instName, EventType.ADD);
      } catch (Exception var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(this.getMessagePrefix() + "Exception while registering bean", var3);
         }
      }

   }

   private void enqueueDelete(Object bean) {
      try {
         ObjectName instName = TreeBeanHarvestableDataProviderHelper.getRegisteredObjectNameForInstance(bean);
         if (instName != null) {
            this.queueEvent(instName, EventType.DELETE);
         } else if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(this.getMessagePrefix() + "No ObjectName found for bean " + bean.toString());
         }

         TreeBeanHarvestableDataProviderHelper.unregisterInstance(bean);
      } catch (Exception var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(this.getHandlerName() + ": Exception deleting instance", var3);
         }
      }

   }

   protected void newInstance(ObjectName currentMBean) throws Exception {
      String typeName = TreeBeanHarvestableDataProviderHelper.getTypeNameForInstance(currentMBean);
      if (null == typeName && DEBUG.isDebugEnabled()) {
         DEBUG.debug(this.getMessagePrefix() + "Type name was NULL for instance " + currentMBean.getCanonicalName());
      } else {
         this.getHandler().newInstance(typeName, currentMBean.getCanonicalName(), "WLS-MBean");
      }
   }
}
