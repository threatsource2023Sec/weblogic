package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ExternalResourceOverrideBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ExternalResourceOverrideBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ResourceName", Class.forName("weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBeanDConfig"), "getResourceName", "setResourceName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResourceType", Class.forName("weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBeanDConfig"), "getResourceType", "setResourceType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RootElement", Class.forName("weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBeanDConfig"), "getRootElement", "setRootElement");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DescriptorFilePath", Class.forName("weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBeanDConfig"), "getDescriptorFilePath", "setDescriptorFilePath");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("VariableAssignments", Class.forName("weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBeanDConfig"), "getVariableAssignments", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for ExternalResourceOverrideBeanDConfigBeanInfo");
         }
      }
   }
}
