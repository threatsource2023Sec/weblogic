package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JMSConnectionFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JMSConnectionFactoryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Name", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getName", "setName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JNDIName", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getJNDIName", "setJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("LocalJNDIName", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getLocalJNDIName", "setLocalJNDIName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultDeliveryParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getDefaultDeliveryParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClientParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getClientParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getTransactionParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FlowControlParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getFlowControlParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LoadBalancingParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getLoadBalancingParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecurityParams", Class.forName("weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBeanDConfig"), "getSecurityParams", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for JMSConnectionFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
