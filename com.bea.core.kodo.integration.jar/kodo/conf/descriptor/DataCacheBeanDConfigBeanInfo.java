package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DataCacheBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DataCacheBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Name", Class.forName("kodo.conf.descriptor.DataCacheBeanDConfig"), "getName", "setName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for DataCacheBeanDConfigBeanInfo");
         }
      }
   }
}
