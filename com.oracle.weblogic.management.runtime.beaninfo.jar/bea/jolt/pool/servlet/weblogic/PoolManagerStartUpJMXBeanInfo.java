package bea.jolt.pool.servlet.weblogic;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JoltConnectionServiceRuntimeMBean;

public class PoolManagerStartUpJMXBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JoltConnectionServiceRuntimeMBean.class;

   public PoolManagerStartUpJMXBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PoolManagerStartUpJMXBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("bea.jolt.pool.servlet.weblogic.PoolManagerStartUpJMX");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "bea.jolt.pool.servlet.weblogic");
      String description = (new String("<p>This class is used for monitoring a WebLogic Jolt component</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JoltConnectionServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConnectionPoolCount")) {
         getterName = "getConnectionPoolCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPoolCount", JoltConnectionServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPoolCount", currentResult);
         currentResult.setValue("description", "<p>The number of configured Jolt connection pools.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPools")) {
         getterName = "getConnectionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPools", JoltConnectionServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPools", currentResult);
         currentResult.setValue("description", "<p>An array of <code>JoltConnectionPoolRuntimeMBeans</code> that each represents the statistics for a Jolt Connection Pool.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.JoltConnectionPoolRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
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
