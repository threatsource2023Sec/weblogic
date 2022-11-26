package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DeliveryFailureParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DeliveryFailureParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ErrorDestination", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanDConfig"), "getErrorDestination", "setErrorDestination");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("RedeliveryLimit", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanDConfig"), "getRedeliveryLimit", "setRedeliveryLimit");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ExpirationPolicy", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanDConfig"), "getExpirationPolicy", "setExpirationPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ExpirationLoggingPolicy", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanDConfig"), "getExpirationLoggingPolicy", "setExpirationLoggingPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TemplateBean", Class.forName("weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBeanDConfig"), "getTemplateBean", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for DeliveryFailureParamsBeanDConfigBeanInfo");
         }
      }
   }
}
