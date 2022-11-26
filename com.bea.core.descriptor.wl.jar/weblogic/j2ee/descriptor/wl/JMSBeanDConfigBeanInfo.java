package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JMSBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JMSBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Version", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getVersion", "setVersion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Notes", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getNotes", "setNotes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Quotas", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getQuotas", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Templates", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getTemplates", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DestinationKeys", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getDestinationKeys", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionFactories", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getConnectionFactories", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ForeignServers", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getForeignServers", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Queues", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getQueues", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Topics", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getTopics", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DistributedQueues", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getDistributedQueues", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DistributedTopics", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getDistributedTopics", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UniformDistributedQueues", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getUniformDistributedQueues", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UniformDistributedTopics", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getUniformDistributedTopics", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFImportedDestinations", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getSAFImportedDestinations", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFRemoteContexts", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getSAFRemoteContexts", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFErrorHandlings", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getSAFErrorHandlings", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondaryDescriptors", Class.forName("weblogic.j2ee.descriptor.wl.JMSBeanDConfig"), "getSecondaryDescriptors", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for JMSBeanDConfigBeanInfo");
         }
      }
   }
}
