package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCOracleParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCOracleParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("FanEnabled", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isFanEnabled", "setFanEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("OnsNodeList", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getOnsNodeList", "setOnsNodeList");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OnsWalletFile", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getOnsWalletFile", "setOnsWalletFile");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OnsWalletPasswordEncrypted", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getOnsWalletPasswordEncrypted", "setOnsWalletPasswordEncrypted");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OnsWalletPassword", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getOnsWalletPassword", "setOnsWalletPassword");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OracleEnableJavaNetFastPath", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isOracleEnableJavaNetFastPath", "setOracleEnableJavaNetFastPath");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("OracleOptimizeUtf8Conversion", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isOracleOptimizeUtf8Conversion", "setOracleOptimizeUtf8Conversion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectionInitializationCallback", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getConnectionInitializationCallback", "setConnectionInitializationCallback");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AffinityPolicy", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getAffinityPolicy", "setAffinityPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OracleProxySession", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isOracleProxySession", "setOracleProxySession");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseDatabaseCredentials", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isUseDatabaseCredentials", "setUseDatabaseCredentials");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ReplayInitiationTimeout", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "getReplayInitiationTimeout", "setReplayInitiationTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ActiveGridlink", Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanDConfig"), "isActiveGridlink", "setActiveGridlink");
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
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCOracleParamsBeanDConfigBeanInfo");
         }
      }
   }
}
