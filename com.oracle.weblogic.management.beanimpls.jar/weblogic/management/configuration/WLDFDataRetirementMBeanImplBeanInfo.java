package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WLDFDataRetirementMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFDataRetirementMBean.class;

   public WLDFDataRetirementMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFDataRetirementMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WLDFDataRetirementMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean specifies how data retirement for a WLDF archive will be performed. This base interface is extended by the interfaces which define specific retirement policies, eg. WLDFDataRetirementByAgeMBean </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WLDFDataRetirementMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ArchiveName")) {
         getterName = "getArchiveName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArchiveName";
         }

         currentResult = new PropertyDescriptor("ArchiveName", WLDFDataRetirementMBean.class, getterName, setterName);
         descriptors.put("ArchiveName", currentResult);
         currentResult.setValue("description", "<p>Name of the archive for which data retirement is configured</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetirementPeriod")) {
         getterName = "getRetirementPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetirementPeriod";
         }

         currentResult = new PropertyDescriptor("RetirementPeriod", WLDFDataRetirementMBean.class, getterName, setterName);
         descriptors.put("RetirementPeriod", currentResult);
         currentResult.setValue("description", "<p>This attribute specifies the period in hours at which the data retirement task will be periodically performed for the archive during the day after it is first executed. The value of this attribute must be positive </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(24));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetirementTime")) {
         getterName = "getRetirementTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetirementTime";
         }

         currentResult = new PropertyDescriptor("RetirementTime", WLDFDataRetirementMBean.class, getterName, setterName);
         descriptors.put("RetirementTime", currentResult);
         currentResult.setValue("description", "<p>This attribute specifies the hour of day at which the data retirement task will first run during the day.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFDataRetirementMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Enable data retirement</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
