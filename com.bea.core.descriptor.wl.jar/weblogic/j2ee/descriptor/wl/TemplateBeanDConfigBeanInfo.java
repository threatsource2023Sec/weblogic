package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class TemplateBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(TemplateBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("DestinationKeys", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getDestinationKeys", "setDestinationKeys");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Thresholds", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getThresholds", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DeliveryParamsOverrides", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getDeliveryParamsOverrides", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DeliveryFailureParams", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getDeliveryFailureParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MessageLoggingParams", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getMessageLoggingParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AttachSender", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getAttachSender", "setAttachSender");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ProductionPausedAtStartup", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "isProductionPausedAtStartup", "setProductionPausedAtStartup");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InsertionPausedAtStartup", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "isInsertionPausedAtStartup", "setInsertionPausedAtStartup");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConsumptionPausedAtStartup", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "isConsumptionPausedAtStartup", "setConsumptionPausedAtStartup");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaximumMessageSize", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getMaximumMessageSize", "setMaximumMessageSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("Quota", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getQuota", "setQuota");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DefaultUnitOfOrder", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "isDefaultUnitOfOrder", "setDefaultUnitOfOrder");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SafExportPolicy", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getSafExportPolicy", "setSafExportPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Multicast", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getMulticast", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TopicSubscriptionParams", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getTopicSubscriptionParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GroupParams", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getGroupParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MessagingPerformancePreference", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getMessagingPerformancePreference", "setMessagingPerformancePreference");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("UnitOfWorkHandlingPolicy", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getUnitOfWorkHandlingPolicy", "setUnitOfWorkHandlingPolicy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("IncompleteWorkExpirationTime", Class.forName("weblogic.j2ee.descriptor.wl.TemplateBeanDConfig"), "getIncompleteWorkExpirationTime", "setIncompleteWorkExpirationTime");
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
            throw new AssertionError("Failed to create PropertyDescriptors for TemplateBeanDConfigBeanInfo");
         }
      }
   }
}
