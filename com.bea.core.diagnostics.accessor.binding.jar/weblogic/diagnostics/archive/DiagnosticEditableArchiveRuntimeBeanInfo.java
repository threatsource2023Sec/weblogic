package weblogic.diagnostics.archive;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.diagnostics.accessor.runtime.EditableArchiveRuntimeMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DiagnosticEditableArchiveRuntimeBeanInfo extends DiagnosticArchiveRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = EditableArchiveRuntimeMBean.class;

   public DiagnosticEditableArchiveRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DiagnosticEditableArchiveRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.archive.DiagnosticEditableArchiveRuntime");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.accessor.runtime.EditableArchiveRuntimeMBean");
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
         currentResult = new PropertyDescriptor("DataRetirementCycles", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementCycles", currentResult);
         currentResult.setValue("description", "<p>The number of data retirement cycles since server start.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DataRetirementTasks")) {
         getterName = "getDataRetirementTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("DataRetirementTasks", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementTasks", currentResult);
         currentResult.setValue("description", "<p> Returns array of data retirement tasks that have been created since last purgeDataRetirementTasks operation.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DataRetirementTotalTime")) {
         getterName = "getDataRetirementTotalTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DataRetirementTotalTime", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataRetirementTotalTime", currentResult);
         currentResult.setValue("description", "<p>Total elapsed time for data retirement for this archive.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastDataRetirementStartTime")) {
         getterName = "getLastDataRetirementStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastDataRetirementStartTime", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastDataRetirementStartTime", currentResult);
         currentResult.setValue("description", "<p>Start time for the last data retirement cycle for this archive.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastDataRetirementTime")) {
         getterName = "getLastDataRetirementTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastDataRetirementTime", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastDataRetirementTime", currentResult);
         currentResult.setValue("description", "<p>Elapsed time for last data retirement cycle for this archive.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecordRetrievalTime")) {
         getterName = "getRecordRetrievalTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordRetrievalTime", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordRetrievalTime", currentResult);
         currentResult.setValue("description", "<p>The time, in milliseconds, spent retrieving records from the archive since the server was started.</p> ");
      }

      if (!descriptors.containsKey("RecordSeekCount")) {
         getterName = "getRecordSeekCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekCount", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekCount", currentResult);
         currentResult.setValue("description", "<p>The number of seek operations performed on the archive since the server was started.</p> ");
      }

      if (!descriptors.containsKey("RecordSeekTime")) {
         getterName = "getRecordSeekTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekTime", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekTime", currentResult);
         currentResult.setValue("description", "<p>The time, in milliseconds, spent locating the first record during a query operation since the server was started.</p> ");
      }

      if (!descriptors.containsKey("RetiredRecordCount")) {
         getterName = "getRetiredRecordCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RetiredRecordCount", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetiredRecordCount", currentResult);
         currentResult.setValue("description", "<p>Number of records retired since server start.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetrievedRecordCount")) {
         getterName = "getRetrievedRecordCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RetrievedRecordCount", EditableArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetrievedRecordCount", currentResult);
         currentResult.setValue("description", "<p>The number of records retrieved from the archive since the server was started.</p> ");
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
      Method mth = EditableArchiveRuntimeMBean.class.getMethod("performRetirement");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Perform data retirement on demand, and delete records older than specified age in the retirement policy. </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = EditableArchiveRuntimeMBean.class.getMethod("purgeDataRetirementTasks", Long.TYPE);
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
