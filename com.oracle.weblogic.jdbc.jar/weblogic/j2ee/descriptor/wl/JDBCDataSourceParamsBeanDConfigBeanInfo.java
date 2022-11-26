package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCDataSourceParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCDataSourceParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("JNDINames", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getJNDINames", "setJNDINames");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Scope", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getScope", "setScope");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RowPrefetch", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "isRowPrefetch", "setRowPrefetch");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RowPrefetchSize", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getRowPrefetchSize", "setRowPrefetchSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StreamChunkSize", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getStreamChunkSize", "setStreamChunkSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AlgorithmType", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getAlgorithmType", "setAlgorithmType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DataSourceList", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getDataSourceList", "setDataSourceList");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionPoolFailoverCallbackHandler", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getConnectionPoolFailoverCallbackHandler", "setConnectionPoolFailoverCallbackHandler");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FailoverRequestIfBusy", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "isFailoverRequestIfBusy", "setFailoverRequestIfBusy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GlobalTransactionsProtocol", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getGlobalTransactionsProtocol", "setGlobalTransactionsProtocol");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("KeepConnAfterLocalTx", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "isKeepConnAfterLocalTx", "setKeepConnAfterLocalTx");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("KeepConnAfterGlobalTx", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "isKeepConnAfterGlobalTx", "setKeepConnAfterGlobalTx");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ProxySwitchingCallback", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getProxySwitchingCallback", "setProxySwitchingCallback");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ProxySwitchingProperties", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanDConfig"), "getProxySwitchingProperties", "setProxySwitchingProperties");
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
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCDataSourceParamsBeanDConfigBeanInfo");
         }
      }
   }
}
