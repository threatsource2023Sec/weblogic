package weblogic.jms.backend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JMSSessionPoolRuntimeMBean;

public class BEServerSessionPoolBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSSessionPoolRuntimeMBean.class;

   public BEServerSessionPoolBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BEServerSessionPoolBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.backend.BEServerSessionPool");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.backend");
      String description = (new String("This class is used for monitoring a WebLogic JMS session pool. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSSessionPoolRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConnectionConsumers")) {
         getterName = "getConnectionConsumers";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionConsumers", JMSSessionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionConsumers", currentResult);
         currentResult.setValue("description", "<p>The connection consumer for this session pool.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionConsumersCurrentCount")) {
         getterName = "getConnectionConsumersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionConsumersCurrentCount", JMSSessionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionConsumersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of connection consumers for this session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionConsumersHighCount")) {
         getterName = "getConnectionConsumersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionConsumersHighCount", JMSSessionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionConsumersHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of simultaneous connection consumers for this session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionConsumersTotalCount")) {
         getterName = "getConnectionConsumersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionConsumersTotalCount", JMSSessionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionConsumersTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of connection consumers made by this session pool since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSServer")) {
         getterName = "getJMSServer";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServer", JMSSessionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSServer", currentResult);
         currentResult.setValue("description", "<p>The JMS server for this session pool.</p> ");
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
