package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SAFDestinationBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SAFDestinationBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("RemoteJNDIName", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getRemoteJNDIName", "setRemoteJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LocalJNDIName", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getLocalJNDIName", "setLocalJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PersistentQos", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getPersistentQos", "setPersistentQos");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NonPersistentQos", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getNonPersistentQos", "setNonPersistentQos");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SAFErrorHandling", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getSAFErrorHandling", "setSAFErrorHandling");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeToLiveDefault", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getTimeToLiveDefault", "setTimeToLiveDefault");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UseSAFTimeToLiveDefault", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "isUseSAFTimeToLiveDefault", "setUseSAFTimeToLiveDefault");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UnitOfOrderRouting", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getUnitOfOrderRouting", "setUnitOfOrderRouting");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageLoggingParams", Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanDConfig"), "getMessageLoggingParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for SAFDestinationBeanDConfigBeanInfo");
         }
      }
   }
}
