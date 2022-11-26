package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SecurityWorkContextBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SecurityWorkContextBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("InboundMappingRequired", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "isInboundMappingRequired", "setInboundMappingRequired");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CallerPrincipalDefaultMapped", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "getCallerPrincipalDefaultMapped", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CallerPrincipalDefaultMappedSet", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "isCallerPrincipalDefaultMappedSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CallerPrincipalMappings", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "getCallerPrincipalMappings", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GroupPrincipalDefaultMapped", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "getGroupPrincipalDefaultMapped", "setGroupPrincipalDefaultMapped");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GroupPrincipalMappings", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "getGroupPrincipalMappings", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for SecurityWorkContextBeanDConfigBeanInfo");
         }
      }
   }
}
