package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationSupportBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ConfigurationSupportBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("BaseRootElement", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getBaseRootElement", "setBaseRootElement");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConfigRootElement", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getConfigRootElement", "setConfigRootElement");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BaseNamespace", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getBaseNamespace", "setBaseNamespace");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConfigNamespace", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getConfigNamespace", "setConfigNamespace");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BaseUri", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getBaseUri", "setBaseUri");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConfigUri", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getConfigUri", "setConfigUri");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BasePackageName", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getBasePackageName", "setBasePackageName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConfigPackageName", Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanDConfig"), "getConfigPackageName", "setConfigPackageName");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ConfigurationSupportBeanDConfigBeanInfo");
         }
      }
   }
}
