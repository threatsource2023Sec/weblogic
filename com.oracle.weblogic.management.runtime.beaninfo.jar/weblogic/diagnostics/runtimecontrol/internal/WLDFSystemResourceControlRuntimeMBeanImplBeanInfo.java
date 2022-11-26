package weblogic.diagnostics.runtimecontrol.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;

public class WLDFSystemResourceControlRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFSystemResourceControlRuntimeMBean.class;

   public WLDFSystemResourceControlRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFSystemResourceControlRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.runtimecontrol.internal.WLDFSystemResourceControlRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.runtimecontrol.internal");
      String description = (new String("<p> This MBean acts as a Runtime Control for a WLDF profile. An WLDF profile could either be a configured system resource or an external descriptor provided by a an user at runtime. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("HarvesterManagerRuntime")) {
         getterName = "getHarvesterManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("HarvesterManagerRuntime", WLDFSystemResourceControlRuntimeMBean.class, getterName, setterName);
         descriptors.put("HarvesterManagerRuntime", currentResult);
         currentResult.setValue("description", "Obtain the WLDFHarvesterManagerRuntimeMBean instance associated with this profile. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WatchManagerRuntime")) {
         getterName = "getWatchManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WatchManagerRuntime", WLDFSystemResourceControlRuntimeMBean.class, getterName, setterName);
         descriptors.put("WatchManagerRuntime", currentResult);
         currentResult.setValue("description", "Obtain the WLDFWatchManagerRuntimeMBean instance associated with this profile. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFSystemResourceControlRuntimeMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "is the descriptor enabled ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExternalResource")) {
         getterName = "isExternalResource";
         setterName = null;
         currentResult = new PropertyDescriptor("ExternalResource", WLDFSystemResourceControlRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExternalResource", currentResult);
         currentResult.setValue("description", "Returns the type of the resource, \"built-in\", \"domain\", or \"external\"; currently internal to support ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
