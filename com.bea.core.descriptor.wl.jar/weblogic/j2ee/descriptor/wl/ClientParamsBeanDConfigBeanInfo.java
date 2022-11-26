package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ClientParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ClientParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ClientId", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getClientId", "setClientId");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ClientIdPolicy", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getClientIdPolicy", "setClientIdPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SubscriptionSharingPolicy", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getSubscriptionSharingPolicy", "setSubscriptionSharingPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("AcknowledgePolicy", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getAcknowledgePolicy", "setAcknowledgePolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("AllowCloseInOnMessage", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "isAllowCloseInOnMessage", "setAllowCloseInOnMessage");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessagesMaximum", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getMessagesMaximum", "setMessagesMaximum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MulticastOverrunPolicy", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getMulticastOverrunPolicy", "setMulticastOverrunPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SynchronousPrefetchMode", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getSynchronousPrefetchMode", "setSynchronousPrefetchMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ReconnectPolicy", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getReconnectPolicy", "setReconnectPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ReconnectBlockingMillis", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getReconnectBlockingMillis", "setReconnectBlockingMillis");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TotalReconnectPeriodMillis", Class.forName("weblogic.j2ee.descriptor.wl.ClientParamsBeanDConfig"), "getTotalReconnectPeriodMillis", "setTotalReconnectPeriodMillis");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ClientParamsBeanDConfigBeanInfo");
         }
      }
   }
}
