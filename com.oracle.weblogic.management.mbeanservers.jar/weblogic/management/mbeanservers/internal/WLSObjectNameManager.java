package weblogic.management.mbeanservers.internal;

import java.beans.BeanInfo;
import java.security.AccessController;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.commo.StandardInterface;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.WLSObjectNameBuilder;
import weblogic.management.jmx.ObjectNameManagerBase;
import weblogic.management.jmx.modelmbean.WLSModelMBeanInstanceContext;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.Service;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLSObjectNameManager extends ObjectNameManagerBase {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   public static final String BEA_DOMAIN_NAME = "com.bea";
   private final String domainName;
   private final BeanInfoAccess beanInfoAccess;
   private final WLSObjectNameBuilder objectNameBuilder;
   private boolean addDomainToReadOnly = false;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void setAddDomainToReadOnly(boolean addDomainToReadOnly) {
      this.addDomainToReadOnly = addDomainToReadOnly;
   }

   public WLSObjectNameManager(String domainName) {
      super(DescriptorBean.class);
      this.domainName = domainName;
      this.beanInfoAccess = ManagementService.getBeanInfoAccess();
      this.objectNameBuilder = new WLSObjectNameBuilder(domainName);
   }

   public boolean isClassMapped(Class clazz) {
      if (clazz.isPrimitive()) {
         return false;
      } else if (clazz == String.class) {
         return false;
      } else if (Service.class.isAssignableFrom(clazz)) {
         return true;
      } else if (StandardInterface.class.isAssignableFrom(clazz)) {
         return true;
      } else if (WebLogicMBean.class.isAssignableFrom(clazz)) {
         return true;
      } else if (DescriptorBean.class.isAssignableFrom(clazz)) {
         return true;
      } else if (this.beanInfoAccess.hasBeanInfo(clazz)) {
         BeanInfo beanInfo = this.beanInfoAccess.getBeanInfoForInterface(clazz.getName(), false, (String)null);
         Boolean valueObject = (Boolean)beanInfo.getBeanDescriptor().getValue("valueObject");
         return valueObject == null || !valueObject;
      } else {
         return false;
      }
   }

   protected String getDomainName() {
      return this.domainName;
   }

   public ObjectName lookupObjectName(Object instance) {
      ObjectName result = super.lookupObjectName(instance);
      if (result != null) {
         return result;
      } else {
         result = this.buildObjectName(instance);
         if (result == null) {
            throw new Error("Unable to build an ObjectName for the instance " + instance + " of class " + (instance == null ? "null" : instance.getClass().getName()));
         } else {
            super.registerObject(result, instance);
            if (debug.isDebugEnabled()) {
               String instanceStr;
               if (instance == null) {
                  instanceStr = "null";
               } else {
                  instanceStr = instance.getClass().getName() + "{" + instance.hashCode() + "}";
               }

               debug.debug("WLSObjectNameManager.lookupObjectName: registered in " + this + ":" + result + ":" + instanceStr);
            }

            return result;
         }
      }
   }

   public ObjectName buildObjectName(Object instance, WLSModelMBeanInstanceContext instanceContext) {
      this.objectNameBuilder.setAddDomainToReadOnly(this.addDomainToReadOnly);
      return this.objectNameBuilder.buildObjectName(instance, instanceContext);
   }

   static boolean isBEADomain(String domainName) {
      return "com.bea".equals(domainName);
   }

   public static ObjectName buildWLSObjectName(Object instance) {
      String domainName = ManagementService.getRuntimeAccess(kernelId).getDomainName();
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      ObjectName result = null;
      Hashtable table = new Hashtable();
      WebLogicMBean bean = (WebLogicMBean)instance;
      String type = bean.getType();
      boolean isConfigTree = false;
      if (bean instanceof ConfigurationMBean) {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         if (!configBean.isEditable()) {
            type = type + "Config";
            isConfigTree = true;
         }
      }

      table.put("Type", type);
      String name = bean.getName();

      for(WebLogicMBean beanForName = bean.getParent(); name == null && beanForName != null; beanForName = beanForName.getParent()) {
         name = beanForName.getName();
      }

      table.put("Name", name);
      if (isConfigTree || bean instanceof RuntimeMBean) {
         table.put("Location", serverName);
      }

      for(WebLogicMBean parent = bean.getParent(); parent != null && !(parent instanceof DomainMBean); parent = parent.getParent()) {
         String parentType = isConfigTree ? parent.getType() + "Config" : parent.getType();
         table.put(parentType, parent.getName());
      }

      try {
         if (type.equals("AdminServer")) {
            domainName = "weblogic";
         }

         result = new WebLogicObjectName(domainName, quoteObjectNameEntries(table));
         return result;
      } catch (MalformedObjectNameException var12) {
         throw new AssertionError("There is problem in constructing ObjectName " + var12);
      }
   }
}
