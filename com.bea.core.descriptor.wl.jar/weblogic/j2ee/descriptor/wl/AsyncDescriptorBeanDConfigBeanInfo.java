package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class AsyncDescriptorBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(AsyncDescriptorBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("TimeoutSecs", Class.forName("weblogic.j2ee.descriptor.wl.AsyncDescriptorBeanDConfig"), "getTimeoutSecs", "setTimeoutSecs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeoutCheckIntervalSecs", Class.forName("weblogic.j2ee.descriptor.wl.AsyncDescriptorBeanDConfig"), "getTimeoutCheckIntervalSecs", "setTimeoutCheckIntervalSecs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AsyncWorkManager", Class.forName("weblogic.j2ee.descriptor.wl.AsyncDescriptorBeanDConfig"), "getAsyncWorkManager", "setAsyncWorkManager");
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
            throw new AssertionError("Failed to create PropertyDescriptors for AsyncDescriptorBeanDConfigBeanInfo");
         }
      }
   }
}
