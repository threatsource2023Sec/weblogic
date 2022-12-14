package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicEjbJarBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicEjbJarBeanDConfig.class);
   static PropertyDescriptor[] pds = null;

   public BeanDescriptor getBeanDescriptor() {
      return this.bd;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      if (pds != null) {
         return pds;
      } else {
         List plist = new ArrayList();

         try {
            PropertyDescriptor pd = new PropertyDescriptor("Description", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getDescription", "setDescription");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("WeblogicEnterpriseBeans", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getWeblogicEnterpriseBeans", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecurityRoleAssignments", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getSecurityRoleAssignments", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RunAsRoleAssignments", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getRunAsRoleAssignments", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecurityPermission", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getSecurityPermission", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionIsolations", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getTransactionIsolations", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageDestinationDescriptors", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getMessageDestinationDescriptors", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IdempotentMethods", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getIdempotentMethods", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SkipStateReplicationMethods", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getSkipStateReplicationMethods", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RetryMethodsOnRollbacks", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getRetryMethodsOnRollbacks", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableBeanClassRedeploy", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "isEnableBeanClassRedeploy", "setEnableBeanClassRedeploy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TimerImplementation", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getTimerImplementation", "setTimerImplementation");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DisableWarnings", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getDisableWarnings", "setDisableWarnings");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("WorkManagers", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getWorkManagers", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ManagedExecutorServices", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getManagedExecutorServices", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ManagedScheduledExecutorServices", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getManagedScheduledExecutorServices", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ManagedThreadFactories", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getManagedThreadFactories", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ComponentFactoryClassName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getComponentFactoryClassName", "setComponentFactoryClassName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("WeblogicCompatibility", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getWeblogicCompatibility", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getId", "setId");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CoherenceClusterRef", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getCoherenceClusterRef", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Version", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getVersion", "setVersion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CdiDescriptor", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getCdiDescriptor", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondaryDescriptors", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig"), "getSecondaryDescriptors", (String)null);
            pd.setValue("dependency", Boolean.FALSE);
            pd.setValue("declaration", Boolean.FALSE);
            pd.setValue("configurable", Boolean.FALSE);
            pd.setValue("key", Boolean.FALSE);
            pd.setValue("dynamic", Boolean.FALSE);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicEjbJarBeanDConfigBeanInfo");
         }
      }
   }
}
