package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SNMPStringMonitorMBeanImplBeanInfo extends SNMPJMXMonitorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPStringMonitorMBean.class;

   public SNMPStringMonitorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPStringMonitorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPStringMonitorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class describes the criteria for a String-based Monitor. A notification is generated when this criteria is satisfied. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPStringMonitorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("StringToCompare")) {
         getterName = "getStringToCompare";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStringToCompare";
         }

         currentResult = new PropertyDescriptor("StringToCompare", SNMPStringMonitorMBean.class, getterName, setterName);
         descriptors.put("StringToCompare", currentResult);
         currentResult.setValue("description", "<p>The string against which the value of the monitored attribute will be compared.</p>  <p>A notification is generated when the criteria specified by Notify Match or Notify Differ is satisfied.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NotifyDiffer")) {
         getterName = "isNotifyDiffer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotifyDiffer";
         }

         currentResult = new PropertyDescriptor("NotifyDiffer", SNMPStringMonitorMBean.class, getterName, setterName);
         descriptors.put("NotifyDiffer", currentResult);
         currentResult.setValue("description", "<p>Generates a notification if the value of the monitored attribute and the value of String to Compare are different.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NotifyMatch")) {
         getterName = "isNotifyMatch";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotifyMatch";
         }

         currentResult = new PropertyDescriptor("NotifyMatch", SNMPStringMonitorMBean.class, getterName, setterName);
         descriptors.put("NotifyMatch", currentResult);
         currentResult.setValue("description", "<p>Generates a notification if the value of the monitored attribute and the value of String to Compare are the same.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
