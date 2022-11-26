package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ProxyBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ProxyBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.ProxyBeanDConfig"), "getInactiveConnectionTimeoutSeconds", "setInactiveConnectionTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionProfilingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ProxyBeanDConfig"), "isConnectionProfilingEnabled", "setConnectionProfilingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseConnectionProxies", Class.forName("weblogic.j2ee.descriptor.wl.ProxyBeanDConfig"), "getUseConnectionProxies", "setUseConnectionProxies");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ProxyBeanDConfigBeanInfo");
         }
      }
   }
}
