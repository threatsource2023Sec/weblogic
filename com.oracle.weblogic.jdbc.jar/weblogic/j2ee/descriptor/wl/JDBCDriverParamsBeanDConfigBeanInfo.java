package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCDriverParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCDriverParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Url", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "getUrl", "setUrl");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DriverName", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "getDriverName", "setDriverName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Properties", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "getProperties", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PasswordEncrypted", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "getPasswordEncrypted", "setPasswordEncrypted");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UseXaDataSourceInterface", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "isUseXaDataSourceInterface", "setUseXaDataSourceInterface");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Password", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "getPassword", "setPassword");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UsePasswordIndirection", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanDConfig"), "isUsePasswordIndirection", "setUsePasswordIndirection");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCDriverParamsBeanDConfigBeanInfo");
         }
      }
   }
}
