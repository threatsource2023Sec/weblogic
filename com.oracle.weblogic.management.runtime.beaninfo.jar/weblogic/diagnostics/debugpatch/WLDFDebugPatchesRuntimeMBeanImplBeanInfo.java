package weblogic.diagnostics.debugpatch;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFDebugPatchesRuntimeMBean;

public class WLDFDebugPatchesRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFDebugPatchesRuntimeMBean.class;

   public WLDFDebugPatchesRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFDebugPatchesRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.debugpatch.WLDFDebugPatchesRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.debugpatch");
      String description = (new String("This interface provides functionality to activate/deactivate dynamic debug patches without requiring server restart. It is assummed that the WLDF instrumentation agent is enabled. This is an internal interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFDebugPatchesRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveDebugPatches")) {
         getterName = "getActiveDebugPatches";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveDebugPatches", WLDFDebugPatchesRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveDebugPatches", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvailableDebugPatches")) {
         getterName = "getAvailableDebugPatches";
         setterName = null;
         currentResult = new PropertyDescriptor("AvailableDebugPatches", WLDFDebugPatchesRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvailableDebugPatches", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugPatchTasks")) {
         getterName = "getDebugPatchTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("DebugPatchTasks", WLDFDebugPatchesRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DebugPatchTasks", currentResult);
         currentResult.setValue("description", "List all debug patch activation/deactivation tasks ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("lookupDebugPatchTask", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Task name ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup a debug patch activation/de-activation task by name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DebugPatchTasks");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("showDebugPatchInfo", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("patch", "Debug patch ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("activateDebugPatch", String.class, String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("patch", "Patch to be activated "), createParameterDescriptor("application", "If not null, limit the scope of activation to specified application. If null, activate at system level. "), createParameterDescriptor("module", "If not null and application is specified, limit the scope of activation within specified module within the application. "), createParameterDescriptor("partitionName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Activate specified debug patch, optionally within the scope of given application and module ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("deactivateDebugPatches", String.class, String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("patches", "Comma separated list of patches to be deactivated "), createParameterDescriptor("application", "If not null, deactivate given patches within the scope of specified application. If null, deactivate at system level. "), createParameterDescriptor("module", "If not null and application is specified, limit the scope of de-activation to specified module. "), createParameterDescriptor("partitionName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deactivate specified patches within the scope of given application and module. ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("deactivateAllDebugPatches");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deactivate all active debug patches ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDebugPatchesRuntimeMBean.class.getMethod("clearDebugPatchTasks");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Clear all completed debug patch activation/deactivation tasks ");
         currentResult.setValue("role", "operation");
      }

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
