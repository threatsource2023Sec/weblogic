package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ConnectionCheckParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ConnectionCheckParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("TableName", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getTableName", "setTableName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CheckOnReserveEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "isCheckOnReserveEnabled", "setCheckOnReserveEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CheckOnReleaseEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "isCheckOnReleaseEnabled", "setCheckOnReleaseEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RefreshMinutes", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getRefreshMinutes", "setRefreshMinutes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CheckOnCreateEnabled", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "isCheckOnCreateEnabled", "setCheckOnCreateEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getConnectionReserveTimeoutSeconds", "setConnectionReserveTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionCreationRetryFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getConnectionCreationRetryFrequencySeconds", "setConnectionCreationRetryFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getInactiveConnectionTimeoutSeconds", "setInactiveConnectionTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TestFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getTestFrequencySeconds", "setTestFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InitSql", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanDConfig"), "getInitSql", "setInitSql");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ConnectionCheckParamsBeanDConfigBeanInfo");
         }
      }
   }
}
