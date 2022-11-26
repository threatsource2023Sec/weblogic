package weblogic.wtc.gwt;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WTCStatisticsRuntimeMBean;

public class WTCStatisticsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCStatisticsRuntimeMBean.class;

   public WTCStatisticsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCStatisticsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wtc.gwt.WTCStatisticsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.wtc.gwt");
      String description = (new String("<p>This class is used to:</p> <ul> <li>query statistics of WTC connections.</li> <li>query statistics of WTC imported and exported services.</li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WTCStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConnectionStatistics")) {
         getterName = "getConnectionStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionStatistics", WTCStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionStatistics", currentResult);
         currentResult.setValue("description", "<p>Returns all statistics data for all configured WTC connections.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
      }

      if (!descriptors.containsKey("ExportedServiceStatistics")) {
         getterName = "getExportedServiceStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("ExportedServiceStatistics", WTCStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExportedServiceStatistics", currentResult);
         currentResult.setValue("description", "<p>Returns all statistics data for all exported services.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
      }

      if (!descriptors.containsKey("ImportedServiceStatistics")) {
         getterName = "getImportedServiceStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("ImportedServiceStatistics", WTCStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImportedServiceStatistics", currentResult);
         currentResult.setValue("description", "<p>Returns all statistics data for all imported services.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
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
      Method mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundMessageTotalCount", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of inbound non-conversational, non-CORBA request messages received by this WTC connection. The WTC connection is identified by lDomAccessPointId and rDomAccessPointId pair. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundMessageTotalCount", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service resource name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID for this exported service. "), createParameterDescriptor("isImport", "Should be false to get value for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of inbound non-conversational, non-CORBA request messages received by this exported Service. If specified service is imported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundSuccessReqTotalCount", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service resource name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID for this exported service. "), createParameterDescriptor("isImport", "Should be false to get value for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of inbound non-conversational, non-CORBA request that successfully handled by specific exported service. If specified service is imported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundFailReqTotalCount", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service resource name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID for this exported service. "), createParameterDescriptor("isImport", "Should be false to get value for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of inbound non-conversational, non-CORBA request that return failure by specific exported service. If specified service is imported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundMessageTotalCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of outbound non-conversational, non-CORBA messages received by this WTC connection. The WTC connection is identified by lDomAccessPointId and rDomAccessPointId pair. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundMessageTotalCount", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID. "), createParameterDescriptor("rDomAccessPointIdList", "The remote access point ID list. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of outbound non-conversational, non-CORBA messages send to this imported Service. If specified service is exported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundSuccessReqTotalCount", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID. "), createParameterDescriptor("rDomAccessPointIdList", "The remote access point ID list. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total number of outbound non-conversational, non-CORBA requests successfully handled by specific imported service. If specified service is exported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundFailReqTotalCount", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID. "), createParameterDescriptor("rDomAccessPointIdList", "The remote access point ID list. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total number of outbound non-conversational, non-CORBA requests return failure by specific imported service. If specified service is exported, the return value should be 0. </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundNWMessageTotalSize", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("RDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total message size of inbound non-conversational, non-CORBA messages received on this WTC connection. The WTC connection is defined by LDomAccessPointId and RDomAccessPointId pair. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInboundNWMessageTotalSize", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service resource name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID for this exported service. "), createParameterDescriptor("isImport", "Should be false to get value for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the total size of inbound non-conversational, non-CORBA messages received by this exported Service. If specified service is imported, the return value should be 0. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundNWMessageTotalSize", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("RDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total size of outbound non-conversational, non-CORBA messages received on this WTC connection. The WTC connection is defined by LDomAccessPointId and RDomAccessPointId pair.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutboundNWMessageTotalSize", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service name. "), createParameterDescriptor("lDomAccessPointId", "The local access point ID. "), createParameterDescriptor("rDomAccessPointIdList", "The remote access point ID list. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total size of outbound non-conversational, non-CORBA messages send to this imported Service. If specified service is exported, the return value should be 0.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutstandingNWReqCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the current number of outstanding inbound non-conversational, non-CORBA requests received on this WTC connection. The WTC connection is defined by LDomAccessPointId and RDomAccessPointId pair.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutstandingNWReqCount", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The service name. "), createParameterDescriptor("lDomAccessPointId", "Local access point. "), createParameterDescriptor("rDomAccessPointIdList", "Remote Access Point ID List. If null, then the service is an exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the current number of outstanding non-conversational, non-CORBA requests for specific imported/exported service.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInTransactionCommittedTotalCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total number of inbound transactions committed on this WTC connection. The WTC connection is defined by lDomAccessPointId and rDomAccessPointId pair.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getInTransactionRolledBackTotalCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total number of inbound transactions rolled back on this WTC connection. The WTC connection is defined by lDomAccessPointId and rDomAccessPointId pair.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutTransactionCommittedTotalCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total number of outbound transaction commited on this WTC connection. The WTC connection is defined by lDomAccessPointId and rDomAccessPointId pair.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCStatisticsRuntimeMBean.class.getMethod("getOutTransactionRolledBackTotalCount", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("lDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("rDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the total number of outbound trsactions rolled backed on this WTC connection. The WTC connection is defined by lDomAccessPointId and rDomAccessPointId pair.</p> ");
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
