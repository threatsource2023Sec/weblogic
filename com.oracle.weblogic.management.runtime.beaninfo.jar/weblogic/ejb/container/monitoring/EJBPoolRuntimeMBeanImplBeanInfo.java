package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EJBPoolRuntimeMBean;

public class EJBPoolRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBPoolRuntimeMBean.class;

   public EJBPoolRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBPoolRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EJBPoolRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all free pool runtime information collected for an EJB. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBPoolRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AccessTotalCount")) {
         getterName = "getAccessTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AccessTotalCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AccessTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times an attempt was made to get an instance from the free pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeansInUseCount")) {
         getterName = "getBeansInUseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BeansInUseCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeansInUseCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of bean instances currently in use from the free pool.</p> ");
         currentResult.setValue("deprecated", "28-Aug-2002.  Use getBeansInUseCurrentCount() instead. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeansInUseCurrentCount")) {
         getterName = "getBeansInUseCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BeansInUseCurrentCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeansInUseCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of bean instances currently being used from the free pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestroyedTotalCount")) {
         getterName = "getDestroyedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DestroyedTotalCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestroyedTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times a bean instance from this pool was destroyed due to a non-application Exception being thrown from it.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleBeansCount")) {
         getterName = "getIdleBeansCount";
         setterName = null;
         currentResult = new PropertyDescriptor("IdleBeansCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IdleBeansCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of available bean instances in the free pool.</p>  <p>Returns the total number of available bean instances in the free pool.</p> ");
         currentResult.setValue("deprecated", "28-Aug-2002.  Use getPooledBeansCurrentCount() instead. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MissTotalCount")) {
         getterName = "getMissTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MissTotalCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MissTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times a failed attempt was made to get an instance from the free pool. An Attempt to get a bean from the pool will fail if there are no available instances in the pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PooledBeansCurrentCount")) {
         getterName = "getPooledBeansCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PooledBeansCurrentCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PooledBeansCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the current number of available bean instances in the free pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutTotalCount")) {
         getterName = "getTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeoutTotalCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of Threads that have timed out waiting for an available bean instance from the free pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WaiterCurrentCount")) {
         getterName = "getWaiterCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaiterCurrentCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaiterCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of Threads currently waiting for an available bean instance from the free pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WaiterTotalCount")) {
         getterName = "getWaiterTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaiterTotalCount", EJBPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaiterTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of Threads currently waiting for an available bean instance from the free pool.</p> ");
         currentResult.setValue("deprecated", "28-Aug-2002.  Use getWaiterCurrentCount() instead. ");
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
      Method mth = EJBPoolRuntimeMBean.class.getMethod("initializePool");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Instructs the Pool to initialize itself to its configured startup time size.</p>  <p>This is a synchronous and will wait until the pool is initialized before returning.</p> ");
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
