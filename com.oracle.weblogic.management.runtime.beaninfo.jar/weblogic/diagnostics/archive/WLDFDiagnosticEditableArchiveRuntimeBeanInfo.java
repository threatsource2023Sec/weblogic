package weblogic.diagnostics.archive;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFEditableArchiveRuntimeMBean;

public class WLDFDiagnosticEditableArchiveRuntimeBeanInfo extends WLDFDiagnosticArchiveRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFEditableArchiveRuntimeMBean.class;

   public WLDFDiagnosticEditableArchiveRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFDiagnosticEditableArchiveRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.archive.WLDFDiagnosticEditableArchiveRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "10.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.archive");
      String description = (new String("<p>Use this interface to collect statistical information about the editable archives maintained by WLDF, such as JDBC based and weblogic.store based WLDF archives</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFEditableArchiveRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DataRetirementCycles")) {
         getterName = "getDataRetirementCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("DataRetirementCycles", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementCycles", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("DataRetirementTasks")) {
         getterName = "getDataRetirementTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("DataRetirementTasks", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementTasks", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("DataRetirementTotalTime")) {
         getterName = "getDataRetirementTotalTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DataRetirementTotalTime", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementTotalTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("LastDataRetirementStartTime")) {
         getterName = "getLastDataRetirementStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastDataRetirementStartTime", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastDataRetirementStartTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("LastDataRetirementTime")) {
         getterName = "getLastDataRetirementTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastDataRetirementTime", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastDataRetirementTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordRetrievalTime")) {
         getterName = "getRecordRetrievalTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordRetrievalTime", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordRetrievalTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordSeekCount")) {
         getterName = "getRecordSeekCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekCount", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordSeekTime")) {
         getterName = "getRecordSeekTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekTime", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RetiredRecordCount")) {
         getterName = "getRetiredRecordCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RetiredRecordCount", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetiredRecordCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RetrievedRecordCount")) {
         getterName = "getRetrievedRecordCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RetrievedRecordCount", WLDFEditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetrievedRecordCount", currentResult);
         currentResult.setValue("description", " ");
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
      Method mth = WLDFEditableArchiveRuntimeMBean.class.getMethod("performDataRetirement");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Perform data retirement on demand, and delete records older than specified age in the retirement policy. </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFEditableArchiveRuntimeMBean.class.getMethod("performRetirement");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFEditableArchiveRuntimeMBean.class.getMethod("purgeDataRetirementTasks", Long.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("completedBefore", "Completion timestamp (millis since Epoch time) of tasks to be purged ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Purge data retirement tasks which have completed before the specified timestamp.</p> ");
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
