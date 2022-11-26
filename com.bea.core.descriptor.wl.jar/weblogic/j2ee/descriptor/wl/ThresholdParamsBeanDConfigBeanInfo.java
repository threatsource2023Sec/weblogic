package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ThresholdParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ThresholdParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("BytesHigh", Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanDConfig"), "getBytesHigh", "setBytesHigh");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("BytesLow", Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanDConfig"), "getBytesLow", "setBytesLow");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessagesHigh", Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanDConfig"), "getMessagesHigh", "setMessagesHigh");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessagesLow", Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanDConfig"), "getMessagesLow", "setMessagesLow");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("TemplateBean", Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanDConfig"), "getTemplateBean", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for ThresholdParamsBeanDConfigBeanInfo");
         }
      }
   }
}
