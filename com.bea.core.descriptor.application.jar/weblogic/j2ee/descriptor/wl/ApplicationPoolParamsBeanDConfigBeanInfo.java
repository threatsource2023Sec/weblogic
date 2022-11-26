package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPoolParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ApplicationPoolParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("SizeParams", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "getSizeParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("XAParams", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "getXAParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoginDelaySeconds", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "getLoginDelaySeconds", "setLoginDelaySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("LeakProfilingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "isLeakProfilingEnabled", "setLeakProfilingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionCheckParams", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "getConnectionCheckParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCXADebugLevel", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "getJDBCXADebugLevel", "setJDBCXADebugLevel");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RemoveInfectedConnectionsEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanDConfig"), "isRemoveInfectedConnectionsEnabled", "setRemoveInfectedConnectionsEnabled");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ApplicationPoolParamsBeanDConfigBeanInfo");
         }
      }
   }
}
