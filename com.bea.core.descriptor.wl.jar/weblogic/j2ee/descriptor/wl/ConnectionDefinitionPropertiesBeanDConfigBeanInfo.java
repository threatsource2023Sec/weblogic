package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDefinitionPropertiesBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ConnectionDefinitionPropertiesBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("PoolParams", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getPoolParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PoolParamsSet", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "isPoolParamsSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Logging", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getLogging", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoggingSet", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "isLoggingSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionSupport", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getTransactionSupport", "setTransactionSupport");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AuthenticationMechanisms", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getAuthenticationMechanisms", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ReauthenticationSupport", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "isReauthenticationSupport", "setReauthenticationSupport");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Properties", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getProperties", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PropertiesSet", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "isPropertiesSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ResAuth", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getResAuth", "setResAuth");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ConnectionDefinitionPropertiesBeanDConfigBeanInfo");
         }
      }
   }
}
