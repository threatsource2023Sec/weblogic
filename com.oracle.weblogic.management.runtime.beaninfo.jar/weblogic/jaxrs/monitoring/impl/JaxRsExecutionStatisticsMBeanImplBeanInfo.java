package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;

public class JaxRsExecutionStatisticsMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsExecutionStatisticsRuntimeMBean.class;

   public JaxRsExecutionStatisticsMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsExecutionStatisticsMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsExecutionStatisticsMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("Execution statistics such as min/max/avg time and request count/rate. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AvgTimeLast15m")) {
         getterName = "getAvgTimeLast15m";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeLast15m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeLast15m", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds in the last 15m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgTimeLast15s")) {
         getterName = "getAvgTimeLast15s";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeLast15s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeLast15s", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds in the last 5m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgTimeLast1h")) {
         getterName = "getAvgTimeLast1h";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeLast1h", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeLast1h", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds in the last 1h. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgTimeLast1m")) {
         getterName = "getAvgTimeLast1m";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeLast1m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeLast1m", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds in the last 1m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgTimeLast1s")) {
         getterName = "getAvgTimeLast1s";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeLast1s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeLast1s", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds in the last 1s. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgTimeTotal")) {
         getterName = "getAvgTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeTotal", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeTotal", currentResult);
         currentResult.setValue("description", "Get average request processing time in milliseconds for the whole lifetime of an application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeLast15m")) {
         getterName = "getMaxTimeLast15m";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeLast15m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeLast15m", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds in the last 15m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeLast15s")) {
         getterName = "getMaxTimeLast15s";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeLast15s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeLast15s", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds in the last 5m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeLast1h")) {
         getterName = "getMaxTimeLast1h";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeLast1h", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeLast1h", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds in the last 1h. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeLast1m")) {
         getterName = "getMaxTimeLast1m";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeLast1m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeLast1m", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds in the last 1m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeLast1s")) {
         getterName = "getMaxTimeLast1s";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeLast1s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeLast1s", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds in the last 1s. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTimeTotal")) {
         getterName = "getMaxTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxTimeTotal", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxTimeTotal", currentResult);
         currentResult.setValue("description", "Get maximum request processing time in milliseconds for the whole lifetime of an application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeLast15m")) {
         getterName = "getMinTimeLast15m";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeLast15m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeLast15m", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds in the last 15m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeLast15s")) {
         getterName = "getMinTimeLast15s";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeLast15s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeLast15s", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds in the last 5m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeLast1h")) {
         getterName = "getMinTimeLast1h";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeLast1h", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeLast1h", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds in the last 1h. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeLast1m")) {
         getterName = "getMinTimeLast1m";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeLast1m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeLast1m", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds in the last 1m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeLast1s")) {
         getterName = "getMinTimeLast1s";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeLast1s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeLast1s", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds in the last 1s. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinTimeTotal")) {
         getterName = "getMinTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("MinTimeTotal", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinTimeTotal", currentResult);
         currentResult.setValue("description", "Get minimum request processing time in milliseconds for the whole lifetime of an application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountLast15m")) {
         getterName = "getRequestCountLast15m";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountLast15m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountLast15m", currentResult);
         currentResult.setValue("description", "Get request count in the last 15m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountLast15s")) {
         getterName = "getRequestCountLast15s";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountLast15s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountLast15s", currentResult);
         currentResult.setValue("description", "Get request count in the last 5m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountLast1h")) {
         getterName = "getRequestCountLast1h";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountLast1h", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountLast1h", currentResult);
         currentResult.setValue("description", "Get request count in the last 1h. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountLast1m")) {
         getterName = "getRequestCountLast1m";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountLast1m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountLast1m", currentResult);
         currentResult.setValue("description", "Get request count in the last 1m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountLast1s")) {
         getterName = "getRequestCountLast1s";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountLast1s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountLast1s", currentResult);
         currentResult.setValue("description", "Get request count in the last 1s. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCountTotal")) {
         getterName = "getRequestCountTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCountTotal", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCountTotal", currentResult);
         currentResult.setValue("description", "Get request count for the whole lifetime of an application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateLast15m")) {
         getterName = "getRequestRateLast15m";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateLast15m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateLast15m", currentResult);
         currentResult.setValue("description", "Get average request rate per second in the last 15m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateLast15s")) {
         getterName = "getRequestRateLast15s";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateLast15s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateLast15s", currentResult);
         currentResult.setValue("description", "Get average request rate per second in the last 5m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateLast1h")) {
         getterName = "getRequestRateLast1h";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateLast1h", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateLast1h", currentResult);
         currentResult.setValue("description", "Get average request rate per second in the last 1h. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateLast1m")) {
         getterName = "getRequestRateLast1m";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateLast1m", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateLast1m", currentResult);
         currentResult.setValue("description", "Get average request rate per second in the last 1m. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateLast1s")) {
         getterName = "getRequestRateLast1s";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateLast1s", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateLast1s", currentResult);
         currentResult.setValue("description", "Get average request rate per second in the last 1s. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestRateTotal")) {
         getterName = "getRequestRateTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestRateTotal", JaxRsExecutionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestRateTotal", currentResult);
         currentResult.setValue("description", "Get average request rate per second for the whole lifetime of an application. ");
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
