package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DebugScopeMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DebugScopeMBean.class;

   public DebugScopeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DebugScopeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DebugScopeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface captures whether the enabled state of a debug scope. The name of a debug scope is the fully qualified scope name for example weblogic.security.SSL ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DebugScopeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", DebugScopeMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "It seems that the getter must be overriden because the setter is. ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", DebugScopeMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "Gets the enabled state of the debug scope mbean. By default a debug scope is disabled. ");
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
