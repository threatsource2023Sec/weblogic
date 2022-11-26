package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ReliabilityConfigBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ReliabilityConfigBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Customized", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "isCustomized", "setCustomized");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("InactivityTimeout", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getInactivityTimeout", "setInactivityTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BaseRetransmissionInterval", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getBaseRetransmissionInterval", "setBaseRetransmissionInterval");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RetransmissionExponentialBackoff", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getRetransmissionExponentialBackoff", "setRetransmissionExponentialBackoff");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("NonBufferedSource", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getNonBufferedSource", "setNonBufferedSource");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("AcknowledgementInterval", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getAcknowledgementInterval", "setAcknowledgementInterval");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SequenceExpiration", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getSequenceExpiration", "setSequenceExpiration");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BufferRetryCount", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getBufferRetryCount", "setBufferRetryCount");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BufferRetryDelay", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getBufferRetryDelay", "setBufferRetryDelay");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("NonBufferedDestination", Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanDConfig"), "getNonBufferedDestination", "setNonBufferedDestination");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ReliabilityConfigBeanDConfigBeanInfo");
         }
      }
   }
}
