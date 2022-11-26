package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DefaultDeliveryParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DefaultDeliveryParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("DefaultDeliveryMode", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultDeliveryMode", "setDefaultDeliveryMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultTimeToDeliver", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultTimeToDeliver", "setDefaultTimeToDeliver");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultTimeToLive", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultTimeToLive", "setDefaultTimeToLive");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultPriority", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultPriority", "setDefaultPriority");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultRedeliveryDelay", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultRedeliveryDelay", "setDefaultRedeliveryDelay");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("SendTimeout", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getSendTimeout", "setSendTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultCompressionThreshold", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultCompressionThreshold", "setDefaultCompressionThreshold");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultUnitOfOrder", Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanDConfig"), "getDefaultUnitOfOrder", "setDefaultUnitOfOrder");
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
            throw new AssertionError("Failed to create PropertyDescriptors for DefaultDeliveryParamsBeanDConfigBeanInfo");
         }
      }
   }
}
