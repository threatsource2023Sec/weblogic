package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class MemberBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(MemberBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("MemberName", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getMemberName", "setMemberName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MemberValue", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getMemberValue", "setMemberValue");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("OverrideValue", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getOverrideValue", "setOverrideValue");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RequiresEncryption", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getRequiresEncryption", "setRequiresEncryption");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CleartextOverrideValue", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getCleartextOverrideValue", "setCleartextOverrideValue");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SecuredOverrideValue", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getSecuredOverrideValue", "setSecuredOverrideValue");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SecuredOverrideValueEncrypted", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getSecuredOverrideValueEncrypted", "setSecuredOverrideValueEncrypted");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ShortDescription", Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanDConfig"), "getShortDescription", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for MemberBeanDConfigBeanInfo");
         }
      }
   }
}
