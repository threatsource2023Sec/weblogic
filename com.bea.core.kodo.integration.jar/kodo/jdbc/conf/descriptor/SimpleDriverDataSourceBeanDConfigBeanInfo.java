package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SimpleDriverDataSourceBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SimpleDriverDataSourceBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ConnectionUserName", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getConnectionUserName", "setConnectionUserName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoginTimeout", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getLoginTimeout", "setLoginTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionPassword", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getConnectionPassword", "setConnectionPassword");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionPasswordEncrypted", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getConnectionPasswordEncrypted", "setConnectionPasswordEncrypted");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionURL", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getConnectionURL", "setConnectionURL");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionDriverName", Class.forName("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanDConfig"), "getConnectionDriverName", "setConnectionDriverName");
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
            throw new AssertionError("Failed to create PropertyDescriptors for SimpleDriverDataSourceBeanDConfigBeanInfo");
         }
      }
   }
}
