package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class AnnotationDefinitionBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(AnnotationDefinitionBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("AnnotationClassName", Class.forName("weblogic.j2ee.descriptor.wl.AnnotationDefinitionBeanDConfig"), "getAnnotationClassName", "setAnnotationClassName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MembershipConstraint", Class.forName("weblogic.j2ee.descriptor.wl.AnnotationDefinitionBeanDConfig"), "getMembershipConstraint", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AllowedOnDeclaration", Class.forName("weblogic.j2ee.descriptor.wl.AnnotationDefinitionBeanDConfig"), "getAllowedOnDeclaration", "setAllowedOnDeclaration");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MemberDefinitions", Class.forName("weblogic.j2ee.descriptor.wl.AnnotationDefinitionBeanDConfig"), "getMemberDefinitions", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for AnnotationDefinitionBeanDConfigBeanInfo");
         }
      }
   }
}
