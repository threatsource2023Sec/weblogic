package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ForeignServerBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ForeignServerBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ForeignDestinations", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getForeignDestinations", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ForeignConnectionFactories", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getForeignConnectionFactories", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("InitialContextFactory", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getInitialContextFactory", "setInitialContextFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionURL", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getConnectionURL", "setConnectionURL");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIPropertiesCredentialEncrypted", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getJNDIPropertiesCredentialEncrypted", "setJNDIPropertiesCredentialEncrypted");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIPropertiesCredential", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getJNDIPropertiesCredential", "setJNDIPropertiesCredential");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIProperties", Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanDConfig"), "getJNDIProperties", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for ForeignServerBeanDConfigBeanInfo");
         }
      }
   }
}
