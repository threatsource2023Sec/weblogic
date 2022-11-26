package bea.jolt.pool.servlet.weblogic;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JoltConnectionPoolRuntimeMBean;

public class ConnectionPoolRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JoltConnectionPoolRuntimeMBean.class;

   public ConnectionPoolRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionPoolRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("bea.jolt.pool.servlet.weblogic.ConnectionPoolRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "bea.jolt.pool.servlet.weblogic");
      String description = (new String("<p>This class is used for monitoring a WebLogic Jolt Connection Pool</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JoltConnectionPoolRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Connections")) {
         getterName = "getConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("Connections", JoltConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Connections", currentResult);
         currentResult.setValue("description", "<p>Returns an array of <code>JoltConnectionRuntimeMBeans</code> that each represents the statistics for a Jolt Connection.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.JoltConnectionRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxCapacity", JoltConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "<p>The maximum connections configured for this Jolt pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolName")) {
         getterName = "getPoolName";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolName", JoltConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolName", currentResult);
         currentResult.setValue("description", "<p>The configured name of this Jolt pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolState")) {
         getterName = "getPoolState";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolState", JoltConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolState", currentResult);
         currentResult.setValue("description", "<p>The pool state as one of 'Active' 'Suspended'.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityContextPropagation")) {
         getterName = "isSecurityContextPropagation";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityContextPropagation", JoltConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecurityContextPropagation", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the security context is propagated.</p> ");
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
      Method mth = JoltConnectionPoolRuntimeMBean.class.getMethod("resetConnectionPool");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resets the connection pool.</p> ");
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
