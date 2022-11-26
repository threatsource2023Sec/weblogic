package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPCounterMonitorMBeanImplBeanInfo extends SNMPJMXMonitorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPCounterMonitorMBean.class;

   public SNMPCounterMonitorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPCounterMonitorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPCounterMonitorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class describes the criteria for a Counter-based Monitor. A notification will be generated when this criteria is satisfied. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPCounterMonitorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Modulus")) {
         getterName = "getModulus";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setModulus";
         }

         currentResult = new PropertyDescriptor("Modulus", SNMPCounterMonitorMBean.class, getterName, setterName);
         descriptors.put("Modulus", currentResult);
         currentResult.setValue("description", "<p>A value to be subtracted from the threshold value when the threshold value is crossed.</p>  <p>If Modulus is 0, a notification is generated each time the agent polls the monitored attribute and its value still exceeds or equals the threshold value.</p>  <p>If Modulus is larger than 0, the value of the modulus is subtracted from the threshold each time the threshold is crossed.</p> ");
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Offset")) {
         getterName = "getOffset";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOffset";
         }

         currentResult = new PropertyDescriptor("Offset", SNMPCounterMonitorMBean.class, getterName, setterName);
         descriptors.put("Offset", currentResult);
         currentResult.setValue("description", "<p>A value to be added to the threshold value each time the observed value equals or exceeds the threshold.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getThreshold()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Threshold")) {
         getterName = "getThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreshold";
         }

         currentResult = new PropertyDescriptor("Threshold", SNMPCounterMonitorMBean.class, getterName, setterName);
         descriptors.put("Threshold", currentResult);
         currentResult.setValue("description", "<p>Specifies a value that triggers the Counter Monitor to generate a notification.</p>  <p>The monitor generates a notification the first time the observed value transitions from below the threshold to at or above the threshold. While the observed value remains at or above the threshold, the Counter Monitor does not generate additional notifications. If the observed value falls below the threshold and then later equals or exceeds the threshold, the SNMP Counter Monitor will generate a notification every time the threshold is crossed.</p>  <p>You can specify an offset value to cause this threshold value to increase each time the observed value equals or exceeds the threshold. The first time the observed value equals or exceeds the new threshold value, this monitor generates a notification and adds the offset value to the new threshold value.</p>  <p>For example, if you set Threshold to 1000 and Offset to 2000, when the observed attribute equals or exceeds 1000, the Counter Monitor sends a notification and increases the threshold to 3000. When the observed attribute equals or exceeds 3000, the Counter Monitor sends a notification and increases the threshold again to 5000.</p> ");
         currentResult.setValue("legalMin", new Long(0L));
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
