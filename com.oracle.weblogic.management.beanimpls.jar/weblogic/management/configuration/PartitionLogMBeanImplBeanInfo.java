package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class PartitionLogMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionLogMBean.class;

   public PartitionLogMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionLogMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.PartitionLogMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Partition scoped logging configuration. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.PartitionLogMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EnabledServerDebugAttributes")) {
         getterName = "getEnabledServerDebugAttributes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabledServerDebugAttributes";
         }

         currentResult = new PropertyDescriptor("EnabledServerDebugAttributes", PartitionLogMBean.class, getterName, setterName);
         descriptors.put("EnabledServerDebugAttributes", currentResult);
         currentResult.setValue("description", "List of attributes from the WebLogic ServerDebugMBean that are enabled for the given partition. Note: Server level debug for a partition should be carefully enabled in consultation with a WebLogic system administrator only for a short period to capture additional debug output when troubleshooting a server problem. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PlatformLoggerLevels")) {
         getterName = "getPlatformLoggerLevels";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatformLoggerLevels";
         }

         currentResult = new PropertyDescriptor("PlatformLoggerLevels", PartitionLogMBean.class, getterName, setterName);
         descriptors.put("PlatformLoggerLevels", currentResult);
         currentResult.setValue("description", "Logger names and level map. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionLogMBean.class.getMethod("addEnabledServerDebugAttribute", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds a ServerDebug attribute to the list of enabled attributes for a partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "EnabledServerDebugAttributes");
      }

      mth = PartitionLogMBean.class.getMethod("removeEnabledServerDebugAttribute", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a ServerDebug attribute from the list of enabled attributes for a partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "EnabledServerDebugAttributes");
      }

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
