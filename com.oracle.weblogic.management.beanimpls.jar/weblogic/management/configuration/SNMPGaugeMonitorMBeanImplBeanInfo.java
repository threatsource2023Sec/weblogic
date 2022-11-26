package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SNMPGaugeMonitorMBeanImplBeanInfo extends SNMPJMXMonitorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPGaugeMonitorMBean.class;

   public SNMPGaugeMonitorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPGaugeMonitorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPGaugeMonitorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class describes the criteria for a Gauge-based Monitor. A notification will be generated when this criteria is satisfied. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPGaugeMonitorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ThresholdHigh")) {
         getterName = "getThresholdHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThresholdHigh";
         }

         currentResult = new PropertyDescriptor("ThresholdHigh", SNMPGaugeMonitorMBean.class, getterName, setterName);
         descriptors.put("ThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The high threshold at which a notification should be generated. A notification is generated the first time the monitored value is equal to or greater than this value.</p> <p>Subsequent crossings of the high threshold value do not cause additional notifications unless the attribute value becomes equal to or less than the low threshold value.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThresholdLow")) {
         getterName = "getThresholdLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThresholdLow";
         }

         currentResult = new PropertyDescriptor("ThresholdLow", SNMPGaugeMonitorMBean.class, getterName, setterName);
         descriptors.put("ThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The low threshold at which a notification should be generated. A notification is generated the first time the monitored value is less than or equal to this value.</p> <p> Subsequent crossings of the low threshold value do not cause additional notifications unless the attribute value becomes equal to or greater than the high threshold value. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
