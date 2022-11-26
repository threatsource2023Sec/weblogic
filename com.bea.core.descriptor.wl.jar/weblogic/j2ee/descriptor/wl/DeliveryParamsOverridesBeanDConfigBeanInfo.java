package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DeliveryParamsOverridesBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DeliveryParamsOverridesBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("DeliveryMode", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getDeliveryMode", "setDeliveryMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeToDeliver", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getTimeToDeliver", "setTimeToDeliver");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TimeToLive", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getTimeToLive", "setTimeToLive");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Priority", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getPriority", "setPriority");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RedeliveryDelay", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getRedeliveryDelay", "setRedeliveryDelay");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TemplateBean", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanDConfig"), "getTemplateBean", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for DeliveryParamsOverridesBeanDConfigBeanInfo");
         }
      }
   }
}
