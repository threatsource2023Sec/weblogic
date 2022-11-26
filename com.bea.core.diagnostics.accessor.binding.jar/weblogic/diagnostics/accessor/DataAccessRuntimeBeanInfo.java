package weblogic.diagnostics.accessor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DataAccessRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DataAccessRuntimeMBean.class;

   public DataAccessRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DataAccessRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.accessor.DataAccessRuntime");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ColumnIndexMap")) {
         getterName = "getColumnIndexMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnIndexMap", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ColumnIndexMap", currentResult);
         currentResult.setValue("description", "<p>Diagnostic data is logically organized as tabular data. This method returns a map of column names to their indices, which are represented as Integer objects.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
      }

      if (!descriptors.containsKey("ColumnInfoMap")) {
         getterName = "getColumnInfoMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnInfoMap", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ColumnInfoMap", currentResult);
         currentResult.setValue("description", "<p>The diagnostic data is logically organized as tabular data. This method returns a map of column names to the respective ColumnInfo Object.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ColumnTypeMap")) {
         getterName = "getColumnTypeMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ColumnTypeMap", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ColumnTypeMap", currentResult);
         currentResult.setValue("description", "<p>Diagnostic data is logically organized as tabular data. This method returns a map of column names to their type names, which are fully qualified class names for the primitive types Integer, Long, Float, Double, String, or an Object.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
      }

      if (!descriptors.containsKey("Columns")) {
         getterName = "getColumns";
         setterName = null;
         currentResult = new PropertyDescriptor("Columns", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Columns", currentResult);
         currentResult.setValue("description", "<p>Diagnostic data is logically organized as tabular data. This method returns an array of ColumnInfo objects, each describing a column in the diagnostic data log.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EarliestAvailableTimestamp")) {
         getterName = "getEarliestAvailableTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("EarliestAvailableTimestamp", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EarliestAvailableTimestamp", currentResult);
         currentResult.setValue("description", "<p>The timestamp, in milliseconds, since Jan 1, 1970 AD, 00:00:00 GMT for the earliest record in the diagnostic data log.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LatestAvailableTimestamp")) {
         getterName = "getLatestAvailableTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("LatestAvailableTimestamp", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LatestAvailableTimestamp", currentResult);
         currentResult.setValue("description", "<p>The timestamp, in milliseconds, since Jan 1, 1970 AD, 00:00:00 GMT for the newest record in the diagnostic data log.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LatestRecordId")) {
         getterName = "getLatestRecordId";
         setterName = null;
         currentResult = new PropertyDescriptor("LatestRecordId", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LatestRecordId", currentResult);
         currentResult.setValue("description", "<p>The latest known record ID for the underlying archive.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimestampAvailable")) {
         getterName = "isTimestampAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("TimestampAvailable", DataAccessRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimestampAvailable", currentResult);
         currentResult.setValue("description", "<p>Returns true, if timestamp information is available with the underlying archive.</p> ");
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
      Method mth = DataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Retrieves the diagnostic data from the underlying log based on the specified query.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginTimeStamp", "The beginning timestamp (inclusive) of the records in the result set. "), createParameterDescriptor("endTimeStamp", "The ending timestamp (exclusive) of the records in the result set. "), createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Retrieves diagnostic data from the underlying log based on the specified query and time range.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.util.Date")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginTimeStamp", "The beginning timestamp (inclusive) of the records in the result set. "), createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Retrieves all diagnostic data from the underlying log that occurred at or after the specified time.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.util.Date")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("retrieveDataRecords", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginRecordId", "The beginning record-id (inclusive) of the records in the result set. "), createParameterDescriptor("endRecordId", "The ending record-id (exclusive) of the records in the result set. "), createParameterDescriptor("endTimeStamp", "Retrieve only those records whose timestamp is earlier than specified value. "), createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Retrieves diagnostic data from the underlying log based on the specified record-ID range, end-time, and query.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("java.util.Date")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor on the server side for the query.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", String.class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("query", "The query expression to filter the result set from the underlying diagnostic log. "), createParameterDescriptor("cursorTimeout", "The timeout interval after which the cursor will become invalid if not invoked. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor with the specified timeout value, in milliseconds.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginTimestamp", (String)null), createParameterDescriptor("endTimestamp", (String)null), createParameterDescriptor("query", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor with the specified query and time range.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> <p>The default cursor timeout period is 5 minutes.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, String.class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginTimestamp", (String)null), createParameterDescriptor("endTimestamp", (String)null), createParameterDescriptor("query", (String)null), createParameterDescriptor("cusorTimeout", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor for records that meet the specified time range and query condition. The timeout value for the cursor is also specified.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginRecordId", "Record id of the first record (inclusive) in the result set "), createParameterDescriptor("endRecordId", "Record id of the last record (exclusive) in the result set "), createParameterDescriptor("endTimestamp", "Include only those records whose timestamp is earlier than this value "), createParameterDescriptor("query", "Query criterion ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor with a given query and a record-id range.</p> <p>The timestamp is specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> <p>The default cursor timeout period is 5 minutes.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("openCursor", Long.TYPE, Long.TYPE, Long.TYPE, String.class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginRecordId", "Record id of the first record (inclusive) in the result set "), createParameterDescriptor("endRecordId", "Record id of the last record (exclusive) in the result set "), createParameterDescriptor("endTimestamp", "Include only those records whose timestamp is earlier than this value "), createParameterDescriptor("query", "Query criterion "), createParameterDescriptor("cusorTimeout", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Opens a cursor using the specified record-ID range, end timestamp, and query. The timeout value for the cursor is also specified.</p> <p>The timestamp is specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("getDataRecordCount", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("query", "Query expression ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number of records that match the specified query.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("getDataRecordCount", Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("startTime", "Start time of the interval (inclusive) "), createParameterDescriptor("endTime", "End time of the interval (exclusive) "), createParameterDescriptor("query", "Query criterion ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number of records that match the specified query and time range.</p> <p>Start and end times are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("getDataRecordCount", Long.TYPE, Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("startRecordId", "Record id of the first record "), createParameterDescriptor("endRecordId", "Look at records whose record-ids are smaller than specified endrecordId "), createParameterDescriptor("endTime", "Look at records which were entered prior to endTime "), createParameterDescriptor("query", "Query criterion ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number that match the specified query and record-ID range, and whose timestamp (if it is available) is earlier than the specified end time.</p> <p>The end time is specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("hasMoreData", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Determines whether the specified cursor has more data to be fetched.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("fetch", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Fetches a maximum of 100 items from the specified cursor.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("fetch", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorName", (String)null), createParameterDescriptor("maxItems", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Fetches items from the open cursor. The total number fetched is limited by the value of the maxItems parameter.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Disposes of the cursor once the client has fetched all the records.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("deleteDataRecords", Long.TYPE, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("startTime", "Start time of the interval (inclusive) "), createParameterDescriptor("endTime", "End time of the interval (exclusive) "), createParameterDescriptor("queryString", "Query criterion ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Delete records within the specified time range that match the specified query. If the query is null or empty, all records within the time range will be deleted.</p>  <p>Start and end times are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p>  <p>Not all archives support a deletion feature. If not supported, this method will throw an UnsupportedOperationException exception.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DataAccessRuntimeMBean.class.getMethod("openQueryResultDataStream", Long.TYPE, Long.TYPE, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beginTimestamp", (String)null), createParameterDescriptor("endTimestamp", (String)null), createParameterDescriptor("query", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Opens a data stream with the specified query and time range.</p> <p>Timestamps are specified as the number of milliseconds elapsed since the epoch: Jan 1 1970 AD, 00:00:00 GMT.</p> <p>The default cursor timeout period is 5 minutes.</p> ");
            String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("getNextQueryResultDataChunk")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = DataAccessRuntimeMBean.class.getMethod("getNextQueryResultDataChunk", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queryResultHandle", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DataAccessRuntimeMBean.class.getMethod("closeQueryResultDataStream", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queryResultHandle", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Closes the stream specified by the given name. ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
