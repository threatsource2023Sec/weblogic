package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class LibraryContextRootOverrideBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(LibraryContextRootOverrideBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ContextRoot", Class.forName("weblogic.j2ee.descriptor.wl.LibraryContextRootOverrideBeanDConfig"), "getContextRoot", "setContextRoot");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OverrideValue", Class.forName("weblogic.j2ee.descriptor.wl.LibraryContextRootOverrideBeanDConfig"), "getOverrideValue", "setOverrideValue");
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
            throw new AssertionError("Failed to create PropertyDescriptors for LibraryContextRootOverrideBeanDConfigBeanInfo");
         }
      }
   }
}
