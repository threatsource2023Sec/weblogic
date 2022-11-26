package weblogic.j2ee.descriptor.wl60;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicRdbmsBeanBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicRdbmsBeanBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("EjbName", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getEjbName", "setEjbName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PoolName", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getPoolName", "setPoolName");
            pd.setValue("dependency", true);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DataSourceJndiName", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getDataSourceJndiName", "setDataSourceJndiName");
            pd.setValue("dependency", true);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TableName", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getTableName", "setTableName");
            pd.setValue("dependency", true);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FieldMaps", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getFieldMaps", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Finders", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getFinders", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableTunedUpdates", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "isEnableTunedUpdates", "setEnableTunedUpdates");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicRdbmsBeanBeanDConfigBeanInfo");
         }
      }
   }
}
