package weblogic.management.jmx;

import java.beans.BeanInfo;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.jmx.modelmbean.WLSModelMBeanInstanceContext;
import weblogic.management.provider.Service;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.core.ManagementCoreService;

public class JMXRuntimeObjectNameManager extends ObjectNameManagerBase {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   private static final String BEA_DOMAIN_NAME = "com.bea";
   private final BeanInfoAccess beanInfoAccess = ManagementCoreService.getBeanInfoAccess();

   public JMXRuntimeObjectNameManager() {
      super(DescriptorBean.class);
   }

   public boolean isClassMapped(Class clazz) {
      if (clazz.isPrimitive()) {
         return false;
      } else if (clazz == String.class) {
         return false;
      } else if (Service.class.isAssignableFrom(clazz)) {
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

               debug.debug("JMXRuntimeObjectNameManager.lookupObjectName: registered in " + this + ":" + result + ":" + instanceStr);
            }

            return result;
         }
      }
   }

   public ObjectName buildObjectName(Object instance, WLSModelMBeanInstanceContext instanceContext) {
      ObjectName result = null;
      if (instance instanceof WebLogicMBean) {
         result = this.lookupWebLogicObjectName(instance);
      } else if (instance instanceof Service) {
         result = this.lookupServiceObjectName((Service)instance);
      }

      return result;
   }

   private ObjectName lookupServiceObjectName(Service service) {
      ObjectName result = null;
      Hashtable table = new Hashtable();
      String type = service.getType();
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
         result = new ObjectName("com.bea", quoteObjectNameEntries(table));
         return result;
      } catch (MalformedObjectNameException var8) {
         throw new RuntimeException(var8);
      }
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

         for(WebLogicMBean parent = bean.getParent(); parent != null; parent = parent.getParent()) {
            table.put(parent.getType(), parent.getName());
         }

         try {
            result = new ObjectName("com.bea", quoteObjectNameEntries(table));
            return result;
         } catch (MalformedObjectNameException var9) {
            throw new AssertionError("There is problem in constructing ObjectName " + var9);
         }
      }
   }

   String getShortName(DescriptorBean beanForName) {
      return beanForName instanceof WebLogicMBean ? ((WebLogicMBean)beanForName).getName() : null;
   }

   String getShortType(DescriptorBean bean) {
      return bean instanceof WebLogicMBean ? ((WebLogicMBean)bean).getType() : null;
   }
}
