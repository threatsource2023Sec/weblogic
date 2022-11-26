package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SoapjmsServiceEndpointAddressBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SoapjmsServiceEndpointAddressBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("LookupVariant", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getLookupVariant", "setLookupVariant");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DestinationName", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getDestinationName", "setDestinationName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DestinationType", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getDestinationType", "setDestinationType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JndiConnectionFactoryName", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJndiConnectionFactoryName", "setJndiConnectionFactoryName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JndiInitialContextFactory", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJndiInitialContextFactory", "setJndiInitialContextFactory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JndiUrl", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJndiUrl", "setJndiUrl");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JndiContextParameter", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJndiContextParameter", "setJndiContextParameter");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeToLive", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getTimeToLive", "setTimeToLive");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Priority", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getPriority", "setPriority");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DeliveryMode", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getDeliveryMode", "setDeliveryMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ReplyToName", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getReplyToName", "setReplyToName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TargetService", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getTargetService", "setTargetService");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BindingVersion", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getBindingVersion", "setBindingVersion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageType", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getMessageType", "setMessageType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableHttpWsdlAccess", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "isEnableHttpWsdlAccess", "setEnableHttpWsdlAccess");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RunAsPrincipal", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getRunAsPrincipal", "setRunAsPrincipal");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RunAsRole", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getRunAsRole", "setRunAsRole");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MdbPerDestination", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "isMdbPerDestination", "setMdbPerDestination");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ActivationConfig", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getActivationConfig", "setActivationConfig");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JmsMessageHeader", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJmsMessageHeader", "setJmsMessageHeader");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JmsMessageProperty", Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanDConfig"), "getJmsMessageProperty", "setJmsMessageProperty");
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
            throw new AssertionError("Failed to create PropertyDescriptors for SoapjmsServiceEndpointAddressBeanDConfigBeanInfo");
         }
      }
   }
}
