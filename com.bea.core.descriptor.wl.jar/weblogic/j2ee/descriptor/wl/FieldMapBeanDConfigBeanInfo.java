package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class FieldMapBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(FieldMapBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("CmpField", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "getCmpField", "setCmpField");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DbmsColumn", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "getDbmsColumn", "setDbmsColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DbmsColumnType", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "getDbmsColumnType", "setDbmsColumnType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DbmsDefaultValue", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "isDbmsDefaultValue", "setDbmsDefaultValue");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GroupName", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "getGroupName", "setGroupName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.FieldMapBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for FieldMapBeanDConfigBeanInfo");
         }
      }
   }
}
