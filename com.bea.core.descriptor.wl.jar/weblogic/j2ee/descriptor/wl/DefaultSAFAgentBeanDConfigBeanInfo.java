package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DefaultSAFAgentBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DefaultSAFAgentBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Notes", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getNotes", "setNotes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BytesMaximum", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getBytesMaximum", "setBytesMaximum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessagesMaximum", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getMessagesMaximum", "setMessagesMaximum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaximumMessageSize", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getMaximumMessageSize", "setMaximumMessageSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultRetryDelayBase", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getDefaultRetryDelayBase", "setDefaultRetryDelayBase");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultRetryDelayMaximum", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getDefaultRetryDelayMaximum", "setDefaultRetryDelayMaximum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultRetryDelayMultiplier", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getDefaultRetryDelayMultiplier", "setDefaultRetryDelayMultiplier");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("WindowSize", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getWindowSize", "setWindowSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("LoggingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "isLoggingEnabled", "setLoggingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultTimeToLive", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getDefaultTimeToLive", "setDefaultTimeToLive");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageBufferSize", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getMessageBufferSize", "setMessageBufferSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("PagingDirectory", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getPagingDirectory", "setPagingDirectory");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("WindowInterval", Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanDConfig"), "getWindowInterval", "setWindowInterval");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for DefaultSAFAgentBeanDConfigBeanInfo");
         }
      }
   }
}
