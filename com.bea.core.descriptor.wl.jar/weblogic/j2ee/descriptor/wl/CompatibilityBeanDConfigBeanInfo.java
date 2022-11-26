package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class CompatibilityBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(CompatibilityBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("SerializeByteArrayToOracleBlob", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isSerializeByteArrayToOracleBlob", "setSerializeByteArrayToOracleBlob");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SerializeCharArrayToBytes", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isSerializeCharArrayToBytes", "setSerializeCharArrayToBytes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AllowReadonlyCreateAndRemove", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isAllowReadonlyCreateAndRemove", "setAllowReadonlyCreateAndRemove");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DisableStringTrimming", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isDisableStringTrimming", "setDisableStringTrimming");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FindersReturnNulls", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isFindersReturnNulls", "setFindersReturnNulls");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoadRelatedBeansFromDbInPostCreate", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "isLoadRelatedBeansFromDbInPostCreate", "setLoadRelatedBeansFromDbInPostCreate");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for CompatibilityBeanDConfigBeanInfo");
         }
      }
   }
}
