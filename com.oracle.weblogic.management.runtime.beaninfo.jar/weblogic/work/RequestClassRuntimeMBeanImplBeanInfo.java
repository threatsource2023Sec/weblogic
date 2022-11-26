package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.RequestClassRuntimeMBean;

public class RequestClassRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RequestClassRuntimeMBean.class;

   public RequestClassRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RequestClassRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.RequestClassRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("RequestClassRuntimeMBean presents runtime information about RequestClasses. A request class represents a class of work. Work using the same request class shares the same priority. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.RequestClassRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CompletedCount")) {
         getterName = "getCompletedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedCount", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedCount", currentResult);
         currentResult.setValue("description", "Total number of completions since server start ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeltaFirst")) {
         getterName = "getDeltaFirst";
         setterName = null;
         currentResult = new PropertyDescriptor("DeltaFirst", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeltaFirst", currentResult);
         currentResult.setValue("description", "Undocumented attribute that exposes a value used in determining priority ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeltaRepeat")) {
         getterName = "getDeltaRepeat";
         setterName = null;
         currentResult = new PropertyDescriptor("DeltaRepeat", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeltaRepeat", currentResult);
         currentResult.setValue("description", "Undocumented attribute that exposes a value used in determining priority ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Interval")) {
         getterName = "getInterval";
         setterName = null;
         currentResult = new PropertyDescriptor("Interval", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Interval", currentResult);
         currentResult.setValue("description", "Undocumented attribute that exposes a value used in determining priority. This attribute is applicable only for ResponseTimeRequestClass. -1 is returned for FairShareRequestClasses. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MyLast")) {
         getterName = "getMyLast";
         setterName = null;
         currentResult = new PropertyDescriptor("MyLast", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MyLast", currentResult);
         currentResult.setValue("description", "Undocumented attribute that exposes a value used in determining priority ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRequestCount")) {
         getterName = "getPendingRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequestCount", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequestCount", currentResult);
         currentResult.setValue("description", "Number of requests waiting for a thread to become available. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestClassType")) {
         getterName = "getRequestClassType";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestClassType", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestClassType", currentResult);
         currentResult.setValue("description", "Returns the type of RequestClass. Either <code>FAIR_SHARE</code> or <code>RESPONSE_TIME</code> or <code>CONTEXT</code> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadUseSquares")) {
         getterName = "getThreadUseSquares";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadUseSquares", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ThreadUseSquares", currentResult);
         currentResult.setValue("description", "Undocumented attribute that exposes a value used in determining priority ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalThreadUse")) {
         getterName = "getTotalThreadUse";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalThreadUse", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalThreadUse", currentResult);
         currentResult.setValue("description", "Total amount of thread use time in millisec's used by the request class since server start. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VirtualTimeIncrement")) {
         getterName = "getVirtualTimeIncrement";
         setterName = null;
         currentResult = new PropertyDescriptor("VirtualTimeIncrement", RequestClassRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("VirtualTimeIncrement", currentResult);
         currentResult.setValue("description", "Current priority of the request class. The priority is relative to other request class priorities. The priority is calculated dynamically frequently and can change. ");
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
