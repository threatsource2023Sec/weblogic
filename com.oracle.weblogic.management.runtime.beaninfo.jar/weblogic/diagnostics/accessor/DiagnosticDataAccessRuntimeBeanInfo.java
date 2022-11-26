package weblogic.diagnostics.accessor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;

public class DiagnosticDataAccessRuntimeBeanInfo extends DataAccessRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFDataAccessRuntimeMBean.class;

   public DiagnosticDataAccessRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DiagnosticDataAccessRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.accessor.DiagnosticDataAccessRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.accessor");
      String description = (new String("<p>Use this interface to access the specific type of diagnostic data from an underlying log for which this instance is created.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Monitor"), BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFDataAccessRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ColumnIndexMap")) {
         getterName = "getColumnIndexMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnIndexMap", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("ColumnIndexMap", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("ColumnInfoMap")) {
         getterName = "getColumnInfoMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnInfoMap", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("ColumnInfoMap", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("ColumnTypeMap")) {
         getterName = "getColumnTypeMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnTypeMap", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("ColumnTypeMap", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("Columns")) {
         getterName = "getColumns";
         setterName = null;
         currentResult = new PropertyDescriptor("Columns", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("Columns", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("DataArchiveParameters")) {
         getterName = "getDataArchiveParameters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataArchiveParameters";
         }

         currentResult = new PropertyDescriptor("DataArchiveParameters", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("DataArchiveParameters", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("EarliestAvailableTimestamp")) {
         getterName = "getEarliestAvailableTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("EarliestAvailableTimestamp", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("EarliestAvailableTimestamp", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("LatestAvailableTimestamp")) {
         getterName = "getLatestAvailableTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("LatestAvailableTimestamp", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("LatestAvailableTimestamp", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("LatestRecordId")) {
         getterName = "getLatestRecordId";
         setterName = null;
         currentResult = new PropertyDescriptor("LatestRecordId", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("LatestRecordId", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("TimestampAvailable")) {
         getterName = "isTimestampAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("TimestampAvailable", WLDFDataAccessRuntimeMBean.class, getterName, setterName);
         descriptors.put("TimestampAvailable", currentResult);
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
      Method mth = WLDFDataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", String.class, Long.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, String.class, Long.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, Long.TYPE, String.class, Long.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("getDataRecordCount", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("getDataRecordCount", Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("getDataRecordCount", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("hasMoreData", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("fetch", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("fetch", String.class, Integer.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("closeCursor", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("deleteDataRecords", Long.TYPE, Long.TYPE, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("closeArchive");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("openQueryResultDataStream", Long.TYPE, Long.TYPE, String.class, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("getNextQueryResultDataChunk", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDataAccessRuntimeMBean.class.getMethod("closeQueryResultDataStream", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
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
