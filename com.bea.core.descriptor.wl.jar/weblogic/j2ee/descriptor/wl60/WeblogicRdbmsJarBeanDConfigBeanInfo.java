package weblogic.j2ee.descriptor.wl60;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicRdbmsJarBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicRdbmsJarBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("WeblogicRdbmsBeans", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig"), "getWeblogicRdbmsBeans", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CreateDefaultDbmsTables", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig"), "isCreateDefaultDbmsTables", "setCreateDefaultDbmsTables");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidateDbSchemaWith", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig"), "getValidateDbSchemaWith", "setValidateDbSchemaWith");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DatabaseType", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig"), "getDatabaseType", "setDatabaseType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondaryDescriptors", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig"), "getSecondaryDescriptors", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicRdbmsJarBeanDConfigBeanInfo");
         }
      }
   }
}
