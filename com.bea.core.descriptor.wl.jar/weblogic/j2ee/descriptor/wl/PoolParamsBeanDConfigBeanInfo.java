package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class PoolParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(PoolParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("MaxCapacity", Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanDConfig"), "getMaxCapacity", "setMaxCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanDConfig"), "getConnectionReserveTimeoutSeconds", "setConnectionReserveTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("HighestNumWaiters", Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanDConfig"), "getHighestNumWaiters", "setHighestNumWaiters");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MatchConnectionsSupported", Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanDConfig"), "isMatchConnectionsSupported", "setMatchConnectionsSupported");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UseFirstAvailable", Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanDConfig"), "isUseFirstAvailable", "setUseFirstAvailable");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for PoolParamsBeanDConfigBeanInfo");
         }
      }
   }
}
