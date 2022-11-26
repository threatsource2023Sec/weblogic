package weblogic.management.internal;

import java.security.AccessController;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.WebLogicMBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.jmx.ObjectNameManagerBase;
import weblogic.management.jmx.modelmbean.WLSModelMBeanInstanceContext;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.Service;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLSObjectNameBuilder {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   public static final String BEA_DOMAIN_NAME = "com.bea";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String domainName;
   private final BeanInfoAccess beanInfoAccess;
   private boolean addDomainToReadOnly = false;

   public void setAddDomainToReadOnly(boolean addDomainToReadOnly) {
      this.addDomainToReadOnly = addDomainToReadOnly;
   }

   public WLSObjectNameBuilder(String domainName) {
      this.domainName = domainName;
      this.beanInfoAccess = ManagementService.getBeanInfoAccess();
   }

   public ObjectName buildObjectName(Object instance) {
      return this.buildObjectName(instance, (WLSModelMBeanInstanceContext)null);
   }

   public ObjectName buildObjectName(Object instance, WLSModelMBeanInstanceContext instanceContext) {
      ObjectName result = null;
      if (instance instanceof WebLogicMBean) {
         result = this.lookupWebLogicObjectName(instance);
      } else {
         if (instance instanceof StandardInterface) {
            return buildCommoObjectName(instance);
         }

         if (instance instanceof AbstractDescriptorBean) {
            result = this.lookupDescriptorObjectName((AbstractDescriptorBean)instance, instanceContext);
         } else if (instance instanceof Service) {
            result = this.lookupServiceObjectName((Service)instance);
         }
      }

      return result;
   }

   private ObjectName lookupServiceObjectName(Service service) {
      ObjectName result = null;
      Hashtable table = new Hashtable();
      String type = service.getType();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals("DOMAIN") && cic.getApplicationId() != null) {
         table.put("Partition", cic.getPartitionName());
      }

      if (type != null) {
         table.put("Type", service.getType());
      }

      String name = service.getName();
      if (name != null) {
         table.put("Name", service.getName());
      }

      Service parent = service.getParentService();
      if (parent != null) {
         table.put("Path", service.getPath());
      }

      try {
         result = new ObjectName("com.bea", ObjectNameManagerBase.quoteObjectNameEntries(table));
         return result;
      } catch (MalformedObjectNameException var9) {
         throw new RuntimeException(var9);
      }
   }

   private ObjectName lookupDescriptorObjectName(AbstractDescriptorBean instance, WLSModelMBeanInstanceContext instanceContext) {
      ObjectName result = null;
      Hashtable table = new Hashtable();
      AbstractDescriptorBean rootBean = (AbstractDescriptorBean)instance.getDescriptor().getRootBean();
      DescriptorImpl descriptor = (DescriptorImpl)instance.getDescriptor();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals("DOMAIN")) {
         table.put("Partition", cic.getPartitionName());
      }

      AbstractDescriptorBean parentDescriptorBean = (AbstractDescriptorBean)descriptor.getContext().get("DescriptorConfigExtension");
      String parentDescriptorAttribute = (String)descriptor.getContext().get("DescriptorConfigExtensionAttribute");
      table.put("Parent", parentDescriptorBean._getQualifiedName());
      Object instanceKey = instance._getKey();
      Object key = instanceKey;

      for(AbstractDescriptorBean parentBean = (AbstractDescriptorBean)instance.getParentBean(); key == null && parentBean != null; parentBean = (AbstractDescriptorBean)parentBean.getParentBean()) {
         key = parentBean._getKey();
      }

      if (key == null && parentDescriptorBean != null) {
         key = parentDescriptorBean._getKey();
      }

      if (key != null) {
         table.put("Name", key.toString());
      }

      Object rootKey = rootBean._getKey();
      if (rootKey == null) {
         rootKey = parentDescriptorBean._getKey();
      }

      String rootKeyString = rootKey.toString();
      StringBuffer pathBuffer = new StringBuffer(parentDescriptorAttribute);
      String qualifiedName = instance._getQualifiedName();
      if (qualifiedName != null && qualifiedName.length() > 0) {
         if (!qualifiedName.startsWith("[")) {
            pathBuffer.append("[");
            pathBuffer.append(rootKeyString);
            pathBuffer.append("]");
         }

         pathBuffer.append(qualifiedName);
      }

      if (instanceContext != null && instanceKey == null && descriptor.getContext().get("ApplicationName") != null) {
         pathBuffer.append("[");
         pathBuffer.append(instanceContext.getArrayIndex());
         pathBuffer.append("]");
      }

      table.put("Path", pathBuffer.toString());
      table.put("Type", this.beanInfoAccess.getInterfaceForInstance(instance).getName());
      if (this.addDomainToReadOnly && instance instanceof AbstractDescriptorBean && !instance.getDescriptor().isEditable()) {
         table.put("Location", this.domainName);
      }

      String appName = (String)descriptor.getContext().get("ApplicationName");
      if (appName != null) {
         table.put("ApplicationName", appName);
      }

      String moduleName = (String)descriptor.getContext().get("ModuleName");
      if (moduleName != null) {
         table.put("ModuleName", moduleName);
      }

      String moduleType = (String)descriptor.getContext().get("ModuleType");
      if (moduleType != null) {
         table.put("ModuleType", moduleType);
      }

      try {
         result = new ObjectName("com.bea", ObjectNameManagerBase.quoteObjectNameEntries(table));
      } catch (MalformedObjectNameException var21) {
         return null;
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Created new ObjectName =>" + result.getCanonicalName());
      }

      return result;
   }

   private ObjectName lookupWebLogicObjectName(Object instance) {
      ObjectName result = null;
      Hashtable table = new Hashtable();
      WebLogicMBean bean = (WebLogicMBean)instance;
      table.put("Type", bean.getType());
      String name = bean.getName();

      for(WebLogicMBean beanForName = bean.getParent(); name == null && beanForName != null; beanForName = beanForName.getParent()) {
         name = beanForName.getName();
      }

      if (name == null) {
         throw new Error("Unable to determine name for bean " + instance);
      } else {
         table.put("Name", name);
         if (this.addDomainToReadOnly && instance instanceof AbstractDescriptorBean) {
            AbstractDescriptorBean descriptorBean = (AbstractDescriptorBean)instance;
            if (!descriptorBean.getDescriptor().isEditable()) {
               table.put("Location", this.domainName);
            }
         }

         for(WebLogicMBean parent = bean.getParent(); parent != null && !(parent instanceof DomainMBean) && !(parent instanceof DomainRuntimeMBean); parent = parent.getParent()) {
            table.put(parent.getType(), parent.getName());
         }

         try {
            result = new ObjectName("com.bea", ObjectNameManagerBase.quoteObjectNameEntries(table));
            return result;
         } catch (MalformedObjectNameException var9) {
            throw new AssertionError("There is problem in constructing ObjectName " + var9);
         }
      }
   }

   String getShortName(DescriptorBean beanForName) {
      if (beanForName instanceof WebLogicMBean) {
         return ((WebLogicMBean)beanForName).getName();
      } else {
         return beanForName instanceof StandardInterface ? ((StandardInterface)beanForName).getName() : null;
      }
   }

   String getShortType(DescriptorBean bean) {
      if (bean instanceof WebLogicMBean) {
         return ((WebLogicMBean)bean).getType();
      } else {
         return bean instanceof StandardInterface ? ((StandardInterface)bean).wls_getInterfaceClassName() : null;
      }
   }

   private static ObjectName buildCommoObjectName(Object instance) {
      return ((StandardInterface)instance).wls_getObjectName();
   }
}
