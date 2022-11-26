package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPoolParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCConnectionPoolParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("InitialCapacity", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getInitialCapacity", "setInitialCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxCapacity", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getMaxCapacity", "setMaxCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MinCapacity", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getMinCapacity", "setMinCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("CapacityIncrement", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getCapacityIncrement", "setCapacityIncrement");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ShrinkFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getShrinkFrequencySeconds", "setShrinkFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("HighestNumWaiters", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getHighestNumWaiters", "setHighestNumWaiters");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionCreationRetryFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getConnectionCreationRetryFrequencySeconds", "setConnectionCreationRetryFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getConnectionReserveTimeoutSeconds", "setConnectionReserveTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TestFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getTestFrequencySeconds", "setTestFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TestConnectionsOnReserve", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isTestConnectionsOnReserve", "setTestConnectionsOnReserve");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ProfileHarvestFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getProfileHarvestFrequencySeconds", "setProfileHarvestFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("IgnoreInUseConnectionsEnabled", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isIgnoreInUseConnectionsEnabled", "setIgnoreInUseConnectionsEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getInactiveConnectionTimeoutSeconds", "setInactiveConnectionTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TestTableName", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getTestTableName", "setTestTableName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("LoginDelaySeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getLoginDelaySeconds", "setLoginDelaySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InitSql", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getInitSql", "setInitSql");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatementCacheSize", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getStatementCacheSize", "setStatementCacheSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("StatementCacheType", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getStatementCacheType", "setStatementCacheType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RemoveInfectedConnections", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isRemoveInfectedConnections", "setRemoveInfectedConnections");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondsToTrustAnIdlePoolConnection", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getSecondsToTrustAnIdlePoolConnection", "setSecondsToTrustAnIdlePoolConnection");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("StatementTimeout", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getStatementTimeout", "setStatementTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ProfileType", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getProfileType", "setProfileType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCXADebugLevel", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getJDBCXADebugLevel", "setJDBCXADebugLevel");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CredentialMappingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isCredentialMappingEnabled", "setCredentialMappingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DriverInterceptor", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getDriverInterceptor", "setDriverInterceptor");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PinnedToThread", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isPinnedToThread", "setPinnedToThread");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IdentityBasedConnectionPoolingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isIdentityBasedConnectionPoolingEnabled", "setIdentityBasedConnectionPoolingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("WrapTypes", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isWrapTypes", "setWrapTypes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FatalErrorCodes", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getFatalErrorCodes", "setFatalErrorCodes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionLabelingCallback", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getConnectionLabelingCallback", "setConnectionLabelingCallback");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionHarvestMaxCount", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getConnectionHarvestMaxCount", "setConnectionHarvestMaxCount");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionHarvestTriggerCount", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getConnectionHarvestTriggerCount", "setConnectionHarvestTriggerCount");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("CountOfTestFailuresTillFlush", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getCountOfTestFailuresTillFlush", "setCountOfTestFailuresTillFlush");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("CountOfRefreshFailuresTillDisable", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getCountOfRefreshFailuresTillDisable", "setCountOfRefreshFailuresTillDisable");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("WrapJdbc", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isWrapJdbc", "setWrapJdbc");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ProfileConnectionLeakTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "getProfileConnectionLeakTimeoutSeconds", "setProfileConnectionLeakTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("InvokeBeginEndRequest", Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanDConfig"), "isInvokeBeginEndRequest", "setInvokeBeginEndRequest");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCConnectionPoolParamsBeanDConfigBeanInfo");
         }
      }
   }
}
