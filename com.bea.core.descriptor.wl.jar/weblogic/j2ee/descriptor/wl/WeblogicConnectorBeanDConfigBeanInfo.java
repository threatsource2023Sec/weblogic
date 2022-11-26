package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicConnectorBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicConnectorBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("NativeLibdir", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getNativeLibdir", "setNativeLibdir");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getJNDIName", "setJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", true);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableAccessOutsideApp", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isEnableAccessOutsideApp", "setEnableAccessOutsideApp");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableGlobalAccessToClasses", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isEnableGlobalAccessToClasses", "setEnableGlobalAccessToClasses");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DeployAsAWhole", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isDeployAsAWhole", "setDeployAsAWhole");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("WorkManager", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getWorkManager", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectorWorkManager", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getConnectorWorkManager", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConnectorWorkManagerSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isConnectorWorkManagerSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Security", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getSecurity", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecuritySet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isSecuritySet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Properties", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getProperties", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PropertiesSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isPropertiesSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AdminObjects", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getAdminObjects", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AdminObjectsSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isAdminObjectsSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OutboundResourceAdapter", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getOutboundResourceAdapter", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OutboundResourceAdapterSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "isOutboundResourceAdapterSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Version", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getVersion", "setVersion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondaryDescriptors", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig"), "getSecondaryDescriptors", (String)null);
            pd.setValue("dependency", Boolean.FALSE);
            pd.setValue("declaration", Boolean.FALSE);
            pd.setValue("configurable", Boolean.FALSE);
            pd.setValue("key", Boolean.FALSE);
            pd.setValue("dynamic", Boolean.FALSE);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicConnectorBeanDConfigBeanInfo");
         }
      }
   }
}
