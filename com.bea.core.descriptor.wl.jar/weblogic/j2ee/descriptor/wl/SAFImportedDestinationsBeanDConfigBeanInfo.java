package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SAFImportedDestinationsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SAFImportedDestinationsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("SAFQueues", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getSAFQueues", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFTopics", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getSAFTopics", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIPrefix", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getJNDIPrefix", "setJNDIPrefix");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFRemoteContext", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getSAFRemoteContext", "setSAFRemoteContext");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFErrorHandling", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getSAFErrorHandling", "setSAFErrorHandling");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeToLiveDefault", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getTimeToLiveDefault", "setTimeToLiveDefault");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseSAFTimeToLiveDefault", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "isUseSAFTimeToLiveDefault", "setUseSAFTimeToLiveDefault");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UnitOfOrderRouting", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getUnitOfOrderRouting", "setUnitOfOrderRouting");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageLoggingParams", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getMessageLoggingParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ExactlyOnceLoadBalancingPolicy", Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanDConfig"), "getExactlyOnceLoadBalancingPolicy", "setExactlyOnceLoadBalancingPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for SAFImportedDestinationsBeanDConfigBeanInfo");
         }
      }
   }
}
